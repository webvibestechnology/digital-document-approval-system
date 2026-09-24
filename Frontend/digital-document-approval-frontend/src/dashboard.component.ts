import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { DocumentService } from '../../services/document.service';
import { ApprovalService } from '../../services/approval.service';
import { NotificationService } from '../../services/notification.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './dashboard.component.html', 
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  private documentService = inject(DocumentService);
  private approvalService = inject(ApprovalService);
  private notificationService = inject(NotificationService);
  private authService = inject(AuthService);

  totalDocuments: number = 0;
  pendingDocuments: number = 0;
  approvedDocuments: number = 0;
  rejectedDocuments: number = 0;
  recentDocuments: any[] = [];   
  pendingApprovals: any[] = []; 
  isAdmin: boolean = false;       

  ngOnInit(): void {
    this.isAdmin = this.authService.getUserRole() === 'ADMIN';

    this.loadDashboardStats();
    this.loadRecentData();
  }

  loadDashboardStats(): void {
    this.documentService.getDocuments().subscribe({
      next: (docs: any[]) => {
        this.totalDocuments = docs.length;

        this.pendingDocuments = docs.filter(d => d.status === 'PENDING').length;
        this.approvedDocuments = docs.filter(d => d.status === 'APPROVED').length;
        this.rejectedDocuments = docs.filter(d => d.status === 'REJECTED').length;

        this.recentDocuments = docs.slice(-5).reverse();
      },
      error: (err) => console.error('An error occurred while featching documents statistics:', err)
    });
  }

  loadRecentData(): void {
    this.approvalService.getPendingApprovals().subscribe({
      next: (approvals: any[]) => {
        this.pendingApprovals = approvals;
      },
      error: (err) => console.error('An error occurred while fetching the pending approval list:', err)
    });
  }
}
