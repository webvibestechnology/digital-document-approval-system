export interface Document {
  id: number;
  title: string;
  description: string;
  status: string;
  uploadedByUsername: string;
  filePath: string;
  createdAt: string;
}

export interface DocumentRequest {
  title: string;
  description: string;
}
