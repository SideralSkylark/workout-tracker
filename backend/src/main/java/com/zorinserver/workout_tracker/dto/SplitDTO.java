package com.zorinserver.workout_tracker.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SplitDTO {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private List<SplitExerciseDTO> splitExercises;
    private List<SplitScheduleDTO> splitSchedules;
}
