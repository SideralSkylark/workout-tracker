package com.zorinserver.workout_tracker.dto;

import lombok.Data;

@Data
public class AddExerciseRequest {
    private String exerciseName;
    private int sets;
    private int reps;
    private Long dayId;
    private Long splitId;
}
