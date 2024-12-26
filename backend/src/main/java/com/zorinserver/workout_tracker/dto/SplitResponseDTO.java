package com.zorinserver.workout_tracker.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SplitResponseDTO {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
}
