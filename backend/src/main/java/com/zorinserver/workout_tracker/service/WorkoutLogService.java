package com.zorinserver.workout_tracker.service;

import com.zorinserver.workout_tracker.entity.WorkoutLog;
import com.zorinserver.workout_tracker.repository.WorkoutLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WorkoutLogService {
    private final WorkoutLogRepository workoutLogRepository;

    public WorkoutLogService(WorkoutLogRepository workoutLogRepository) {
        this.workoutLogRepository = workoutLogRepository;
    }

    public List<WorkoutLog> getLogsByDate(LocalDate date) {
        return workoutLogRepository.findByWorkoutDate(date);
    }

    public WorkoutLog createWorkoutLog(WorkoutLog workoutLog) {
        return workoutLogRepository.save(workoutLog);
    }
}
