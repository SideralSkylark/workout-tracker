package com.zorinserver.workout_tracker.repository;

import com.zorinserver.workout_tracker.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    // Add custom queries if needed
}
