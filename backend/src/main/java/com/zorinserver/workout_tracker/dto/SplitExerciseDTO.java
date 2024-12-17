package com.zorinserver.workout_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SplitExerciseDTO {
    private Long id;
    private Long splitId;
    private Long exerciseId;
    private int sets;
    private int reps;
}
