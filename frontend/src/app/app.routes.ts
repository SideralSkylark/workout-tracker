import { Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';

export const routes: Routes = [
  {
    path: '',
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
];
