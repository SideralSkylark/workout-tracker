package com.zorinserver.workout_tracker.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zorinserver.workout_tracker.dto.DayDTO;
import com.zorinserver.workout_tracker.service.DayService;

@RestController
@RequestMapping("/api/days")
public class DayController {
    private final DayService dayService;

    public DayController(DayService dayService) {
        this.dayService = dayService;
    }

    @GetMapping
    public ResponseEntity<List<DayDTO>> getAllDays() {
        return ResponseEntity.ok(dayService.getAllDays());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DayDTO> getDayById(@PathVariable Long id) {
        DayDTO day = dayService.getDayById(id);
        if (day != null) {
            return ResponseEntity.ok(day);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<DayDTO> createDay(@RequestBody DayDTO dayDTO) {
        DayDTO createdDay = dayService.createDay(dayDTO);
        return ResponseEntity.ok(createdDay);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DayDTO> updateDay(
        @PathVariable Long id, 
        @RequestBody DayDTO dayDTO) {
            DayDTO updatedDay = dayService.updateDay(id, dayDTO);
            if (updatedDay != null) {
                return ResponseEntity.ok(updatedDay);
            }
            return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DayDTO> deleteDay(@PathVariable Long id) {
        dayService.deleteDay(id);
        return ResponseEntity.noContent().build();
    }
}
