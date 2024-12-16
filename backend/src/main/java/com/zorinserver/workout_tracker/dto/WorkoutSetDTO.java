package com.zorinserver.workout_tracker.dto;

public class WorkoutSetDTO {
    private Long id;
    private int setNumber;
    private int completedReps;

    public int getSetNumber() {
        return setNumber;
    }

    public void setSetNumber(int setNumber) {
        this.setNumber = setNumber;
    }

    public int getCompletedReps() {
        return completedReps;
    }

    public void setCompletedReps(int completedReps) {
        this.completedReps = completedReps;
    }
}