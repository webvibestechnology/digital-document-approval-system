import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './profile.html',
  styleUrl: './profile.css',
})
export class ProfileComponent {
  username = '';
  role = '';

  constructor(private authService: AuthService) {
    this.username = authService.getUsername();
    this.role = authService.getUserRole();
  }
}
