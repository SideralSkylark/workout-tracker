package com.zorinserver.workout_tracker.service;

import com.zorinserver.workout_tracker.entity.Day;
import com.zorinserver.workout_tracker.repository.DayRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DayService {
    private final DayRepository dayRepository;

    public DayService(DayRepository dayRepository) {
        this.dayRepository = dayRepository;
    }

    public List<Day> getAllDays() {
        return dayRepository.findAll();
    }
}
