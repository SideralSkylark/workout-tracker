import { Component } from '@angular/core';
import { WorkoutService } from '../services/workout.service';
import { CommonModule } from '@angular/common';
import { ExerciseService } from '../services/exercise.service';
import { FormsModule } from '@angular/forms';
import { LogService } from '../services/log.service';

@Component({
  selector: 'app-log-workout',
  imports: [CommonModule, FormsModule],
  templateUrl: './log-workout.component.html',
  styleUrl: './log-workout.component.css'
})
export class LogWorkoutComponent {
  isLoading: boolean = true;
  split: any[] = [];
  splitId: any = 0 || null;
  exercises: any[] = [];

  constructor(
    private workoutService: WorkoutService,
    private exerciseService: ExerciseService,
    private logService: LogService) {}

  ngOnInit(): void {
    this.loadSplitAndWorkout();
  }

  private getTodayId(): number {
    const today = new Date().getDay();
    return today === 0 ? 7 : today;
  }

  private loadSplitAndWorkout(): void {
    this.workoutService.getWorkoutSplits().subscribe((splits) => {
      this.splitId = splits[0].id;
      this.loadTodayWorkout();
      console.log(this.splitId);
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
      next: (response) => {
        console.log('Workout logged successfully:', response);
        alert('Workout logged successfully!');
        this.exercises = []; // Clear form after submission
      },
      error: (error) => {
        console.error('Error logging workout:', error);
        alert('An error occurred while logging your workout.');
      },
    });
  }
}
