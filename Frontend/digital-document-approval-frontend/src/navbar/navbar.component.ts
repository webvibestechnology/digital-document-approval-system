import { Component, OnInit, inject } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { NotificationService } from '../../services/notification.service'; // तुमच्या सर्व्हिसचा पाथ
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './navbar.html',
  styleUrls: ['./navbar.css']
})
export class NavbarComponent implements OnInit {
  public authService = inject(AuthService);
  private notificationService = inject(NotificationService);
  private router = inject(Router);

  username: string | null = '';
  unreadCount: number = 0;

  ngOnInit(): void {
    this.username = localStorage.getItem('username');

    this.loadUnreadNotifications();
  }

  loadUnreadNotifications(): void {
    if (this.authService.isLoggedIn()) {
      this.notificationService.getNotifications().subscribe({
        next: (notifications: any[]) => {

          this.unreadCount = notifications.filter(n => !n.isRead || n.status === 'UNREAD').length;
        },
        error: (err) => console.error('नोटिफिकेशन्स आणताना एरर आली:', err)
      });
    }
  }

  logout(): void {
    this.authService.logout();      
    this.router.navigate(['/login']); 
  }
}

