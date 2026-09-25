import { Component, OnInit } from '@angular/core';
import { NotificationService } from './natification.service'; 

export interface Notification {
  id: number;
  message: string;
  isRead: boolean;
}

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html', 
  styleUrls: ['./notification.component.css']
})
export class NotificationComponent implements OnInit {
  
  notifications: Notification[] = [];
  unreadCount: number = 0;

  constructor(private notificationService: NotificationService) {}

  ngOnInit(): void {
    this.loadNotifications();
  }

  private loadNotifications(): void {
    this.notificationService.getNotifications().subscribe((data: Notification[]) => {
      this.notifications = data;
      this.updateUnreadCount();
    });
  }

  private updateUnreadCount(): void {
    this.unreadCount = this.notifications.filter(n => !n.isRead).length;
  }

  markAsRead(id: number): void {
    this.notificationService.markAsRead(id).subscribe(() => {
      const notification = this.notifications.find(n => n.id === id);
      if (notification) {
        notification.isRead = true;
        this.updateUnreadCount(); 
      }
    });
  }

  markAllAsRead(): void {
    this.notificationService.markAllAsRead().subscribe(() => {
      this.notifications.forEach(n => n.isRead = true);
      this.unreadCount = 0;
    });
  }
}

