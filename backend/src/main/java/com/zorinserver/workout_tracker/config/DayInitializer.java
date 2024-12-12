package com.zorinserver.workout_tracker.config;

import com.zorinserver.workout_tracker.entity.Day;
import com.zorinserver.workout_tracker.repository.DayRepository;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Component
public class DayInitializer {

    private final DayRepository dayRepository;

    public DayInitializer(DayRepository dayRepository) {
        this.dayRepository = dayRepository;
    }

    @PostConstruct
    public void initDays() {
        if (dayRepository.count() == 0) {
            List<String> daysOfWeek = Arrays.asList(
                "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
            );
            for (String dayName : daysOfWeek) {
                Day day = new Day();
                day.setName(dayName);
                dayRepository.save(day);
            }
        }
    }
}
