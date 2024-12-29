import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DayService {
  private apiBaseUrl = '/api';

  constructor(private http: HttpClient) { }

  getDays(): Observable<any> {
    return this.http.get(`${this.apiBaseUrl}/days`);
  }

  getDaysWithWorkouts(splitId: number): Observable<any> {
    return this.http.get(`${this.apiBaseUrl}/split-schedules/days-with-workouts/${splitId}`);
  }
}
