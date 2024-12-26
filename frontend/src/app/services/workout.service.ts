import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WorkoutService {
  private apiBaseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  getWorkoutSplits(): Observable<any> {
    return this.http.get(`${this.apiBaseUrl}/splits`);
  }

  getSplitsByUserId(user_id: number): Observable<any> {
    return this.http.get(`${this.apiBaseUrl}/splits/userId/${user_id}`);
  }

  createSplit(splitData: {user_id: number, name: string}): Observable<any> {
    return this.http.post(`${this.apiBaseUrl}/splits`, splitData);
  }

  deleteSplit(splitId: number): Observable<any> {
    return this.http.delete(`${this.apiBaseUrl}/splits/${splitId}`);
  }

  getSplitSchedule(): Observable<any> {
    return this.http.get(`${this.apiBaseUrl}/split-schedules`);
  }

  getWorkoutLogs(): Observable<any> {
    return this.http.get(`${this.apiBaseUrl}/workout-logs`);
  }
  
  getAllExercises(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiBaseUrl}/exercises`);
  }
  
  addExerciseToDay(splitId: number, data: any): Observable<any> {
    return this.http.post(`${this.apiBaseUrl}/splits/${splitId}/schedule`, data);
  }
  
  removeExerciseFromDay(splitId: number, scheduleId: number): Observable<any> {
    return this.http.delete(`${this.apiBaseUrl}/splits/${splitId}/schedule/${scheduleId}`);
  }
  
}
