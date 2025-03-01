package com.zorinserver.workout_tracker.repository;

import com.zorinserver.workout_tracker.entity.Exercise;
import com.zorinserver.workout_tracker.entity.WorkoutLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface WorkoutLogRepository extends JpaRepository<WorkoutLog, Long> {
    List<WorkoutLog> findByWorkoutDate(LocalDate workoutDate);
    boolean existsByWorkoutDate(LocalDate workoutDate);
    boolean existsByWorkoutDateAndSplitId(LocalDate workoutDate, Long splitId);
    List<WorkoutLog> findByWorkoutDateBetween(LocalDate startDate, LocalDate endDate);
    List<WorkoutLog> findByWorkoutDateBetweenAndSplitId(LocalDate startDate, LocalDate endDate, Long splitId);
    List<WorkoutLog> findBySplitId(Long splitId);
    boolean existsByExercise(Exercise exercise);
}
