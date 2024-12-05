package com.zorinserver.workout_tracker.repository;

import com.zorinserver.workout_tracker.entity.Day;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayRepository extends JpaRepository<Day, Long> {
    // Add custom queries, e.g., find by day name
}
