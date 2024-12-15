package com.zorinserver.workout_tracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
public class WorkoutSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "workout_log_id", nullable = false)
    @JsonIgnore
    private WorkoutLog workoutLog;

    @Column(name = "set_number", nullable = false)
    private int setNumber;

    @Column(name = "completed_reps", nullable = false)
    private int completedReps;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WorkoutLog getWorkoutLog() {
        return workoutLog;
    }

    public void setWorkoutLog(WorkoutLog workoutLog) {
        this.workoutLog = workoutLog;
    }

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