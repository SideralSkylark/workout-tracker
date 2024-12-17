package com.zorinserver.workout_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddExerciseRequest {
    private String exerciseName;
    private int sets;
    private int reps;
    private Long dayId;
    private Long splitId;
}
