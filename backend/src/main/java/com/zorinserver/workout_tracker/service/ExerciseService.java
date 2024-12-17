package com.zorinserver.workout_tracker.service;

import com.zorinserver.workout_tracker.dto.AddExerciseRequest;
import com.zorinserver.workout_tracker.dto.ExerciseDTO;
import com.zorinserver.workout_tracker.entity.Day;
import com.zorinserver.workout_tracker.entity.Exercise;
import com.zorinserver.workout_tracker.entity.Split;
import com.zorinserver.workout_tracker.entity.SplitExercise;
import com.zorinserver.workout_tracker.entity.SplitSchedule;
import com.zorinserver.workout_tracker.mapper.ExerciseMapper;
import com.zorinserver.workout_tracker.repository.DayRepository;
import com.zorinserver.workout_tracker.repository.ExerciseRepository;
import com.zorinserver.workout_tracker.repository.SplitExerciseRepository;
import com.zorinserver.workout_tracker.repository.SplitRepository;
import com.zorinserver.workout_tracker.repository.SplitScheduleRepository;
import com.zorinserver.workout_tracker.repository.WorkoutLogRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;
    private final DayRepository dayRepository;
    private final SplitRepository splitRepository;
    private final SplitExerciseRepository splitExerciseRepository;
    private final SplitScheduleRepository splitScheduleRepository;

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
    }

    public List<ExerciseDTO> getAllExercises() {
        return exerciseRepository.findAll()
                .stream()
                .map(ExerciseMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ExerciseDTO getExerciseById(Long id) {
        Optional<Exercise> exercise = exerciseRepository.findById(id);
        return exercise.map(ExerciseMapper::toDTO).orElse(null);
    }

    public ExerciseDTO createExercise(ExerciseDTO exerciseDTO) {
        Exercise exercise = ExerciseMapper.toEntity(exerciseDTO);
        Exercise savedExercise = exerciseRepository.save(exercise);
        return ExerciseMapper.toDTO(savedExercise);
    }

    @Transactional
    public ExerciseDTO addExercise(AddExerciseRequest request) {
        Day day = dayRepository.findById(request.getDayId())
        .orElseThrow(() -> new RuntimeException("Day not found with ID: " + request.getDayId()));

        Split split = splitRepository.findById(request.getSplitId())
        .orElseThrow(() -> new RuntimeException("Split now found with ID: " + request.getSplitId()));

        Exercise exercise = new Exercise();
        exercise.setName(request.getExerciseName());
        Exercise savedExercise = exerciseRepository.save(exercise);

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

        return ExerciseMapper.toDTO(savedExercise);
    }

    public ExerciseDTO updateExercise(Long id, ExerciseDTO exerciseDTO) {
        Optional<Exercise> existingExercise = exerciseRepository.findById(id);

        if (existingExercise.isPresent()) {
            Exercise exercise = existingExercise.get();
            exercise.setName(exerciseDTO.getName());
            Exercise updatedExercise = exerciseRepository.save(exercise);
            return ExerciseMapper.toDTO(updatedExercise);
        }
        return null;
    }

    @Transactional
    public void deleteExercise(Long exerciseId) {
        if (exerciseRepository.existsById(exerciseId)) {
            exerciseRepository.deleteById(exerciseId);
            splitScheduleRepository.deleteByExerciseId(exerciseId);
        }
    }
}
