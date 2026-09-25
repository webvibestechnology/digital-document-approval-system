import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.css',
})
export class SidebarComponent {
  isAdmin: boolean = false;
  isApprover: boolean = false;

  constructor(private authService: AuthService) {
    this.isAdmin = authService.getUserRole() === 'ADMIN';
    this.isApprover = authService.getUserRole() === 'APPROVER';
  }
}
