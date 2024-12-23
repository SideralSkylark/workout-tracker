package com.zorinserver.workout_tracker.mapper;

import com.zorinserver.workout_tracker.dto.DayDTO;
import com.zorinserver.workout_tracker.dto.LogByIdDTO;
import com.zorinserver.workout_tracker.entity.Day;
import com.zorinserver.workout_tracker.entity.WorkoutLog;

public class WorkoutLogMapper {
    public static DayDTO toDTO(Day day) {
        return DayDTO.builder()
                .id(day.getId())
                .name(day.getName())
                .build();
    }

    public static Day toEntity(DayDTO dayDTO) {
        Day day = new Day();
        day.setId(dayDTO.getId());
        day.setName(dayDTO.getName());
        return day;
    }

    public static LogByIdDTO toLogByIdDTO(WorkoutLog workoutLog) {
        return LogByIdDTO.builder()
                .id(workoutLog.getId())
                .name(workoutLog.getExercise().getName())
                .workoutDate(workoutLog.getWorkoutDate())
                .sets(workoutLog.getCompletedSets())
                .reps(workoutLog.getCompletedReps())
                .build();

    }
    public static WorkoutLog toEntity(LogByIdDTO logByIdDTO) {
        WorkoutLog workoutLog = new WorkoutLog();
        return workoutLog;
    }
}
