package com.zorinserver.workout_tracker.dto;

public class WorkoutSetDTO {
    private int setNumber;
    private int completedReps;

    // Getters and setters

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