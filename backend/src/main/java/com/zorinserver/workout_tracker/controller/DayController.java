package com.zorinserver.workout_tracker.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zorinserver.workout_tracker.entity.Day;
import com.zorinserver.workout_tracker.service.DayService;

@RestController
@RequestMapping("/api/days")
public class DayController {
    private final DayService dayService;

    public DayController(DayService dayService) {
        this.dayService = dayService;
    }

    @GetMapping
    public ResponseEntity<List<Day>> getAllDays() {
        return ResponseEntity.ok(dayService.getAllDays());
    }
}
