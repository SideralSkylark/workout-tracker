package com.zorinserver.workout_tracker.repository;

import com.zorinserver.workout_tracker.entity.WorkoutSet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutSetRepository extends JpaRepository<WorkoutSet, Long> { }
