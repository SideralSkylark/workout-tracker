package com.zorinserver.workout_tracker.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zorinserver.workout_tracker.dto.LogByIdDTO;
import com.zorinserver.workout_tracker.dto.WorkoutLogDTO;
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

    @GetMapping("/logs-exist")
    public ResponseEntity<Boolean> checkLogsForDateAndSplit(
        @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        @RequestParam("splitId") Long splitId
    ) {
        boolean logsExist = workoutLogService.logsExistForDateAndSplit(date, splitId);
        return ResponseEntity.ok(logsExist);
    }

    @GetMapping("/current-week")
    public ResponseEntity<List<LogByIdDTO>> getLogsForCurrentWeek(
        @RequestParam(value = "splitId", required = false) Long splitId) {
        
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(java.time.DayOfWeek.MONDAY);
        LocalDate endOfWeek = startOfWeek.plusDays(6);

        List<LogByIdDTO> logs;

        if (splitId != null) {
            logs = workoutLogService.getLogsForDateRangeAndSplit(startOfWeek, endOfWeek, splitId);
        } else {
            logs = workoutLogService.getLogsForDateRange(startOfWeek, endOfWeek);
        }

        return ResponseEntity.ok(logs);
    }

    @GetMapping("/by-split/{splitId}")
    public ResponseEntity<List<LogByIdDTO>> getLogsBySplitId(@PathVariable Long splitId) {
        List<LogByIdDTO> logs = workoutLogService.getLogsBySplitId(splitId);
        return ResponseEntity.ok(logs);
    }

    @PostMapping("/log-workout")
    public ResponseEntity<List<WorkoutLogDTO>> logWorkouts(@RequestBody List<WorkoutLogWithSetsDTO> workoutLogDTOs) {
        List<WorkoutLog> savedLogs = workoutLogService.createWorkoutLogsFromDTOs(workoutLogDTOs);

        List<WorkoutLogDTO> savedLogDTOs = savedLogs.stream()
            .map(log -> {
                WorkoutLogDTO dto = new WorkoutLogDTO();
                dto.setId(log.getId());
                dto.setWorkoutDate(log.getWorkoutDate());
                dto.setExerciseId(log.getExercise().getId());
                dto.setSplitId(log.getSplit().getId());
                dto.setCompletedSets(log.getCompletedSets());
                dto.setCompletedReps(log.getCompletedReps());
                return dto;
            }).collect(Collectors.toList());

        return ResponseEntity.ok(savedLogDTOs);
    }
}
