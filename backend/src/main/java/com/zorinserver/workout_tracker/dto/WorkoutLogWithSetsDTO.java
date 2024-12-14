package com.zorinserver.workout_tracker.dto;

import java.time.LocalDate;
import java.util.List;


public class WorkoutLogWithSetsDTO {
    private LocalDate workoutDate;
    private Long exerciseId;
    private Long splitId;
    private List<WorkoutSetDTO> workoutSets;

    public LocalDate getWorkoutDate() {
        return workoutDate;
    }

    public void setWorkoutDate(LocalDate workoutDate) {
        this.workoutDate = workoutDate;
    }

    public Long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public Long getSplitId() {
        return splitId;
    }

    public void setSplitId(Long splitId) {
        this.splitId = splitId;
    }

    public List<WorkoutSetDTO> getWorkoutSets() {
        return workoutSets;
    }

    public void setWorkoutSets(List<WorkoutSetDTO> workoutSets) {
        this.workoutSets = workoutSets;
    }
}
