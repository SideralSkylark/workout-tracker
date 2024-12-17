package com.zorinserver.workout_tracker.service;

import com.zorinserver.workout_tracker.dto.DayDTO;
import com.zorinserver.workout_tracker.entity.Day;
import com.zorinserver.workout_tracker.mapper.DayMapper;
import com.zorinserver.workout_tracker.repository.DayRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DayService {
    private final DayRepository dayRepository;

    public DayService(DayRepository dayRepository) {
        this.dayRepository = dayRepository;
    }

    public List<DayDTO> getAllDays() {
        return dayRepository.findAll()
                .stream()
                .map(DayMapper::toDTO)
                .collect(Collectors.toList());
    }

    public DayDTO getDayById(Long id) {
        Optional<Day> day = dayRepository.findById(id);
        return day.map(DayMapper::toDTO).orElse(null);
    }

    public DayDTO createDay(DayDTO dayDTO) {
        Day day = DayMapper.toEntity(dayDTO);
        Day savedDay = dayRepository.save(day);
        return DayMapper.toDTO(savedDay);
    }

    public DayDTO updateDay(Long id, DayDTO dayDTO) {
        Optional<Day> existingDay = dayRepository.findById(id);

        if (existingDay.isPresent()) {
            Day day = existingDay.get();
            day.setName(dayDTO.getName());
            Day updatedDay = dayRepository.save(day);
            return DayMapper.toDTO(updatedDay);
        }
        return null;
    }

    public void deleteDay(Long id) {
        if (dayRepository.existsById(id)) {
            dayRepository.deleteById(id);
        }
    }
}
