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

import com.zorinserver.workout_tracker.dto.CreateSplitDTO;
import com.zorinserver.workout_tracker.dto.SplitResponseDTO;
import com.zorinserver.workout_tracker.entity.Split;
import com.zorinserver.workout_tracker.entity.User;
import com.zorinserver.workout_tracker.repository.UserRepository;
import com.zorinserver.workout_tracker.service.SplitService;

@RestController
@RequestMapping("/api/splits")
public class SplitController {
    private final SplitService splitService;
    private final UserRepository userRepository;

    public SplitController(
        SplitService splitService,
        UserRepository userRepository) {
        this.splitService = splitService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<Split>> getAllSplits() {
        return ResponseEntity.ok(splitService.getAllSplits());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Split> getSplitById(@PathVariable Long id) {
        return ResponseEntity.ok(splitService.getSplitById(id));
    }

    @GetMapping("/userId/{userId}")
    public List<SplitResponseDTO> getSplitsByUserId(@PathVariable Long userId) {
        return splitService.getSplitsByUserId(userId);
    }

    @PostMapping
    public ResponseEntity<Split> createSplit(@RequestBody CreateSplitDTO createSplitDTO) {
        User user = userRepository.findById(Long.parseLong(createSplitDTO.getUser_id()))
                .orElseThrow(() -> new RuntimeException("User not found"));
        Split split = new Split();
        split.setName(createSplitDTO.getName());
        split.setUser(user);
        return ResponseEntity.ok(splitService.createSplit(split));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Split> updateSplit(@PathVariable Long id, @RequestBody Split split) {
        return ResponseEntity.ok(splitService.updateSplit(id, split));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSplit(@PathVariable Long id) {
        splitService.deleteSplit(id);
        return ResponseEntity.noContent().build();
    }
}
