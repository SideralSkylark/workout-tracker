package com.zorinserver.workout_tracker.service;

import com.zorinserver.workout_tracker.dto.DayDTO;
import com.zorinserver.workout_tracker.dto.ExerciseDayDTO;
import com.zorinserver.workout_tracker.entity.SplitSchedule;
import com.zorinserver.workout_tracker.mapper.DayMapper;
import com.zorinserver.workout_tracker.repository.SplitScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SplitScheduleService {
    private final SplitScheduleRepository splitScheduleRepository;

    public SplitScheduleService(SplitScheduleRepository splitScheduleRepository) {
        this.splitScheduleRepository = splitScheduleRepository;
    }

    public List<ExerciseDayDTO> getExercisesByDayIdAndSplitId(Long dayId, Long splitId) {
        return splitScheduleRepository.findExercisesByDayIdAndSplitId(dayId, splitId);
    }

    public List<DayDTO> getDaysWithWorkoutsBySplitId(Long splitId) {
        return splitScheduleRepository.findDaysWithWorkoutsBySplitId(splitId)
                .stream()
                .map(DayMapper::toDTO)
                .collect(Collectors.toList());
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
