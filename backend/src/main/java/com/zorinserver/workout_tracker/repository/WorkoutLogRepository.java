package com.zorinserver.workout_tracker.repository;

import com.zorinserver.workout_tracker.entity.WorkoutLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface WorkoutLogRepository extends JpaRepository<WorkoutLog, Long> {
    // Custom query to find logs by date, split, or exercise
    List<WorkoutLog> findByWorkoutDate(LocalDate workoutDate);
    List<WorkoutLog> findBySplitId(Long splitId);
}
