package com.zorinserver.workout_tracker.repository;

import com.zorinserver.workout_tracker.dto.ExerciseDayDTO;
import com.zorinserver.workout_tracker.entity.Day;
import com.zorinserver.workout_tracker.entity.SplitSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SplitScheduleRepository extends JpaRepository<SplitSchedule, Long> {
    
    @Query("SELECT new com.zorinserver.workout_tracker.dto.ExerciseDayDTO(e.name, se.sets, se.reps, d.name) " +
       "FROM SplitSchedule ss " +
       "JOIN ss.split s " +
       "JOIN ss.day d " +
       "JOIN ss.exercise e " +
       "JOIN SplitExercise se ON se.split = ss.split AND se.exercise = ss.exercise " +
       "WHERE d.id = :dayId AND s.id = :splitId")
    List<ExerciseDayDTO> findExercisesByDayIdAndSplitId(@Param("dayId") Long dayId, @Param("splitId") Long splitId);

    @Query("SELECT DISTINCT s.day FROM SplitSchedule s WHERE s.split.id = :splitId")
    List<Day> findDaysWithWorkoutsBySplitId(@Param("splitId") Long splitId);


    List<SplitSchedule> findBySplitId(Long splitId);
    List<SplitSchedule> findByDayId(Long dayId);
}
