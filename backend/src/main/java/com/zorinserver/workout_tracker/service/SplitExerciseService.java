package com.zorinserver.workout_tracker.service;

import com.zorinserver.workout_tracker.entity.SplitExercise;
import com.zorinserver.workout_tracker.repository.SplitExerciseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SplitExerciseService {
    private final SplitExerciseRepository splitExerciseRepository;

    public SplitExerciseService(SplitExerciseRepository splitExerciseRepository) {
        this.splitExerciseRepository = splitExerciseRepository;
    }

    public List<SplitExercise> getAllSplitExercises() {
        return splitExerciseRepository.findAll();
    }

    public SplitExercise getSplitExerciseById(Long id) {
        return splitExerciseRepository.findById(id).orElseThrow(() -> new RuntimeException("SplitExercise not found"));
    }

    public SplitExercise createSplitExercise(SplitExercise splitExercise) {
        return splitExerciseRepository.save(splitExercise);
    }

    public void deleteSplitExercise(Long id) {
        splitExerciseRepository.deleteById(id);
    }
}
