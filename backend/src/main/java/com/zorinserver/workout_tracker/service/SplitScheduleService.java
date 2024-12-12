package com.zorinserver.workout_tracker.service;

import com.zorinserver.workout_tracker.dto.ExerciseDayDTO;
import com.zorinserver.workout_tracker.entity.Day;
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

    public List<ExerciseDayDTO> getExercisesByDayIdAndSplitId(Long dayId, Long splitId) {
        return splitScheduleRepository.findExercisesByDayIdAndSplitId(dayId, splitId);
    }

    public List<Day> getDaysWithWorkoutsBySplitId(Long splitId) {
        return splitScheduleRepository.findDaysWithWorkoutsBySplitId(splitId);
    }

    public List<SplitSchedule> getAllSplitSchedules() {
        return splitScheduleRepository.findAll();
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
