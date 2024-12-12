import { Component, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { WorkoutService } from '../services/workout.service';
import { CommonModule } from '@angular/common';
import { DayService } from '../services/day.service';
import { Observable, forkJoin } from 'rxjs';
import { map } from 'rxjs/operators';

@Component({
  selector: 'app-dashboard',
  imports: [RouterLink, CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
  standalone: true
})
export class DashboardComponent implements OnInit {
  split: any[] = [];
  daysToWorkout: any[] = [];
  nextWorkout: any = null;
  isLoading: boolean = true;
  weeklyProgress: any = null;
  recentActivity: any = null;
  userName = signal('Sidik');

  constructor(
    private workoutService: WorkoutService,
    private dayService: DayService
  ) {}

  ngOnInit(): void {
    this.loadDashboardData();
  }

  private loadDashboardData(): void {
    this.isLoading = true;

    forkJoin({
      splits: this.workoutService.getWorkoutSplits(),
      logs: this.workoutService.getWorkoutLogs()
    }).subscribe({
      next: ({ splits, logs }) => {
        this.split = splits;
        this.weeklyProgress = this.calculateWeeklyProgress(logs);
        this.recentActivity = logs.slice(0, 5);

        if (this.split.length > 0) {
          this.loadNextWorkout(this.split[0].id);
        }

        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading dashboard data:', error);
        this.isLoading = false;
      }
    });
  }

  private getTodayId(): number {
    const today = new Date().getDay();
    return today === 0 ? 7 : today;
  }

  private loadNextWorkout(splitId: number): void {
    this.getDaysToWorkout(splitId).subscribe({
      next: (daysWithWorkouts: any[]) => {
        const todayId = this.getTodayId();
        const sortedDays = daysWithWorkouts.sort((a: any, b: any) => a.id - b.id);
        const nextWorkoutDay = sortedDays.find(day => day.id >= todayId) || sortedDays[0];

        this.nextWorkout = nextWorkoutDay;
      },
      error: (error) => {
        console.error('Error fetching workout days:', error);
      }
    });
  }

  private getDaysToWorkout(splitId: number): Observable<any> {
    return this.dayService.getDaysWithWorkouts(splitId);
  }

  private calculateWeeklyProgress(logs: any[]): any {
    const weeklyTarget = 5;
    const completedWorkouts = logs.filter(log => this.isThisWeek(log.workout_date));
    return {
      completed: completedWorkouts.length,
      target: weeklyTarget
    };
  }

  private isThisWeek(date: string): boolean {
    const now = new Date();
    const logDate = new Date(date);
    const startOfWeek = new Date(now);
    startOfWeek.setDate(now.getDate() - now.getDay() + 1); // Monday
    const endOfWeek = new Date(startOfWeek);
    endOfWeek.setDate(startOfWeek.getDate() + 6);

    return logDate >= startOfWeek && logDate <= endOfWeek;
  }
}
