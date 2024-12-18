import { Component } from '@angular/core';
import { WorkoutService } from '../services/workout.service';
import { CommonModule } from '@angular/common';
import { ExerciseService } from '../services/exercise.service';
import { FormsModule } from '@angular/forms';
import { LogService } from '../services/log.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-log-workout',
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './log-workout.component.html',
  styleUrl: './log-workout.component.css'
})
export class LogWorkoutComponent {
  isLoading: boolean = true;
  split: any[] = [];
  splitId: any = 0 || null;
  exercises: any[] = [];
  logsExist: boolean = false;

  constructor(
    private workoutService: WorkoutService,
    private exerciseService: ExerciseService,
    private logService: LogService) {}

  ngOnInit(): void {
    this.checkIfLogsExistForToday();
  }

  private getTodayId(): number {
    const today = new Date().getDay();
    return today === 0 ? 7 : today;
  }

  private checkIfLogsExistForToday(): void {
    const todayDate = new Date().toISOString().split('T')[0];
    this.logService.checkLogsForDate(todayDate).subscribe({
      next: (logsExist: boolean) => {
        this.logsExist = logsExist;
        this.loadSplitAndWorkout();
      },
      error: (error) => {
        console.error('Error checking logs for today:', error);
        this.isLoading = false;
      },
    });
  }

  private loadSplitAndWorkout(): void {
    this.workoutService.getWorkoutSplits().subscribe((splits) => {
      this.splitId = splits[0].id;
      this.loadTodayWorkout();
      this.isLoading = false;
    });
  }

  loadTodayWorkout(): void {
    this.exerciseService.getExercisesByDayAndSplit(this.getTodayId(), this.splitId).subscribe((exercises) => {
      this.exercises = exercises.map((exercise: any) => ({
        id: exercise.id, // Include exercise ID for later submission
        name: exercise.exerciseName,
        sets: exercise.sets,
        reps: exercise.reps,
        loggedReps: Array(exercise.sets).fill(null) // Initialize reps logging
      }));
    });
  }  

  onSubmit(): void {
    const workoutLogs = this.exercises.map((exercise) => ({
      workoutDate: new Date().toISOString().split('T')[0],
      exerciseId: exercise.id, // Include exercise ID in the payload
      splitId: this.splitId,
      completedSets: exercise.loggedReps.filter((reps: any) => reps !== null).length,
      completedReps: exercise.loggedReps.reduce((sum: number, reps: number | null) => sum + (reps || 0), 0),
      workoutSets: exercise.loggedReps.map((reps: number | null, index: number) => ({
        setNumber: index + 1,
        completedReps: reps || 0,
      })),
      notes: '',
    }));

    console.log('Payload being sent:', workoutLogs);
    
    this.logService.logWorkouts(workoutLogs).subscribe({
      next: (savedLogs: any[]) => {
        console.log('Workout logged successfully:', savedLogs);
        alert('Workout logged successfully!');
        this.exercises = [];
      },
      error: (error) => {
        console.error('Error logging workout:', error);
        alert('An error occurred while logging your workout.');
      },
    });
  }
}
