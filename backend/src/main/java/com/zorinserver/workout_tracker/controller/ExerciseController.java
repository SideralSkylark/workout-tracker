package com.zorinserver.workout_tracker.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zorinserver.workout_tracker.dto.AddExerciseRequest;
import com.zorinserver.workout_tracker.dto.ExerciseDTO;
import com.zorinserver.workout_tracker.service.ExerciseService;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {
    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping
    public ResponseEntity<List<ExerciseDTO>> getAllExercises() {
        return ResponseEntity.ok(exerciseService.getAllExercises());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseDTO> getExerciseById(@PathVariable Long id) {
        return ResponseEntity.ok(exerciseService.getExerciseById(id));
    }

    @PostMapping
    public ResponseEntity<ExerciseDTO> createExercise(@RequestBody ExerciseDTO exercise) {
        return ResponseEntity.ok(exerciseService.createExercise(exercise));
    }
// diferent dto
    @PostMapping("/addExercise")
    public ResponseEntity<ExerciseDTO> addExercise(@RequestBody AddExerciseRequest request) {
        ExerciseDTO exercise = exerciseService.addExercise(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(exercise);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExerciseDTO> updateExercise(@PathVariable Long id, @RequestBody ExerciseDTO exerciseDTO) {
        return ResponseEntity.ok(exerciseService.updateExercise(id, exerciseDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercise(@PathVariable Long id) {
        exerciseService.deleteExercise(id);
        return ResponseEntity.noContent().build();
    }
}
