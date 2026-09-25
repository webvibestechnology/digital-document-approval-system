import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserService } from '../../services/user';
import { User } from '../../models/user';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin.html',
  styleUrl: './admin.css',
})
export class AdminComponent implements OnInit {
  users: User[] = [];
  isLoading = true;
  errorMessage = '';
  successMessage = '';

  get adminCount(): number {
    return this.users.filter(u => u.role.toUpperCase() === 'ADMIN').length;
  }

  get approverCount(): number {
    return this.users.filter(u => u.role.toUpperCase() === 'APPROVER').length;
  }

  constructor(private userService: UserService) {}

  ngOnInit() {
    this.loadUsers();
  }

  loadUsers() {
    this.isLoading = true;
    this.userService.getAllUsers().subscribe({
      next: (users) => {
        this.users = users;
        this.isLoading = false;
      },
      error: () => {
        this.errorMessage = 'Failed to load users. Please try again.';
        this.isLoading = false;
      }
    });
  }

  deleteUser(id: number) {
    if (!confirm('Are you sure you want to delete this user? This action cannot be undone.')) return;

    this.userService.deleteUser(id).subscribe({
      next: () => {
        this.successMessage = 'User deleted successfully.';
        this.loadUsers();
        setTimeout(() => this.successMessage = '', 3000);
      },
      error: () => {
        this.errorMessage = 'Failed to delete user.';
        setTimeout(() => this.errorMessage = '', 3000);
      }
    });
  }
}
