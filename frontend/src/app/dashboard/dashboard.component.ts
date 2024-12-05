import { Component, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { WorkoutSplitService } from '../services/workout-split.service';

@Component({
  selector: 'app-dashboard',
  imports: [RouterLink],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
  standalone: true // This is required
})
export class DashboardComponent implements OnInit{
  workouts: any[] = [];

  constructor(private workoutSplitService: WorkoutSplitService) {}

  ngOnInit(): void {
      this.fetchWorkouts();
  }

  fetchWorkouts(): void {
    this.workoutSplitService.getAllWorkouts().subscribe({
      next: (data) => (this.workouts = data),
      error: (err) => console.error('error fetching workouts: ', err ),
    });
  }

  userName = signal('Sidik');
}
