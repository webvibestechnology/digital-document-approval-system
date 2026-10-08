import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth';
import { RegisterRequest } from '../../models/user';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class RegisterComponent {
  registerData = {
    username: '',
    email: '',
    password: '',
    confirmPassword: '',
    roleName: 'USER',
    departmentName:''
  };

  errorMessage = '';
  successMessage = '';
  isLoading = false;

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit() {
    if (this.registerData.password !== this.registerData.confirmPassword) {
      this.errorMessage = 'Passwords do not match!';
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';

    const data: RegisterRequest = {
      username: this.registerData.username,
      email: this.registerData.email,
      password: this.registerData.password,
      roleName: this.registerData.roleName
    };

    console.log('Sending data to backend:', data);

    this.authService.register(data).subscribe({
      next: () => {
        this.successMessage = 'Registration successful! Please login.';
        this.isLoading = false;
        setTimeout(() => this.router.navigate(['/login']), 2000);
      },
      error: (err: any) => {
        this.errorMessage = err?.error?.error || 'Registration failed. Please try again.';
        this.isLoading = false;
      },
      complete: () => {
        this.isLoading = false;
      }
    });
  }
}
