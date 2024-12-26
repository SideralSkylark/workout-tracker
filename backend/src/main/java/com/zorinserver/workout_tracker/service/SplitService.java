package com.zorinserver.workout_tracker.service;

import com.zorinserver.workout_tracker.dto.SplitResponseDTO;
import com.zorinserver.workout_tracker.entity.Split;
import com.zorinserver.workout_tracker.mapper.SplitMapper;
import com.zorinserver.workout_tracker.repository.SplitRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<SplitResponseDTO> getSplitsByUserId(Long userId) {
        return splitRepository.findByUserId(userId)
                .stream()
                .map(SplitMapper::toSplitResponseDTO)
                .collect(Collectors.toList());
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
