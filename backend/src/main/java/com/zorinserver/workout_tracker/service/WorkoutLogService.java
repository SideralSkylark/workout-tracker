package com.zorinserver.workout_tracker.service;

import com.zorinserver.workout_tracker.dto.LogByIdDTO;
import com.zorinserver.workout_tracker.dto.WorkoutLogWithSetsDTO;
import com.zorinserver.workout_tracker.entity.Exercise;
import com.zorinserver.workout_tracker.entity.Split;
import com.zorinserver.workout_tracker.entity.WorkoutLog;
import com.zorinserver.workout_tracker.entity.WorkoutSet;
import com.zorinserver.workout_tracker.mapper.WorkoutLogMapper;
import com.zorinserver.workout_tracker.repository.ExerciseRepository;
import com.zorinserver.workout_tracker.repository.SplitRepository;
import com.zorinserver.workout_tracker.repository.WorkoutLogRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkoutLogService {
    private final WorkoutLogRepository workoutLogRepository;
    private final ExerciseRepository exerciseRepository;
    private final SplitRepository splitRepository;

    public WorkoutLogService(
        WorkoutLogRepository workoutLogRepository, 
        ExerciseRepository exerciseRepository, 
        SplitRepository splitRepository) {
        this.workoutLogRepository = workoutLogRepository;
        this.exerciseRepository = exerciseRepository;
        this.splitRepository = splitRepository;
    }

    public List<WorkoutLog> getAllWorkoutLogs() {
        return workoutLogRepository.findAll();
    }

    public List<WorkoutLog> getLogsByDate(LocalDate date) {
        return workoutLogRepository.findByWorkoutDate(date);
    }

    public boolean logsExistForDate(LocalDate date) {
        return workoutLogRepository.existsByWorkoutDate(date);
    }

    public boolean logsExistForDateAndSplit(LocalDate date, Long splitId) {
        return workoutLogRepository.existsByWorkoutDateAndSplitId(date, splitId);
    }    

    public List<LogByIdDTO> getLogsForDateRange(LocalDate startDate, LocalDate endDate) {
        return workoutLogRepository.findByWorkoutDateBetween(startDate, endDate)
                .stream()
                .map(WorkoutLogMapper::toLogByIdDTO)
                .collect(Collectors.toList());
    }   
    
    public List<LogByIdDTO> getLogsForDateRangeAndSplit(LocalDate startDate, LocalDate endDate, Long splitId) {
        return workoutLogRepository.findByWorkoutDateBetweenAndSplitId(startDate, endDate, splitId)
                .stream()
                .map(WorkoutLogMapper::toLogByIdDTO)
                .collect(Collectors.toList());
    }    

    public WorkoutLog saveWorkoutLogWithSets(WorkoutLog workoutLog, List<WorkoutSet> sets) {
        Exercise exercise = exerciseRepository.findById(workoutLog.getExercise().getId())
            .orElseThrow(() -> new IllegalArgumentException("Invalid exercise ID"));
        workoutLog.setExercise(exercise);

        sets.forEach(set -> set.setWorkoutLog(workoutLog));
        workoutLog.setWorkoutSets(sets);

        return workoutLogRepository.save(workoutLog);
    }

    public List<LogByIdDTO> getLogsBySplitId(Long splitId) {
        return workoutLogRepository.findBySplitId(splitId)
                .stream()
                .map(WorkoutLogMapper::toLogByIdDTO)
                .collect(Collectors.toList());
    }

    public List<WorkoutLog> createWorkoutLogsFromDTOs(List<WorkoutLogWithSetsDTO> dtos) {
        List<WorkoutLog> savedLogs = new ArrayList<>();

        for (WorkoutLogWithSetsDTO dto : dtos) {
            Exercise exercise = exerciseRepository.findById(dto.getExerciseId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid exercise ID: " + dto.getExerciseId()));
            Split split = splitRepository.findById(dto.getSplitId())
                .orElseThrow(() -> new IllegalArgumentException("Split not found: " + dto.getSplitId()));

            WorkoutLog workoutLog = new WorkoutLog();
            workoutLog.setWorkoutDate(dto.getWorkoutDate());
            workoutLog.setExercise(exercise);
            workoutLog.setSplit(split);
            workoutLog.setCompletedSets(dto.getCompletedSets());
            workoutLog.setCompletedReps(dto.getCompletedReps());

            List<WorkoutSet> workoutSets = dto.getWorkoutSets().stream().map(setDTO -> {
                WorkoutSet set = new WorkoutSet();
                set.setSetNumber(setDTO.getSetNumber());
                set.setCompletedReps(setDTO.getCompletedReps());
                set.setWorkoutLog(workoutLog);
                return set;
            }).collect(Collectors.toList());

            WorkoutLog savedLog = saveWorkoutLogWithSets(workoutLog, workoutSets);
            savedLogs.add(savedLog);
        }

        return savedLogs;
    }
}