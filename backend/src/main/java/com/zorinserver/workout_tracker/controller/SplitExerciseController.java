package com.zorinserver.workout_tracker.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zorinserver.workout_tracker.entity.SplitExercise;
import com.zorinserver.workout_tracker.service.SplitExerciseService;

@RestController
@RequestMapping("/api/split-exercises")
public class SplitExerciseController {
    private final SplitExerciseService splitExerciseService;

    public SplitExerciseController(SplitExerciseService splitExerciseService) {
        this.splitExerciseService = splitExerciseService;
    }

    @GetMapping
    public ResponseEntity<List<SplitExercise>> getAllSplitExercises() {
        return ResponseEntity.ok(splitExerciseService.getAllSplitExercises());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SplitExercise> getSplitExerciseById(@PathVariable Long id) {
        return ResponseEntity.ok(splitExerciseService.getSplitExerciseById(id));
    }

    @PostMapping
    public ResponseEntity<SplitExercise> createSplitExercise(@RequestBody SplitExercise splitExercise) {
        return ResponseEntity.ok(splitExerciseService.createSplitExercise(splitExercise));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSplitExercise(@PathVariable Long id) {
        splitExerciseService.deleteSplitExercise(id);
        return ResponseEntity.noContent().build();
    }
}
