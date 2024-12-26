package com.zorinserver.workout_tracker.mapper;

import com.zorinserver.workout_tracker.dto.SplitDTO;
import com.zorinserver.workout_tracker.dto.SplitResponseDTO;
import com.zorinserver.workout_tracker.entity.Split;

public class SplitMapper {

    public static SplitDTO toDTO(Split split) {
        return SplitDTO.builder()
                .id(split.getId())
                .name(split.getName())
                .createdAt(split.getCreatedAt())
                .build();
    }

    public static SplitResponseDTO toSplitResponseDTO(Split split) {
        return SplitResponseDTO.builder()
                .id(split.getId())
                .name(split.getName())
                .createdAt(split.getCreatedAt())
                .build();
    }

    public static Split toEntity(SplitDTO splitDTO) {
        Split split = new Split();
        split.setId(splitDTO.getId());
        split.setName(splitDTO.getName());
        split.setCreatedAt(splitDTO.getCreatedAt());
        return split;
    }
}
