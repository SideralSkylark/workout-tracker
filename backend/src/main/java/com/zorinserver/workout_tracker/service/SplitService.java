package com.zorinserver.workout_tracker.service;

import com.zorinserver.workout_tracker.entity.Split;
import com.zorinserver.workout_tracker.repository.SplitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SplitService {
    private final SplitRepository splitRepository;

    public SplitService(SplitRepository splitRepository) {
        this.splitRepository = splitRepository;
    }

    public List<Split> getAllSplits() {
        return splitRepository.findAll();
    }

    public Split getSplitById(Long id) {
        return splitRepository.findById(id).orElseThrow(() -> new RuntimeException("Split not found"));
    }

    public Split createSplit(Split split) {
        return splitRepository.save(split);
    }

    public Split updateSplit(Long id, Split updatedSplit) {
        Split split = getSplitById(id);
        split.setName(updatedSplit.getName());
        return splitRepository.save(split);
    }

    public void deleteSplit(Long id) {
        splitRepository.deleteById(id);
    }
}
