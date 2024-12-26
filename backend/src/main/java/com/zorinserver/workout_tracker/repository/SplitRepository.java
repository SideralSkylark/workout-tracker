package com.zorinserver.workout_tracker.repository;

import com.zorinserver.workout_tracker.entity.Split;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SplitRepository extends JpaRepository<Split, Long> {
    List<Split> findByUserId(Long userId);
 }
