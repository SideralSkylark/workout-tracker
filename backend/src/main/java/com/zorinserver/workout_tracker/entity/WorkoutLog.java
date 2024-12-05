package com.zorinserver.workout_tracker.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class WorkoutLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "workout_date", nullable = false)
    private LocalDate workoutDate;

    @ManyToOne
    @JoinColumn(name = "split_id", nullable = false)
    private Split split;

    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @Column(name = "completed_sets")
    private int completedSets;

    @Column(name = "completed_reps")
    private int completedReps;

    private String notes;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getWorkoutDate() {
        return workoutDate;
    }

    public void setWorkoutDate(LocalDate workoutDate) {
        this.workoutDate = workoutDate;
    }

    public Split getSplit() {
        return split;
    }

    public void setSplit(Split split) {
        this.split = split;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public int getCompletedSets() {
        return completedSets;
    }

    public void setCompletedSets(int completedSets) {
        this.completedSets = completedSets;
    }

    public int getCompletedReps() {
        return completedReps;
    }

    public void setCompletedReps(int completedReps) {
        this.completedReps = completedReps;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
