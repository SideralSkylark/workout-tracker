import { Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';

export const routes: Routes = [
  {
    path: 'dashboard',
    pathMatch: 'full',
    loadComponent: () => {
      return import('./dashboard/dashboard.component').then(m => m.DashboardComponent);
    },
  },
  {
    path: 'workout-split',
    loadComponent: () => {
      return import('./workout-split/workout-split.component').then(m => m.WorkoutSplitComponent);
    },
  },
  {
    path: 'log-workout',
    loadComponent: () => {
      return import('./log-workout/log-workout.component').then(m => m.LogWorkoutComponent);
    },
  },
  {
    path: 'progress',
    loadComponent: () => {
      return import('./progress/progress.component').then(m => m.ProgressComponent);
    },
  },
  {
    path: '',
    loadComponent: () => {
      return import('./auth/login/login.component').then(m => m.LoginComponent);
    },
  },
  {
    path: 'register',
    loadComponent:() => {
      return import('./auth/register/register.component').then(m => m.RegisterComponent);
    },
  },
];
