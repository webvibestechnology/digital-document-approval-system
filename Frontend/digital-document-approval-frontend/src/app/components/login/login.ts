import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth'; // तुमचा पाथ यानुसार तपासून घ्या

@Component({
  selector: 'app-login',
  templateUrl: './login.html',
  styleUrls: ['./login.css'],
  standalone: true,
  imports: [FormsModule, CommonModule, RouterModule] // 👈 इथून AuthService काढून टाकला आहे!
})
export class LoginComponent implements OnInit {
  email = '';
  password = '';
  errorMessage = '';
  isLoading = false;

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    if (this.authService.getToken()) {
      this.router.navigate(['/dashboard']);
    }
  }

  onLogin(): void {
    if (!this.email || !this.password) {
      this.errorMessage = 'Please enter both email and password.';
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';

    this.authService.login({ email: this.email, password: this.password }).subscribe({
      next: (res) => {
        console.log('Login Response:', res);
        
        if (res && res.token) {
          this.authService.saveToken(res.token, res.role, res.username);
          this.router.navigate(['/dashboard']);
        } else {
          this.errorMessage = 'Invalid token received from server.';
        }
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Login Error:', err);
        this.errorMessage = 'Invalid email or password.';
        this.isLoading = false;
      }
    });
  }
}
