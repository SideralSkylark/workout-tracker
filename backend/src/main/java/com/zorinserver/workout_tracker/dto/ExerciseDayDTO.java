package com.zorinserver.workout_tracker.dto;

import lombok.AllArgsConstructor;

import lombok.Data;

@Data
@AllArgsConstructor
public class ExerciseDayDTO {
    private String exerciseName;
    private int sets;
    private int reps;
    private String dayName;
}
