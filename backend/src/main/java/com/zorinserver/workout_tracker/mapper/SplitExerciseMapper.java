package com.zorinserver.workout_tracker.mapper;

import com.zorinserver.workout_tracker.dto.SplitExerciseDTO;
import com.zorinserver.workout_tracker.entity.SplitExercise;

public class SplitExerciseMapper {

    public static SplitExerciseDTO toDTO(SplitExercise splitExercise) {
        return SplitExerciseDTO.builder()
                .id(splitExercise.getId())
                .splitId(splitExercise.getSplit().getId())
                .exerciseId(splitExercise.getExercise().getId())
                .sets(splitExercise.getSets())
                .reps(splitExercise.getReps())
                .build();
    }

    public static SplitExercise toEntity(SplitExerciseDTO splitExerciseDTO) {
        SplitExercise splitExercise = new SplitExercise();
        splitExercise.setId(splitExerciseDTO.getId());
        splitExercise.setSets(splitExerciseDTO.getSets());
        splitExercise.setReps(splitExerciseDTO.getReps());
        return splitExercise;
    }
}
