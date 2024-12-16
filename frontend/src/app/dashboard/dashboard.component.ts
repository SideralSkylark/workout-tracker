import { Component, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { WorkoutService } from '../services/workout.service';
import { CommonModule } from '@angular/common';
import { DayService } from '../services/day.service';
import { Observable, forkJoin } from 'rxjs';

@Component({
  selector: 'app-dashboard',
  imports: [RouterLink, CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
  standalone: true,
})
export class DashboardComponent implements OnInit {
  splits: any[] = [];
  daysToWorkout: any[] = [];
  nextWorkout: any = null;
  isWorkoutToday: boolean = false;
  isLoading: boolean = true;
  weeklyProgress: any = null;
  recentActivity: any = null;
  userName = signal('Sidik');

  constructor(
    private workoutService: WorkoutService,
    private dayService: DayService
  ) {}

  ngOnInit(): void {
    this.initializeDashboard();
  }

  private initializeDashboard(): void {
    this.isLoading = true;

    forkJoin({
      splits: this.workoutService.getWorkoutSplits(),
      logs: this.workoutService.getWorkoutLogs(),
    }).subscribe({
      next: ({ splits, logs }) => {
        this.splits = splits;
        this.weeklyProgress = this.calculateWeeklyProgress(logs);
        this.loadRecentActivity(logs);

        if (this.splits.length > 0) {
          this.loadNextWorkout(this.splits[0].id);
        }

        this.isLoading = false;
      },
      error: (error) => this.handleError('Error loading dashboard data', error),
    });
  }

  /**
   * Loads the most recent activity logs sorted by date.
   * @param logs - List of all workout logs.
   */
  private loadRecentActivity(logs: any[]): void {
    const sortedLogs = logs.sort(
      (a, b) => new Date(b.workoutDate).getTime() - new Date(a.workoutDate).getTime()
    );
    this.recentActivity = sortedLogs.slice(0, 5);
  }

  private loadNextWorkout(splitId: number): void {
    this.getWorkoutDays(splitId).subscribe({
      next: (days: any[]) => {
        const todayId = this.getTodayId();
        const sortedDays = days.sort((a: any, b: any) => a.id - b.id);
        const upcomingWorkout = sortedDays.find((day) => day.id === todayId);

        this.nextWorkout = upcomingWorkout || null;
        this.isWorkoutToday = Boolean(this.nextWorkout);
      },
      error: (error) => this.handleError('Error fetching workout days', error),
    });
  }

  private getWorkoutDays(splitId: number): Observable<any> {
    return this.dayService.getDaysWithWorkouts(splitId);
  }

  private calculateWeeklyProgress(logs: any[]): any {
    const weeklyTarget = 5;
    const completedWorkouts = logs.filter((log) =>
      this.isThisWeek(new Date(log.workout_date))
    );
    return {
      completed: completedWorkouts.length,
      target: weeklyTarget,
    };
  }

  private isThisWeek(date: Date): boolean {
    const now = new Date();
    const startOfWeek = this.getStartOfWeek(now);
    const endOfWeek = new Date(startOfWeek);
    endOfWeek.setDate(startOfWeek.getDate() + 6);

    return date >= startOfWeek && date <= endOfWeek;
  }

  private getTodayId(): number {
    const today = new Date().getDay();
    return today === 0 ? 7 : today;
  }

  private getStartOfWeek(date: Date): Date {
    const start = new Date(date);
    start.setDate(date.getDate() - date.getDay() + 1);
    return start;
  }

  private handleError(message: string, error: any): void {
    console.error(message, error);
    this.isLoading = false;
  }
}
