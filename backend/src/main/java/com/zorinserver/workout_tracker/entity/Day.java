package com.zorinserver.workout_tracker.entity;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "day", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<SplitSchedule> splitSchedules;

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

    public List<SplitSchedule> getSplitSchedules() {
        return splitSchedules;
    }

    public void setSplitSchedules(List<SplitSchedule> splitSchedules) {
        this.splitSchedules = splitSchedules;
    }
}