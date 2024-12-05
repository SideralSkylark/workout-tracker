package com.zorinserver.workout_tracker.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zorinserver.workout_tracker.entity.WorkoutLog;
import com.zorinserver.workout_tracker.service.WorkoutLogService;

@RestController
@RequestMapping("/api/workout-logs")
public class WorkoutLogController {
    private final WorkoutLogService workoutLogService;
    
    public WorkoutLogController(WorkoutLogService workoutLogService) {
        this.workoutLogService = workoutLogService;
    }

    @GetMapping("/{date}")
    public ResponseEntity<List<WorkoutLog>> getLogsByDate(@PathVariable String date) {
        return ResponseEntity.ok(workoutLogService.getLogsByDate(LocalDate.parse(date)));
    }

    @PostMapping
    public ResponseEntity<WorkoutLog> createWorkoutLog(@RequestBody WorkoutLog workoutLog) {
        return ResponseEntity.ok(workoutLogService.createWorkoutLog(workoutLog));
    }
}
