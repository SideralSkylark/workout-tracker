import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../auth.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  onLogin(): void {
    this.authService.login(this.username, this.password).subscribe({
      next: (response: any) => {
        const token = response.token; 
        const user_id = response.user_id;
        const username = response.username;
        const splits = response.splits;

        this.authService.storeToken(token);
        this.authService.storeUserData(user_id, username, splits);
        
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        this.errorMessage = `Invalid username or password: ${err.message}`;
      }
    });
  }
}
