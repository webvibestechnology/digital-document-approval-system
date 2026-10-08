import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { DocumentService } from '../../services/document';
import { AuthService } from '../../services/auth';
import { Document } from '../../models/document';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-document',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './document.html',
  styleUrl: './document.css',
})
export class DocumentComponent implements OnInit {
  documents: Document[] = [];
  showUploadForm = false;
  isLoading = true;       
  isUploading = false;    
  title = '';
  description = '';
  selectedFile: File | null = null;
  errorMessage = '';
  successMessage = '';

  constructor(
    public documentService: DocumentService,
    public authService: AuthService
  ) {}

  ngOnInit() {
    this.loadDocuments();
  }

  loadDocuments(): void {
    this.isLoading = true;
    this.errorMessage = '';
    
    const userRole = this.authService.getUserRole();
    const serviceMethod = userRole === 'ADMIN' 
      ? this.documentService.getAllDocuments() 
      : this.documentService.getMyDocuments();

    serviceMethod.subscribe({
      next: (docs: Document[]) => {
        this.documents = docs;
        this.isLoading = false;
      },
      error: (err) => {
        console.error("Failed to load documents:", err);
        this.errorMessage = 'Failed to load documents.';
        this.isLoading = false; 
        this.documents = []; 
      }
    });
  }

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
    }
  }

  upload() {
    if (!this.title || !this.selectedFile) {
      this.errorMessage = 'Please fill in all required fields';
      return;
    }

    this.isUploading = true;
    this.errorMessage = '';
    this.successMessage = '';

    const formData = new FormData();
    formData.append('title', this.title);
    formData.append('description', this.description);
    formData.append('file', this.selectedFile);

    this.documentService.uploadDocument(
      this.title,
      this.description,
      this.selectedFile,
      undefined as never
    ).subscribe({
      next: () => {
        this.successMessage = 'Document uploaded successfully!';
        this.title = '';
        this.description = '';
        this.selectedFile = null;
        this.showUploadForm = false;
        this.loadDocuments(); 
        this.isUploading = false; 
      },
      error: (err) => {
        console.error('Upload failed:', err);
        this.errorMessage = err.error?.message || 'Upload failed';
        this.isUploading = false; 
      }
    });
  }

  deleteDocument(id: number) {
    if (!confirm('Are you sure you want to delete this document?')) return;

    this.documentService.deleteDocument(id).subscribe({
      next: () => {
        this.successMessage = 'Document deleted successfully';
        this.loadDocuments();
      },
      error: (err) => {
        this.errorMessage = err.error?.error || 'Delete failed';
      }
    });
  }


  downloadDocument(id: number) {
    this.documentService.downloadDocument(id);
  }
}

