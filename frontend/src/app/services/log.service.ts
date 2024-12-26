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

  checkLogsForDateAndSplit(date: string, split_id: number): Observable<boolean> {
    const params = { date, splitId: split_id.toString() };
    return this.http.get<boolean>(`${this.apiBaseUrl}/workout-logs/logs-exist`, { params });
  }  

  getLogsForCurrentWeek(splitId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiBaseUrl}/workout-logs/current-week?splitId=${splitId}`);
  }   
  
  getLogsBySplitId(splitId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiBaseUrl}/workout-logs/by-split/${splitId}`);
  }
}
