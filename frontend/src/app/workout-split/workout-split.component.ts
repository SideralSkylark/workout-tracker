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
  styleUrls: ['./workout-split.component.css']
})
export class WorkoutSplitComponent {
  splits: any[] = [];
  exercises: any[] = [];
  days: any[] = [];
  daysToExercise: { day: string, exercises: any[] }[] = [];
  newSplitName: string = '';
  currentSplitId: number = 0;
  isLoading: boolean = true;
  newExercise: { splitId: number, dayId: number, exerciseName: string; sets: number; reps: number; } = {
    splitId: 0,
    dayId: 0,
    exerciseName: '',
    reps: 0,
    sets: 0
  };

  constructor(
    private workoutService: WorkoutService, 
    private exerciseService: ExerciseService,
    private dayService: DayService) {}

  ngOnInit(): void {
    this.loadSplitsAndDays(); // Start loading splits and days together
  }

  loadSplitsAndDays(): void {
    this.isLoading = true;
    forkJoin([
      this.workoutService.getWorkoutSplits(),
      this.dayService.getDays()
    ]).subscribe({
      next: ([splits, days]) => {
        this.splits = splits;
        this.days = days;
        if (this.splits.length > 0) {
          this.setCurrentSplitId();
          this.loadExercises(this.currentSplitId); 
        }
        this.isLoading = false; // Set loading to false once data is loaded
      },
      error: (error) => {
        console.error('Error loading splits or days', error);
        this.isLoading = false; // Handle error and stop loading indicator
      }
    });
  }
  

  setCurrentSplitId(): void {
    this.currentSplitId = this.splits[0].id;
  }

  createSplit(): void {
    if (this.newSplitName.trim()) {
      const splitData = { name: this.newSplitName };
      this.workoutService.createSplit(splitData).subscribe(() => {
        this.newSplitName = '';
        this.loadSplitsAndDays(); // Reload splits and days after creating a new split
      });
    }
  }

  deleteSplit(splitId: number): void {
    this.workoutService.deleteSplit(splitId).subscribe(() => {
      this.loadSplitsAndDays(); // Reload splits and days after deleting a split
    });
  }

  openExerciseModal(): void {
    this.newExercise.splitId = this.currentSplitId;
    const modalElement = document.getElementById('addExerciseModal');
    if (modalElement) {
      const modal = new bootstrap.Modal(modalElement);
      modal.show();
    }
  }

  addExercise(): void {
    const exerciseData = this.newExercise;
    this.exerciseService.addExercise(exerciseData).subscribe(() => {
      this.loadExercises(this.currentSplitId);
      this.newExercise = { splitId: 0, dayId: 0, exerciseName: '', reps: 0, sets: 0 }; // Reset form
    });
  }

  loadExercises(splitId: number): void {
    console.log("Loading exercises for splitId:", splitId);
    
    // Set loading flag to true before making requests
    this.isLoading = true;
    this.daysToExercise = []; // Clear previous exercises data
  
    const exerciseRequests = this.days.map((day) => {
      return this.exerciseService.getExercisesByDayAndSplit(day.id, splitId);
    });
  
    forkJoin(exerciseRequests).subscribe({
      next: (exercisesResults) => {
        exercisesResults.forEach((exercises, index) => {
          const day = this.days[index];
          if (exercises.length > 0) {
            this.daysToExercise.push({
              day: day.name,
              exercises: exercises.map((exercise) => ({
                id: exercise.id,
                exerciseName: exercise.exerciseName,
                sets: exercise.sets,
                reps: exercise.reps
              }))
            });
          }
        });
        console.log("Days to Exercise:", this.daysToExercise);
        console.log(this.exercises);
      },
      error: (error) => {
        console.error("Error loading exercises:", error);
      },
      complete: () => {
        // Set loading flag to false once the request completes
        this.isLoading = false;
      }
    });
  }  
}
