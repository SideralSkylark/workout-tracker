import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ExerciseService {
  private apiBaseUrl = '/api';
  
  constructor(private http: HttpClient) { }

  addExercise(exerciseData: {splitId: number, 
    dayId: number, 
    exerciseName: string,
    reps: number,
    sets: number
  }): Observable<any> {
    return this.http.post(`${this.apiBaseUrl}/exercises/addExercise`, exerciseData);
  }

  deleteExercise(exerciseId: number): Observable<any> {
    return this.http.delete(`${this.apiBaseUrl}/exercises/${exerciseId}`);
  }

  getExercisesByDayAndSplit(dayId: number, splitId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiBaseUrl}/split-schedules/exercises-by-day-id/${dayId}/${splitId}`);
  }
  
}
