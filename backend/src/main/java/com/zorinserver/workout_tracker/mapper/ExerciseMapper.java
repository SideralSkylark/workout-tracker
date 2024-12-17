package com.zorinserver.workout_tracker.mapper;

import com.zorinserver.workout_tracker.dto.ExerciseDTO;
import com.zorinserver.workout_tracker.entity.Exercise;

public class ExerciseMapper {
    public static ExerciseDTO toDTO(Exercise exercise) {
        return ExerciseDTO.builder()
                .id(exercise.getId())
                .name(exercise.getName())
                .build();
    }

    public static Exercise toEntity(ExerciseDTO exerciseDTO) {
        Exercise exercise = new Exercise();
        exercise.setId(exerciseDTO.getId());
        exercise.setName(exerciseDTO.getName());
        return exercise;
    }
}
