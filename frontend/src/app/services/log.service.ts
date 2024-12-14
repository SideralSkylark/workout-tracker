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
}
