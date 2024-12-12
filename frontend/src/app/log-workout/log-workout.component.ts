import { Component } from '@angular/core';
import { WorkoutService } from '../services/workout.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-log-workout',
  imports: [CommonModule],
  templateUrl: './log-workout.component.html',
  styleUrl: './log-workout.component.css'
})
export class LogWorkoutComponent {
  todaySplit: any = null;
  exercises: any[] = [];

  constructor(private workoutService: WorkoutService) {}

  ngOnInit(): void {
    this.loadTodayWorkout();
  }

  loadTodayWorkout(): void {
    this.workoutService.getSplitSchedule().subscribe((schedule) => {
      const todayId = this.getTodayId();
      this.todaySplit = schedule.find((s: any) => s.day_id === todayId);
      if (this.todaySplit) {
        this.loadExercises(this.todaySplit.split_id);
      }
    });
  }

  loadExercises(splitId: number): void {
    this.workoutService.getWorkoutSplits().subscribe((splits) => {
      const split = splits.find((s: any) => s.id === splitId);
      if (split) {
        this.exercises = split.splitExercises || [];
      }
    });
  }

  getTodayId(): number {
    const today = new Date().getDay(); // 0=Sunday, 1=Monday, ..., 6=Saturday
    return today === 0 ? 7 : today; // Map Sunday to 7
  }
}
