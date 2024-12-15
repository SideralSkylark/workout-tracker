package com.zorinserver.workout_tracker.service;

import com.zorinserver.workout_tracker.dto.AddExerciseRequest;
import com.zorinserver.workout_tracker.entity.Day;
import com.zorinserver.workout_tracker.entity.Exercise;
import com.zorinserver.workout_tracker.entity.Split;
import com.zorinserver.workout_tracker.entity.SplitExercise;
import com.zorinserver.workout_tracker.entity.SplitSchedule;
import com.zorinserver.workout_tracker.repository.DayRepository;
import com.zorinserver.workout_tracker.repository.ExerciseRepository;
import com.zorinserver.workout_tracker.repository.SplitExerciseRepository;
import com.zorinserver.workout_tracker.repository.SplitRepository;
import com.zorinserver.workout_tracker.repository.SplitScheduleRepository;
import com.zorinserver.workout_tracker.repository.WorkoutLogRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;
    private final DayRepository dayRepository;
    private final SplitRepository splitRepository;
    private final SplitExerciseRepository splitExerciseRepository;
    private final SplitScheduleRepository splitScheduleRepository;
    private final WorkoutLogRepository workoutLogRepository;

    public ExerciseService(
        ExerciseRepository exerciseRepository,
        DayRepository dayRepository,
        SplitRepository splitRepository,
        SplitExerciseRepository splitExerciseRepository,
        SplitScheduleRepository splitScheduleRepository,
        WorkoutLogRepository workoutLogRepository) {
        this.exerciseRepository = exerciseRepository;
        this.dayRepository = dayRepository;
        this.splitRepository = splitRepository;
        this.splitExerciseRepository = splitExerciseRepository;
        this.splitScheduleRepository = splitScheduleRepository;
        this.workoutLogRepository = workoutLogRepository;
    }

    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
    }

    public Exercise getExerciseById(Long id) {
        return exerciseRepository.findById(id).orElseThrow(() -> new RuntimeException("Exercise not found"));
    }

    public Exercise createExercise(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    @Transactional
    public Exercise addExercise(AddExerciseRequest request) {
        Day day = dayRepository.findById(request.getDayId())
        .orElseThrow(() -> new RuntimeException("Day not found with ID: " + request.getDayId()));

        Split split = splitRepository.findById(request.getSplitId())
        .orElseThrow(() -> new RuntimeException("Split now found with ID: " + request.getSplitId()));

        Exercise exercise = new Exercise();
        exercise.setName(request.getExerciseName());
        exerciseRepository.save(exercise);

        SplitExercise splitExercise = new SplitExercise();
        splitExercise.setExercise(exercise);
        splitExercise.setSplit(split);
        splitExercise.setSets(request.getSets());
        splitExercise.setReps(request.getReps());
        splitExerciseRepository.save(splitExercise);

        SplitSchedule splitSchedule = new SplitSchedule();
        splitSchedule.setDay(day);
        splitSchedule.setExercise(exercise);
        splitSchedule.setSplit(split);
        splitScheduleRepository.save(splitSchedule);

        return exercise;
    }

    public Exercise updateExercise(Long id, Exercise updatedExercise) {
        Exercise exercise = getExerciseById(id);
        exercise.setName(updatedExercise.getName());
        return exerciseRepository.save(exercise);
    }

    @Transactional
    public void deleteExercise(Long exerciseId) {
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new EntityNotFoundException("Exercise not found with id: " + exerciseId));

        // Check if the exercise is referenced in any WorkoutLog
        if (workoutLogRepository.existsByExercise(exercise)) {
            throw new IllegalStateException("Cannot delete exercise as it is referenced in WorkoutLogs");
        }

        // Perform the deletion
        exerciseRepository.delete(exercise);
    }
}
