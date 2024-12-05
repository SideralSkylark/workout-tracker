package com.zorinserver.workout_tracker.repository;

import com.zorinserver.workout_tracker.entity.Split;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SplitRepository extends JpaRepository<Split, Long> {
    // Add custom queries if needed, e.g., find by name
}
