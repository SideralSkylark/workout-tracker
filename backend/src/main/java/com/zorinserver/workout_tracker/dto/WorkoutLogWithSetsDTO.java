package com.zorinserver.workout_tracker.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutLogWithSetsDTO {
    private LocalDate workoutDate;
    private Long exerciseId;
    private Long splitId;
    private List<WorkoutSetDTO> workoutSets;
}
