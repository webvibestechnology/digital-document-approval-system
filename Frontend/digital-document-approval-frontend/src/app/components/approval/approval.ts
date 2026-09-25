import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApprovalService } from '../../services/approval';
import { Approval } from '../../models/approval';

@Component({
  selector: 'app-approval',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './approval.html',
  styleUrl: './approval.css',
})
export class ApprovalComponent implements OnInit {
  pendingApprovals: Approval[] = [];
  isLoading = true;
  // Track per-item comments using documentId as key
  comments: { [documentId: number]: string } = {};
  processingId: number | null = null;
  errorMessage = '';
  successMessage = '';

  constructor(private approvalService: ApprovalService) {}

  ngOnInit() {
    this.loadApprovals();
  }

  loadApprovals() {
    this.isLoading = true;
    this.approvalService.getPendingApprovals().subscribe({
      next: (approvals) => {
        this.pendingApprovals = approvals;
        this.isLoading = false;
      },
      error: () => {
        this.errorMessage = 'Failed to load approvals.';
        this.isLoading = false;
      }
    });
  }

  approve(documentId: number) {
    this.processingId = documentId;
    this.approvalService.processApproval({
      documentId,
      status: 'APPROVED',
      comments: this.comments[documentId] || ''
    }).subscribe({
      next: () => {
        this.successMessage = 'Document approved successfully!';
        this.comments[documentId] = '';
        this.loadApprovals();
        this.processingId = null;
        setTimeout(() => this.successMessage = '', 3000);
      },
      error: () => {
        this.errorMessage = 'Failed to approve document.';
        this.processingId = null;
      }
    });
  }

  reject(documentId: number) {
    if (!this.comments[documentId]) {
      this.errorMessage = 'Comments are required when rejecting a document.';
      return;
    }
    this.processingId = documentId;
    this.approvalService.processApproval({
      documentId,
      status: 'REJECTED',
      comments: this.comments[documentId]
    }).subscribe({
      next: () => {
        this.successMessage = 'Document rejected.';
        this.comments[documentId] = '';
        this.loadApprovals();
        this.processingId = null;
        setTimeout(() => this.successMessage = '', 3000);
      },
      error: () => {
        this.errorMessage = 'Failed to reject document.';
        this.processingId = null;
      }
    });
  }
}
