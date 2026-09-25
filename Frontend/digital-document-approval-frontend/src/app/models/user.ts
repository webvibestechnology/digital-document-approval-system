export interface User {
  id: number;
  username: string;
  email: string;
  role: string;
  department: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  username: string;
  email: string;
  password: string;
  roleName: string;
  departmentId?: number;
}

export interface AuthResponse {
  token: string;
  role: string;
  username: string;
}
