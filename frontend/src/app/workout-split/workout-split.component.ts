import { Component } from '@angular/core';
import { WorkoutService } from '../services/workout.service';
import { ExerciseService } from '../services/exercise.service';
import { DayService } from '../services/day.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { lastValueFrom } from 'rxjs';
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

  private userId: number | null = null;

  constructor(
    private workoutService: WorkoutService,
    private exerciseService: ExerciseService,
    private dayService: DayService
  ) {}

  async ngOnInit(): Promise<void> {
    this.isLoading = true;
    try {
      this.initializeUserDetails();
      await this.loadSplitsAndDays();
    } catch (error) {
      console.error('Error initializing component:', error);
    } finally {
      this.isLoading = false;
    }
  }

  private initializeUserDetails(): void {
    this.userId = parseInt(localStorage.getItem('user_id') || '0', 10) || null;
  }

  private async loadSplitsAndDays(): Promise<void> {
    if (!this.userId) return;

    try {
      this.splits = await this.loadWorkoutSplits(this.userId);
      this.days = await this.loadDays();

      if (this.splits.length > 0) {
        this.currentSplitId = this.splits[0].id;
        await this.loadExercises(this.currentSplitId);
      }
    } catch (error) {
      console.error('Error loading splits or days:', error);
    }
  }

  private async loadWorkoutSplits(userId: number): Promise<any[]> {
    return lastValueFrom(this.workoutService.getSplitsByUserId(userId));
  }

  private async loadDays(): Promise<any[]> {
    return lastValueFrom(this.dayService.getDays());
  }

  async loadExercises(splitId: number): Promise<void> {
    this.isLoading = true;
    this.daysToExercise = [];

    try {
      const exerciseRequests = this.days.map((day) =>
        lastValueFrom(this.exerciseService.getExercisesByDayAndSplit(day.id, splitId))
      );
      const results = await Promise.all(exerciseRequests);

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
    } catch (error) {
      console.error('Error loading exercises:', error);
    } finally {
      this.isLoading = false;
    }
  }

  async createSplit(): Promise<void> {
    if (this.newSplitName.trim() && this.userId) {
      const splitData = { user_id: this.userId, name: this.newSplitName };

      try {
        await lastValueFrom(this.workoutService.createSplit(splitData));
        this.newSplitName = '';
        await this.loadSplitsAndDays();
      } catch (error) {
        console.error('Error creating split:', error);
      }
    }
  }

  async deleteSplit(splitId: number): Promise<void> {
    try {
      await lastValueFrom(this.workoutService.deleteSplit(splitId));
      await this.loadSplitsAndDays();
    } catch (error) {
      console.error('Error deleting split:', error);
    }
  }

  async addExercise(): Promise<void> {
    try {
      await lastValueFrom(this.exerciseService.addExercise(this.newExercise));
      await this.loadExercises(this.currentSplitId);
      this.resetExerciseForm();
    } catch (error) {
      console.error('Error adding exercise:', error);
    }
  }

  async deleteExercise(exerciseId: number): Promise<void> {
    try {
      await lastValueFrom(this.exerciseService.deleteExercise(exerciseId));
      await this.loadExercises(this.currentSplitId);
    } catch (error) {
      console.error('Error deleting exercise:', error);
    }
  }

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
