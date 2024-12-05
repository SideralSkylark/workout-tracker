import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WorkoutSplitService {
  private readonly API_URL = 'http://localhost:8080/api'

  constructor(private http: HttpClient) { }

  getAllWorkout(): Observable<any> {
    return this.http.get(`${this.API_URL}/exercise`);
  }
}
