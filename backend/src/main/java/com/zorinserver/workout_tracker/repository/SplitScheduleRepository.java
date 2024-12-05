package com.zorinserver.workout_tracker.repository;

import com.zorinserver.workout_tracker.entity.SplitSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SplitScheduleRepository extends JpaRepository<SplitSchedule, Long> {
    // Custom query to find schedules by split ID or day ID
    List<SplitSchedule> findBySplitId(Long splitId);
    List<SplitSchedule> findByDayId(Long dayId);
}
