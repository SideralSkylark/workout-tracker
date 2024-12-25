// src/app/auth/register/register.component.ts
import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../auth.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-register',
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  username: string = '';
  password: string = '';
  errorMessage: string = '';
  successMessage: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  onRegister(): void {
    this.authService.register(this.username, this.password).subscribe(
      (response) => {
        this.successMessage = 'User registered successfully.';
        setTimeout(() => {
          this.router.navigate(['/login']); // Redirect to login page after successful registration
        }, 2000);
      },
      (error) => {
        this.errorMessage = error.error || 'An error occurred. Please try again.';
      }
    );
  }
}
