import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NotificationService } from '../../services/notification';
import { AuthService } from '../../services/auth';
import { Notification } from '../../models/notification';

@Component({
  selector: 'app-notification',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './notification.html',
  styleUrl: './notification.css',
})
export class NotificationComponent {
  notifications: Notification[] = [];
  isLoading = true;
  unreadCount = 0;

  constructor(
    private notificationService: NotificationService,
    private authService: AuthService
  ) {}

  ngOnInit() {
    this.loadNotifications();
  }

  loadNotifications() {
    this.notificationService.getNotifications().subscribe({
      next: (notifications) => {
        this.notifications = notifications;
        this.unreadCount = notifications.filter(n => !n.isRead).length;
        this.isLoading = false;
      },
      error: () => {
        this.isLoading = false;
      }
    });
  }

  markRead(id: number) {
    this.notificationService.markAsRead(id).subscribe({
      next: () => {
        const notification = this.notifications.find(n => n.id === id);
        if (notification) {
          notification.isRead = true;
          this.unreadCount--;
        }
      }
    });
  }

  markAllRead() {
    this.notificationService.markAllAsRead().subscribe({
      next: () => {
        this.notifications.forEach(n => n.isRead = true);
        this.unreadCount = 0;
      }
    });
  }
}
