import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from './services/autho';
        

@Component({
  selector: 'app-document',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './document.component.html', 
  styleUrls: ['./document.component.css']
})
export class DocumentComponent implements OnInit {
  private documentService = inject(this.documentService.DocumentService);
  private authService = inject(AuthService);
  private fb = inject(FormBuilder);

  documents: any[] = [];
  showUploadForm: boolean = false;
  selectedFile: File | null = null;
  uploadForm!: FormGroup;

  constructor() {
    this.uploadForm = this.fb.group({
      title: ['', [Validators.required, Validators.minLength(3)]]
    });
  }

  ngOnInit(): void {
    this.loadDocuments();
  }

  loadDocuments(): void {
    const userRole = this.authService.getUserRole(); 
    
    if (userRole === 'ADMIN') {
      const documentService = this.documentService;
      documentService.getAllDocuments().subscribe({
        next: (data: any[]) => this.documents = data,
        error: (err: any) => console.error('Error while loading all documents:', err)
      });
    } else {
      this.documentService.getMyDocuments().subscribe({
        next: (data: any[]) => this.documents = data,
        error: (err: any) => console.error('Error while loading user documents:', err)
      });
    }
  }

  onFileSelected(event: Event): void {
    const element = event.currentTarget as HTMLInputElement;
    let fileList: FileList | null = element.files;
    
    if (fileList && fileList.length > 0) {
      this.selectedFile = fileList[0];
      console.log('Selected File:', this.selectedFile.name);
    }
  }

  uploadDocument(): void {
    if (this.uploadForm.invalid || !this.selectedFile) {
      alert('Please enter a title and selected a file!');
      return;
    }

    const formData = new FormData();
    formData.append('title', this.uploadForm.get('title')?.value);
    formData.append('file', this.selectedFile);

    this.documentService.uploadDocument(formData).subscribe({
      next: (response: any) => {
        alert('Document uploaded successfully!');
        const newLocal = this;
        newLocal.uploadForm.reset();      
        this.selectedFile = null;     
        this.showUploadForm = false;  
        this.loadDocuments();        
      },
      error: (err: any) => console.error('An error occurred while uploading:', err)
    });
  }

  deleteDocument(id: number): void {
    if (confirm('Do you really want to deleted this document?')) {
      this.documentService.deleteDocument(id).subscribe({
        next: () => {
          alert('डॉक्युमेंट डिलीट करण्यात आले.');
          this.documents = this.documents.filter(doc => doc.id !== id);
        },
        error: (err: any) => console.error('An error occurred while deleting:', err)
      });
    }
  }

  getStatusClass(status: string): string {
    switch (status.toUpperCase()) {
      case 'APPROVED': return 'badge-approved';
      case 'PENDING': return 'badge-pending';
      case 'REJECTED': return 'badge-rejected';
      default: return 'badge-secondary';
    }
  }
}
