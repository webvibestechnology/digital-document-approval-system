import { Component, OnInit } from '@angular/core';
import { ApprovalService } from './approval.service';

export interface Approval {
  id: number;
  documentId: number;
  documentName: string;
  status: string;
}

@Component({
  selector: 'app-approval',
  templateUrl: './approval.component.html',
  styleUrls: ['./approval.component.css']
})
export class ApprovalComponent implements OnInit {

  pendingApprovals: Approval[] = [];
  selectedApproval: Approval | null = null;
  comments: string = '';
  isModalOpen: boolean = false; 
  successMessage: string | null = null;

  constructor(
    private approvalService: ApprovalService
  ) {}

  ngOnInit(): void {
    this.loadPendingApprovals();
  }

  loadPendingApprovals(): void {
    this.approvalService.getPendingApprovals().subscribe({
      next: (data: Approval[]) => {
        this.pendingApprovals = data;
      },
      error: (err: any) => {
        console.error('Error while loading data:', err);
      }
    });
  }

  openApprovalModal({ approval }: { approval: Approval; }): void {
    this.selectedApproval = approval;
    this.isModalOpen = true; 
  }

  approve(documentId: number): void {
    this.approvalService.processApproval(documentId, 'APPROVED', this.comments).subscribe({
      next: () => {
        this.handleActionSuccess(documentId, 'Document has been successfuly approved!');
      },
      error: (err: any) => {
        console.error('Error while approving:', err);
      }
    });
  }

  reject(documentId: number): void {
    this.approvalService.processApproval(documentId, 'REJECTED', this.comments).subscribe({
      next: () => {
        this.handleActionSuccess(documentId, 'Document has been successfully rejected!');
      },
      error: (err: any) => {
        console.error('Error occurred while rejected:', err);
      }
    });
  }

  private handleActionSuccess(documentId: number, message: string): void {
    this.pendingApprovals = this.pendingApprovals.filter(app => app.documentId !== documentId);
    
    this.successMessage = message;
    
    this.isModalOpen = false;
    this.selectedApproval = null;
    this.comments = '';
 
    setTimeout(() => {
      this.successMessage = null;
    }, 3000);
  }
}
