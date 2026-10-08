import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Document } from '../models/document';

@Injectable({
  providedIn: 'root',
})
export class DocumentService {
  private apiUrl = 'http://localhost:8080/api/documents';
  successMessage!: string;
  documentService: any;
  selectedFile: File | null = null;
  errorMessage!: string;
  title: any;
  isUploading: boolean | undefined;
  description: any;
  showUploadForm!: boolean;

  constructor(private http: HttpClient) {}

  uploadDocument(title: string, description: string, selectedFile: File, z: undefined): Observable<Document> {
    return this.http.post<Document>(`${this.apiUrl}/upload`, FormData);
  }

  getMyDocuments(): Observable<Document[]> {
    return this.http.get<Document[]>(`${this.apiUrl}/my`);
  }

  getAllDocuments(): Observable<Document[]> {
    return this.http.get<Document[]>(this.apiUrl);
  }

  deleteDocument(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }

  downloadDocument(id: number): void {
    window.open(`${this.apiUrl}/${id}/download`, '_blank');
  }
  onFileSelected(event: Event) {
  const input = event.target as HTMLInputElement;
  if (input.files && input.files.length > 0) {
    this.selectedFile = input.files[0];
  }
}

upload() {
  if (!this.title || !this.selectedFile) {
    alert('Please fill in all required fields and select a file.');
    return;
  }

  this.isUploading = true;
  this.errorMessage = '';
  this.successMessage = '';

  const formData = new FormData();

  const documentRequest = {
    title: this.title,
    description: this.description
  };
  formData.append('data', new Blob([JSON.stringify(documentRequest)], { type: 'application/json' }));
  formData.append('file', this.selectedFile);

  this.documentService.uploadDocument(formData).subscribe({
    next: (res: any) => {
      this.successMessage = 'Document uploaded successfully!';
      this.title = '';
      this.description = '';
      this.selectedFile = null;
      this.showUploadForm = false;
      this.loadDocuments(); 
      this.isUploading = false;
    },
    error: (err: { error: { message: string; }; }) => {
      console.error('Apload failed:', err);
      this.errorMessage = err.error?.message || 'Upload failed';
      this.isUploading = false;
    }
  });
}
  loadDocuments() {
    throw new Error('Method not implemented.');
  }
}
