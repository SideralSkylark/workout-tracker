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
public class WorkoutLogDTO {
    private Long id;
    private LocalDate workoutDate;
    private Long splitId;
    private Long exerciseId;
    private int completedSets;
    private int completedReps;
    private String notes;
    private List<WorkoutSetDTO> workoutSets;
}
