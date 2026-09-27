import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DocumentService } from '../../services/document';
import { AuthService } from '../../services/auth';
import { Document } from '../../models/document';
import { HttpClient, HttpClientModule } from '@angular/common/http';


@Component({
  selector: 'app-document',
  standalone: true,
    imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './document.html',
  styleUrl: './document.css',
})
export class DocumentComponent {
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
    private documentService: DocumentService,
    private authService: AuthService,
    private http: HttpClient
  ) {}
  downloadFile(documentId: number) {
    const token = localStorage.getItem('token'); 
    
    this.http.get(`http://localhost:8080/api/documents/${documentId}/download`, {
      headers: { 'Authorization': `Bearer ${token}` },
      responseType: 'blob' 
    }).subscribe((response: Blob) => {
      const blob = new Blob([response], { type: response.type });
      const url = window.URL.createObjectURL(blob);
      const anchor = document.createElement('a');
      anchor.href = url;
      anchor.download = 'downloaded_file.pdf'; 
      anchor.click();
    }, error => {
      console.error('Download failed', error);
    });
  }

  ngOnInit() {
    this.loadDocuments();
  }

  loadDocuments() {
    this.isLoading = true;
    this.errorMessage = '';
    const userRole = this.authService.getUserRole();
    const serviceMethod = userRole === 'ADMIN' 
      ? this.documentService.getAllDocuments() 
      : this.documentService.getMyDocuments();

    serviceMethod.subscribe({
      next: (docs) => {
        this.documents = docs;
        this.isLoading = false;
      },
      error: () => {
        this.isLoading = false;
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

    this.documentService.uploadDocument(this.title, this.description, this.selectedFile).subscribe({
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
        this.errorMessage = err.error?.error || 'Upload failed';
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
