import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DocumentService } from '../../services/document';
import { AuthService } from '../../services/auth';
import { Document } from '../../models/document';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class DashboardComponent {
  isLoading = true;
  totalDocuments = 0;
  pendingCount = 0;
  approvedCount = 0;
  rejectedCount = 0;
  recentDocuments: Document[] = [];

  constructor(
    private documentService: DocumentService,
    private authService: AuthService
  ) {}

  ngOnInit() {
    const userRole = this.authService.getUserRole();
    const serviceMethod = userRole === 'ADMIN' 
      ? this.documentService.getAllDocuments() 
      : this.documentService.getMyDocuments();

    serviceMethod.subscribe({
      next: (docs) => {
        this.totalDocuments = docs.length;
        this.pendingCount = docs.filter(d => d.status === 'PENDING').length;
        this.approvedCount = docs.filter(d => d.status === 'APPROVED').length;
        this.rejectedCount = docs.filter(d => d.status === 'REJECTED').length;
        this.recentDocuments = docs.slice(0, 5);
        this.isLoading = false;
      },
      error: () => {
        this.isLoading = false;
      }
    });
  }
}
