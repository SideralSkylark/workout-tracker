package com.zorinserver.workout_tracker.dto;

import java.time.LocalDateTime;
import java.util.List;

public class SplitDTO {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private List<SplitExerciseDTO> splitExercises;
    private List<SplitScheduleDTO> splitSchedules;
}
