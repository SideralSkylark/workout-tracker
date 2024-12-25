import { Component } from '@angular/core';
import { WorkoutService } from '../services/workout.service';
import { ExerciseService } from '../services/exercise.service';
import { DayService } from '../services/day.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { forkJoin } from 'rxjs';
import * as bootstrap from 'bootstrap';

@Component({
  selector: 'app-workout-split',
  imports: [CommonModule, FormsModule],
  templateUrl: './workout-split.component.html',
  styleUrls: ['./workout-split.component.css'],
})
export class WorkoutSplitComponent {
  splits: any[] = [];
  days: any[] = [];
  daysToExercise: { day: string; exercises: any[] }[] = [];

  newSplitName: string = '';
  newExercise = {
    splitId: 0,
    dayId: 0,
    exerciseName: '',
    sets: 0,
    reps: 0,
  };

  currentSplitId: number = 0;
  isLoading: boolean = true;

  constructor(
    private workoutService: WorkoutService,
    private exerciseService: ExerciseService,
    private dayService: DayService
  ) {}

  ngOnInit(): void {
    this.loadSplitsAndDays();
  }

  loadSplitsAndDays(): void {
    this.isLoading = true;

    forkJoin([this.workoutService.getWorkoutSplits(), this.dayService.getDays()])
      .subscribe({
        next: ([splits, days]) => {
          this.splits = splits;
          this.days = days;

          if (this.splits.length > 0) {
            this.currentSplitId = this.splits[0].id;
            this.loadExercises(this.currentSplitId);
          }
        },
        error: (error) => {
          console.error('Error loading splits or days', error);
        },
        complete: () => {
          this.isLoading = false;
        },
      });
  }

  createSplit(): void {
    if (this.newSplitName.trim()) {
      const user_id = localStorage.getItem('user_id') || '';
      const splitData = { user_id: user_id, name: this.newSplitName };

      this.workoutService.createSplit(splitData).subscribe(() => {
        this.newSplitName = '';
        this.loadSplitsAndDays();
      });
    }
  }

  deleteSplit(splitId: number): void {
    this.workoutService.deleteSplit(splitId).subscribe(() => {
      this.loadSplitsAndDays();
    });
  }

  // Exercise Management
  loadExercises(splitId: number): void {
    this.isLoading = true;
    this.daysToExercise = [];

    const exerciseRequests = this.days.map((day) =>
      this.exerciseService.getExercisesByDayAndSplit(day.id, splitId)
    );

    forkJoin(exerciseRequests).subscribe({
      next: (results) => {
        results.forEach((exercises, index) => {
          const day = this.days[index];

          if (exercises.length > 0) {
            this.daysToExercise.push({
              day: day.name,
              exercises: exercises.map((exercise) => ({
                id: exercise.id,
                exerciseName: exercise.exerciseName,
                sets: exercise.sets,
                reps: exercise.reps,
              })),
            });
          }
        });
      },
      error: (error) => {
        console.error('Error loading exercises:', error);
      },
      complete: () => {
        this.isLoading = false;
      },
    });
  }

  addExercise(): void {
    const exerciseData = this.newExercise;

    this.exerciseService.addExercise(exerciseData).subscribe(() => {
      this.loadExercises(this.currentSplitId);
      this.resetExerciseForm();
    });
  }

  deleteExercise(exerciseId: number): void {
    this.exerciseService.deleteExercise(exerciseId).subscribe(() => {
      this.loadExercises(this.currentSplitId);
    });
  }

  // Utility Methods
  openExerciseModal(): void {
    this.newExercise.splitId = this.currentSplitId;

    const modalElement = document.getElementById('addExerciseModal');
    if (modalElement) {
      const modal = new bootstrap.Modal(modalElement);
      modal.show();
    }
  }

  resetExerciseForm(): void {
    this.newExercise = {
      splitId: this.currentSplitId,
      dayId: 0,
      exerciseName: '',
      sets: 0,
      reps: 0,
    };
  }
}