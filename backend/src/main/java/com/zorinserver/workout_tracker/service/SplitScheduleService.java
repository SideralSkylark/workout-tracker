package com.zorinserver.workout_tracker.service;

import com.zorinserver.workout_tracker.entity.SplitSchedule;
import com.zorinserver.workout_tracker.repository.SplitScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SplitScheduleService {
    private final SplitScheduleRepository splitScheduleRepository;

    public SplitScheduleService(SplitScheduleRepository splitScheduleRepository) {
        this.splitScheduleRepository = splitScheduleRepository;
    }

    public List<SplitSchedule> getSchedulesBySplitId(Long splitId) {
        return splitScheduleRepository.findBySplitId(splitId);
    }

    public List<SplitSchedule> getSchedulesByDayId(Long dayId) {
        return splitScheduleRepository.findByDayId(dayId);
    }

    public SplitSchedule createSplitSchedule(SplitSchedule splitSchedule) {
        return splitScheduleRepository.save(splitSchedule);
    }

    public void deleteSplitSchedule(Long id) {
        splitScheduleRepository.deleteById(id);
    }
}
