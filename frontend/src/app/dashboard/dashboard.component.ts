import { Component, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { WorkoutService } from '../services/workout.service';
import { CommonModule } from '@angular/common';
import { DayService } from '../services/day.service';
import { LogService } from '../services/log.service';
import { lastValueFrom, Observable } from 'rxjs';

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
  workoutLogged: boolean = false;
  weeklyProgress: any = null;
  recentActivity: any = null;
  userName = signal('Sidik');

  constructor(
    private workoutService: WorkoutService,
    private dayService: DayService,
    private logService: LogService
  ) {}

  async ngOnInit(): Promise<void> {
    this.isLoading = true;

    try {
      // Load all data simultaneously
      const [logsExist, splits, weeklyLogs] = await Promise.all([
        this.checkIfLogsExistForToday(),
        this.loadWorkoutSplits(),
        this.loadWeeklyLogs(),
      ]);

      this.workoutLogged = logsExist;
      this.splits = splits;
      this.recentActivity = this.getRecentActivity(weeklyLogs);

      if (this.splits.length > 0) {
        await this.loadNextWorkout(this.splits[0].id);
      }

      this.weeklyProgress = this.calculateWeeklyProgress(weeklyLogs);
    } catch (error) {
      this.handleError('Error loading dashboard', error);
    } finally {
      this.isLoading = false;
    }
  }

  private async loadWorkoutSplits(): Promise<any[]> {
    return lastValueFrom(this.workoutService.getWorkoutSplits());
  }

  private async loadWeeklyLogs(): Promise<any[]> {
    return lastValueFrom(this.logService.getLogsForCurrentWeek());
  }

  private async checkIfLogsExistForToday(): Promise<boolean> {
    const todayDate = new Date().toISOString().split('T')[0];
    try {
      return await lastValueFrom(this.logService.checkLogsForDate(todayDate));
    } catch (error) {
      console.error('Error checking logs for today:', error);
      return false;
    }
  }

  private async loadNextWorkout(splitId: number): Promise<void> {
    try {
      const days: any[] = await lastValueFrom(this.getWorkoutDays(splitId)); // Assuming `days` is an array of objects
      const todayId = this.getTodayId();
      const sortedDays = days.sort((a: any, b: any) => a.id - b.id);
  
      this.daysToWorkout = sortedDays;
      this.nextWorkout = sortedDays.find((day: any) => day.id === todayId) || null; // Explicitly type `day`
      this.isWorkoutToday = Boolean(this.nextWorkout);
    } catch (error) {
      this.handleError('Error fetching workout days', error);
    }
  }  

  private getWorkoutDays(splitId: number): Observable<any> {
    return this.dayService.getDaysWithWorkouts(splitId);
  }

  private getRecentActivity(logs: any[]): any[] {
    return logs
      .filter((log) => log.workoutDate)
      .sort(
        (a, b) =>
          new Date(b.workoutDate).getTime() - new Date(a.workoutDate).getTime()
      )
      .slice(0, 5);
  }

  private calculateWeeklyProgress(logs: any[]): any {
    const weeklyTarget = this.daysToWorkout.length || 1; // Avoid division by zero
    const completedWorkouts = this.filterDaysWorked(logs).length;
    return {
      completed: completedWorkouts,
      target: weeklyTarget,
      percentage: ((completedWorkouts / weeklyTarget) * 100).toFixed(2),
    };
  }

  private filterDaysWorked(logs: any[]): string[] {
    const uniqueDates = new Set<string>();

  logs.forEach(log => {
    // Assuming the log has a 'date' field and the date is in a standard format like '2024-12-17T08:00:00Z'
    const date = log.workoutDate.split('T')[0]; // Extract the date part (YYYY-MM-DD)
    uniqueDates.add(date);
  });

  return Array.from(uniqueDates);
  }

  private getTodayId(): number {
    const today = new Date().getDay();
    return today === 0 ? 7 : today; // Sunday as 7
  }

  private handleError(message: string, error: any): void {
    console.error(message, error);
  }
}