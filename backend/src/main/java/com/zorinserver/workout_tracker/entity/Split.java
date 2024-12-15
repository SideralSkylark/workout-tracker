package com.zorinserver.workout_tracker.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Split {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "split", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<SplitExercise> splitExercises;

    @OneToMany(mappedBy = "split", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<SplitSchedule> splitSchedules;

    @OneToMany(mappedBy = "split", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<WorkoutLog> workoutLogs;

    public Split() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<SplitExercise> getSplitExercises() {
        return splitExercises;
    }

    public void setSplitExercises(List<SplitExercise> splitExercises) {
        this.splitExercises = splitExercises;
    }

    public List<SplitSchedule> getSplitSchedules() {
        return splitSchedules;
    }

    public void setSplitSchedules(List<SplitSchedule> splitSchedules) {
        this.splitSchedules = splitSchedules;
    }

    public List<WorkoutLog> getWorkoutLogs() {
        return workoutLogs;
    }

    public void setWorkoutLogs(List<WorkoutLog> workoutLogs) {
        this.workoutLogs = workoutLogs;
    }
}