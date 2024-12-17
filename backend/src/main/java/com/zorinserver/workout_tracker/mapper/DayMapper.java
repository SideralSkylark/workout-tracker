package com.zorinserver.workout_tracker.mapper;

import com.zorinserver.workout_tracker.dto.DayDTO;
import com.zorinserver.workout_tracker.entity.Day;

public class DayMapper {

    public static DayDTO toDTO(Day day) {
        return DayDTO.builder()
                .id(day.getId())
                .name(day.getName())
                .build();
    }

    public static Day toEntity(DayDTO dayDTO) {
        Day day = new Day();
        day.setId(dayDTO.getId());
        day.setName(dayDTO.getName());
        return day;
    }
}
