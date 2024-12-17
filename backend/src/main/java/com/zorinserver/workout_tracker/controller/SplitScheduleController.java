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

import com.zorinserver.workout_tracker.dto.DayDTO;
import com.zorinserver.workout_tracker.dto.ExerciseDayDTO;
import com.zorinserver.workout_tracker.entity.SplitSchedule;
import com.zorinserver.workout_tracker.service.SplitScheduleService;

@RestController
@RequestMapping("/api/split-schedules")
public class SplitScheduleController {
    private final SplitScheduleService splitScheduleService;

    public SplitScheduleController(SplitScheduleService splitScheduleService) {
        this.splitScheduleService = splitScheduleService;
    }

    @GetMapping("/exercises-by-day-id/{dayId}/{splitId}")
    public ResponseEntity<List<ExerciseDayDTO>> getExercisesByDayId(@PathVariable Long dayId, @PathVariable Long splitId) {
        return ResponseEntity.ok(splitScheduleService.getExercisesByDayIdAndSplitId(dayId, splitId));
    }

    @GetMapping("/days-with-workouts/{splitId}")
    public ResponseEntity<List<DayDTO>> getDaysWithWorkouts(@PathVariable Long splitId) {
        List<DayDTO> daysWithWorkouts = splitScheduleService.getDaysWithWorkoutsBySplitId(splitId);
        return ResponseEntity.ok(daysWithWorkouts);
    }

    @GetMapping
    public ResponseEntity<List<SplitSchedule>> getAllSplitSchedules() {
        return ResponseEntity.ok(splitScheduleService.getAllSplitSchedules());
    }

    @GetMapping("/split/{splitId}")
    public ResponseEntity<List<SplitSchedule>> getSchedulesBySplitId(@PathVariable Long splitId) {
        return ResponseEntity.ok(splitScheduleService.getSchedulesBySplitId(splitId));
    }

    @GetMapping("/day/{dayId}")
    public ResponseEntity<List<SplitSchedule>> getSchedulesByDayId(@PathVariable Long dayId) {
        return ResponseEntity.ok(splitScheduleService.getSchedulesByDayId(dayId));
    }

    @PostMapping
    public ResponseEntity<SplitSchedule> createSplitSchedule(@RequestBody SplitSchedule splitSchedule) {
        return ResponseEntity.ok(splitScheduleService.createSplitSchedule(splitSchedule));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSplitSchedule(@PathVariable Long id) {
        splitScheduleService.deleteSplitSchedule(id);
        return ResponseEntity.noContent().build();
    }
}
