import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient, private router: Router) {}

  login(username: string, password: string) {
    return this.http.post<{ token: string, id: number, username: string, splits: any[] }>(
      `${this.apiUrl}/login`,
      { username, password }, 
      { responseType: 'json' }
    );
  }

  storeToken(token: string) {
    console.log('Storing token:', token);
    localStorage.setItem('jwt', token);
  }

  storeUserData(id: any, username: string, splits: any[]): void {
    localStorage.setItem('user_id', id);
    localStorage.setItem('username', username);
    localStorage.setItem('splits', JSON.stringify(splits));
  }

  getToken() {
    const token = localStorage.getItem('jwt');
    console.log('Retrieved token:', token);
    return token;
  }

  getUserData(): { username: string, splits: any[] } {
    const username = localStorage.getItem('username') || '';
    const splits = localStorage.getItem('splits');
    return { username, splits: splits ? JSON.parse(splits) : [] };
  }

  logout() {
    localStorage.removeItem('jwt');
    this.router.navigate(['/login']);
  }

  register(username: string, password: string) {
    const user = { username, password };
    return this.http.post(`${this.apiUrl}/register`, user); // JSON response
  }
}
