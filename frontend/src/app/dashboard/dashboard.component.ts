import { Component, OnInit } from '@angular/core';
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
  userName: string = '';
  userId: number | null = null;

  constructor(
    private workoutService: WorkoutService,
    private dayService: DayService,
    private logService: LogService
  ) {}

  async ngOnInit(): Promise<void> {
    this.isLoading = true;

    try {
      this.initializeUserDetails();
      this.loadDashboardData();
    } catch (error) {
      this.handleError('Error loading dashboard', error);
    } finally {
      this.isLoading = false;
    }
  }

  private initializeUserDetails(): void {
    this.userName = localStorage.getItem('username') || '';
    this.userId = parseInt(localStorage.getItem('user_id') || '0', 10) || null;
    const cachedSplits = localStorage.getItem('splits');
    this.splits = cachedSplits ? JSON.parse(cachedSplits) : [];
  }

  private async loadDashboardData(): Promise<void> {
    if (this.splits.length > 0) {
      const selectedSplitId = this.splits[0].id;

      const [logsExist, weeklyLogs] = await Promise.all([
        this.checkIfLogsExistForToday(),
        this.loadWeeklyLogs(selectedSplitId),
      ]);

      this.workoutLogged = logsExist;
      this.recentActivity = this.getRecentActivity(weeklyLogs);

      await this.loadNextWorkout(selectedSplitId);

      this.weeklyProgress = this.calculateWeeklyProgress(weeklyLogs);
    } else {
      console.warn('No splits found in cache.');
    }
  }

  private async loadWorkoutSplits(user_id: number): Promise<any[]> {
    return lastValueFrom(this.workoutService.getSplitsByUserId(user_id));
  }

  private async loadWeeklyLogs(splitId: number): Promise<any[]> {
    return lastValueFrom(this.logService.getLogsForCurrentWeek(splitId));
  }

  private async checkIfLogsExistForToday(): Promise<boolean> {
    const todayDate = new Date().toISOString().split('T')[0];
    const split_id = this.splits[0].id;
    try {
      return await lastValueFrom(this.logService.checkLogsForDateAndSplit(todayDate, split_id));
    } catch (error) {
      console.error('Error checking logs for today:', error);
      return false;
    }
  }

  private async loadNextWorkout(splitId: number): Promise<void> {
    try {
      const days: any[] = await lastValueFrom(this.getWorkoutDays(splitId));
      const todayId = this.getTodayId();
      const sortedDays = days.sort((a: any, b: any) => a.id - b.id);
  
      this.daysToWorkout = sortedDays;
  
      if (this.workoutLogged) {
        // If a workout is logged today, find the next workout day
        const nextDayIndex = sortedDays.findIndex((day) => day.id > todayId);
        this.nextWorkout =
          nextDayIndex !== -1
            ? sortedDays[nextDayIndex]
            : sortedDays[0]; // Loop back to the first day if none found
      } else {
        // If no workout logged, set today's workout if applicable
        this.nextWorkout = sortedDays.find((day: any) => day.id === todayId) || null;
      }
  
      this.isWorkoutToday =
        this.nextWorkout && this.nextWorkout.id === todayId && !this.workoutLogged;
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
      .sort((a, b) => new Date(b.workoutDate).getTime() - new Date(a.workoutDate).getTime())
      .slice(0, 5);
  }

  private calculateWeeklyProgress(logs: any[]): any {
    const weeklyTarget = this.daysToWorkout.length || 1;
    const completedWorkouts = this.filterDaysWorked(logs).length;
    return {
      completed: completedWorkouts,
      target: weeklyTarget,
      percentage: ((completedWorkouts / weeklyTarget) * 100).toFixed(2),
    };
  }

  private filterDaysWorked(logs: any[]): string[] {
    const uniqueDates = new Set(logs.map((log) => log.workoutDate.split('T')[0]));
    return Array.from(uniqueDates);
  }

  private getTodayId(): number {
    const today = new Date().getDay();
    return today === 0 ? 7 : today; 
  }

  private handleError(message: string, error: any): void {
    console.error(message, error);
  }
}