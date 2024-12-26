import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { LogService } from '../services/log.service';
import { lastValueFrom } from 'rxjs';
import { WorkoutService } from '../services/workout.service';
import { DayService } from '../services/day.service';

@Component({
  selector: 'app-progress',
  imports: [CommonModule, FormsModule],
  templateUrl: './progress.component.html',
  styleUrls: ['./progress.component.css'],
})
export class ProgressComponent {
  isLoading: boolean = true;
  logs: any[] = [];
  weeklyLogs: any[] = [];
  daysWorked: string[] = [];
  daysMissed: string[] = [];
  daysLeft: string[] = [];
  groupedLogs: { date: string; logs: any[] }[] = [];
  filteredLogs: { date: string; logs: any[] }[] = [];
  filterText: string = '';
  filterTime: string = 'all';

  private userId: number | null = null;
  private daysToWorkout: string[] = [];
  private splits: any[] = [];

  constructor(
    private logService: LogService,
    private workoutService: WorkoutService,
    private dayService: DayService
  ) {}

  async ngOnInit(): Promise<void> {
    this.isLoading = true;
    try {
      this.initializeUserDetails();
      await this.loadWorkoutData();
    } catch (error) {
      this.handleError('Error loading dashboard', error);
    } finally {
      this.isLoading = false;
    }
  }

  private initializeUserDetails(): void {
    const userId = localStorage.getItem('user_id');
    this.userId = userId ? parseInt(userId, 10) : null;
  }

  private async loadWorkoutData(): Promise<void> {
    if (!this.userId) return;

    this.splits = await this.loadWorkoutSplits(this.userId);
    if (this.splits.length === 0) return;

    const splitId = this.splits[0].id;
    await this.loadDaysToWorkout(splitId);

    this.logs = await this.loadLogsBySplitId(splitId);
    this.weeklyLogs = await this.loadWeeklyLogs(splitId);

    this.calculateWeeklyProgress();
    this.groupLogsByDate();
    this.applyFilters();
  }

  private async loadWorkoutSplits(userId: number): Promise<any[]> {
    return lastValueFrom(this.workoutService.getSplitsByUserId(userId));
  }

  private async loadDaysToWorkout(splitId: number): Promise<void> {
    try {
      const days = await lastValueFrom(
        this.dayService.getDaysWithWorkouts(splitId)
      );
      this.daysToWorkout = days.map((day: { name: string }) => day.name);
    } catch (error) {
      this.handleError('Error loading days to workout', error);
    }
  }

  private async loadLogsBySplitId(splitId: number): Promise<any[]> {
    return lastValueFrom(this.logService.getLogsBySplitId(splitId));
  }

  private async loadWeeklyLogs(splitId: number): Promise<any[]> {
    return lastValueFrom(this.logService.getLogsForCurrentWeek(splitId));
  }

  private calculateWeeklyProgress(): void {
    this.daysWorked = this.getDaysWorked(this.weeklyLogs);
    this.daysMissed = this.getDaysMissed(this.daysWorked, this.daysToWorkout);
    this.daysLeft = this.getDaysLeft(this.daysWorked, this.daysMissed);
  }

  private groupLogsByDate(): void {
    const grouped: { [key: string]: any[] } = {};
    this.logs.forEach((log) => {
      const date = new Date(log.workoutDate).toLocaleDateString('en-US');
      grouped[date] = grouped[date] || [];
      grouped[date].push(log);
    });
    this.groupedLogs = Object.keys(grouped).map((date) => ({
      date,
      logs: grouped[date],
    }));
  }

  applyFilters(): void {
    this.filteredLogs = this.groupedLogs
      .filter((group) =>
        this.filterText
          ? group.logs.some((log) =>
              log.name.toLowerCase().includes(this.filterText.toLowerCase())
            )
          : true
      )
      .filter((group) => {
        if (this.filterTime === 'all') return true;

        const now = new Date();
        const startDate =
          this.filterTime === 'week'
            ? new Date(now.setDate(now.getDate() - now.getDay()))
            : new Date(now.setMonth(now.getMonth() - 1));
        return new Date(group.date) >= startDate;
      });
  }

  private getDaysWorked(logs: any[]): string[] {
    return Array.from(
      new Set(
        logs.map((log) =>
          new Date(log.workoutDate).toLocaleDateString('en-US', {
            weekday: 'long',
          })
        )
      )
    );
  }

  private getDaysMissed(workedDays: string[], targetDays: string[]): string[] {
    const today = new Date().toLocaleDateString('en-US', { weekday: 'long' });
    const pastDays = targetDays.slice(0, targetDays.indexOf(today) + 1);
    return pastDays.filter((day) => !workedDays.includes(day));
  }

  private getDaysLeft(workedDays: string[], missedDays: string[]): string[] {
    const today = new Date().toLocaleDateString('en-US', { weekday: 'long' });
    const todayIndex = this.daysToWorkout.indexOf(today);

    if (todayIndex === -1) return [];

    return this.daysToWorkout
      .slice(todayIndex + 1)
      .filter(
        (day) => !workedDays.includes(day) && !missedDays.includes(day)
      );
  }

  private handleError(message: string, error: any): void {
    console.error(message, error);
  }
}
