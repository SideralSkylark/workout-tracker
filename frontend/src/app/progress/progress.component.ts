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
  private isLoading: boolean = true;
  logs: any[] = [];
  weeklyLogs: any[] = [];
  private daysToWorkout: string[] = [];
  private splits: any[] = [];
  daysWorked: string[] = [];
  daysMissed: string[] = [];
  daysLeft: string[] = [];
  groupedLogs: { date: string; logs: any[] }[] = [];
  filteredLogs: { date: string; logs: any[] }[] = [];

  filterText: string = '';
  filterTime: string = 'all';

  constructor(
    private logService: LogService,
    private workoutService: WorkoutService,
    private dayService: DayService
  ) {}

  async ngOnInit(): Promise<void> {
    this.isLoading = true;

    try {
      const splits = await this.loadWorkoutSplits();
      this.splits = splits;

      if (this.splits.length > 0) {
        const splitId = this.splits[0].id; // Using the first split as an example
        await this.loadDaysToWorkout(splitId);

        const logs = await this.loadLogsBySplitId(splitId);
        const weeklyLogs = await this.loadWeeklyLogs();

        this.logs = logs;
        this.weeklyLogs = weeklyLogs;

        this.calculateWeeklyProgress(this.weeklyLogs);
        this.groupLogsByDate(this.logs);
        this.applyFilters(); // Initialize filtered logs
      } else {
        console.warn('No splits available to load logs.');
      }
    } catch (error) {
      this.handleError('Error loading dashboard', error);
    } finally {
      this.isLoading = false;
    }
  }

  private async loadLogsBySplitId(splitId: number): Promise<any[]> {
    return lastValueFrom(this.logService.getLogsBySplitId(splitId));
  }

  private async loadWeeklyLogs(): Promise<any[]> {
    return lastValueFrom(this.logService.getLogsForCurrentWeek());
  }

  private async loadWorkoutSplits(): Promise<any[]> {
    return lastValueFrom(this.workoutService.getWorkoutSplits());
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

  private calculateWeeklyProgress(weeklyLogs: any[]): void {
    this.daysWorked = this.getDaysWorked(weeklyLogs);
    this.daysMissed = this.getDaysMissed(this.daysWorked, this.daysToWorkout);
    this.daysLeft = this.getDaysLeft(this.daysWorked, this.daysMissed);
  }

  private groupLogsByDate(logs: any[]): void {
    const grouped: { [key: string]: any[] } = {};

    logs.forEach((log) => {
      const date = new Date(log.workoutDate).toLocaleDateString('en-US');
      if (!grouped[date]) {
        grouped[date] = [];
      }
      grouped[date].push(log);
    });

    this.groupedLogs = Object.keys(grouped).map((date) => ({
      date,
      logs: grouped[date],
    }));
  }

  applyFilters(): void {
    let filtered = [...this.groupedLogs];

    if (this.filterText) {
      filtered = filtered.map((group) => ({
        ...group,
        logs: group.logs.filter((log) =>
          log.name.toLowerCase().includes(this.filterText.toLowerCase())
        ),
      })).filter((group) => group.logs.length > 0);
    }

    if (this.filterTime !== 'all') {
      const now = new Date();
      const startDate =
        this.filterTime === 'week'
          ? new Date(now.setDate(now.getDate() - now.getDay()))
          : new Date(now.setMonth(now.getMonth() - 1));

      filtered = filtered.filter((group) =>
        new Date(group.date) >= startDate
      );
    }

    this.filteredLogs = filtered;
  }

  private getDaysWorked(logs: any[]): string[] {
    const workedDays = new Set<string>();
    logs.forEach((log) => {
      const date = new Date(log.workoutDate).toLocaleDateString('en-US', {
        weekday: 'long',
      });
      workedDays.add(date);
    });

    return Array.from(workedDays);
  }

  private getDaysMissed(workedDays: string[], targetDays: string[]): string[] {
    return targetDays.filter((day) => !workedDays.includes(day));
  }

  private getDaysLeft(workedDays: string[], missedDays: string[]): string[] {
    const today = new Date().toLocaleDateString('en-US', { weekday: 'long' });
    const todayIndex = this.daysToWorkout.indexOf(today);

    return this.daysToWorkout
      .slice(todayIndex + 1)
      .filter((day) => !workedDays.includes(day) && !missedDays.includes(day));
  }

  private handleError(message: string, error: any): void {
    console.error(message, error);
  }
}
