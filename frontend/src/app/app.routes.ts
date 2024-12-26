import { Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { authGuard } from './auth/auth.guard';

export const routes: Routes = [
  {
    path: 'dashboard',
    pathMatch: 'full',
    loadComponent: () => 
      import('./dashboard/dashboard.component').then(m => m.DashboardComponent),
    canActivate: [authGuard]
  },
  {
    path: 'workout-split',
    loadComponent: () => 
      import('./workout-split/workout-split.component').then(m => m.WorkoutSplitComponent),
    canActivate: [authGuard]
  },
  {
    path: 'log-workout',
    loadComponent: () => 
      import('./log-workout/log-workout.component').then(m => m.LogWorkoutComponent),
    canActivate: [authGuard]
  },
  {
    path: 'progress',
    loadComponent: () => 
      import('./progress/progress.component').then(m => m.ProgressComponent),
    canActivate: [authGuard]
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
  {
    path: '**',
    loadComponent: () => {
      return import('./page-not-found/page-not-found.component').then(m => m.PageNotFoundComponent);
    }
  }
];
