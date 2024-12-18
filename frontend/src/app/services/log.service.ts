import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LogService {
  private apiBaseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  logWorkouts(workoutLogs: any[]): Observable<any> {
    return this.http.post<any>(`${this.apiBaseUrl}/workout-logs/log-workout`, workoutLogs);
  }

  checkLogsForDate(date: string): Observable<boolean> {
    return this.http.get<boolean>(`${this.apiBaseUrl}/workout-logs/logs-exist?date=${date}`);
  }

  getLogsForCurrentWeek(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiBaseUrl}/workout-logs/current-week`);
  }  
}
