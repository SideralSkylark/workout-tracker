package com.zorinserver.workout_tracker.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogByIdDTO {
    private Long id;
    private String name;
    private LocalDate workoutDate;
    private int sets;
    private int reps;
}
