package com.zorinserver.workout_tracker.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zorinserver.workout_tracker.dto.WorkoutLogWithSetsDTO;
import com.zorinserver.workout_tracker.entity.WorkoutLog;
import com.zorinserver.workout_tracker.service.WorkoutLogService;

@RestController
@RequestMapping("/api/workout-logs")
public class WorkoutLogController {
    private final WorkoutLogService workoutLogService;

    public WorkoutLogController(WorkoutLogService workoutLogService) {
        this.workoutLogService = workoutLogService;
    }

    @GetMapping
    public ResponseEntity<List<WorkoutLog>> getAllWorkoutLogs() {
        return ResponseEntity.ok(workoutLogService.getAllWorkoutLogs());
    }

    @GetMapping("/{date}")
    public ResponseEntity<List<WorkoutLog>> getLogsByDate(@PathVariable String date) {
        return ResponseEntity.ok(workoutLogService.getLogsByDate(LocalDate.parse(date)));
    }

    @PostMapping("/log-workout")
    public ResponseEntity<?> logWorkouts(@RequestBody List<WorkoutLogWithSetsDTO> workoutLogDTOs) {
        workoutLogDTOs.forEach(dto -> workoutLogService.createWorkoutLogFromDTO(dto));
        return ResponseEntity.ok("Workout logs saved successfully");
    }
}
