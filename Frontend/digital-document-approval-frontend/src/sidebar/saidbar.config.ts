import { Component, OnInit, inject } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service'; 

interface NavLink {
  label: string;
  route: string;
  icon: string;
  roles: string[]; 

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './sidebar.component.html', 
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
  public authService = inject(AuthService);

  isAdmin: boolean = false;
  isApprover: boolean = false;
  userRole: string | null = '';

  navLinks: NavLink[] = [
    { label: 'Dashboard', route: '/dashboard', icon: '📊', roles: ['USER', 'APPROVER', 'ADMIN'] },
    { label: 'Document Apload', route: '/upload', icon: '📁', roles: ['USER', 'ADMIN'] },
    { label: 'Approval Pending List', route: '/approvals', icon: '⏳', roles: ['APPROVER', 'ADMIN'] },
    { label: 'Reports and Statisticcs', route: '/reports', icon: '📈', roles: ['ADMIN'] },
    { label: 'User Mangement', route: '/users', icon: '👥', roles: ['ADMIN'] }
  ];

  ngOnInit(): void {
    this.userRole = localStorage.getItem('user_role'); 

    this.isAdmin = this.userRole === 'ADMIN';
    this.isApprover = this.userRole === 'APPROVER';
  }

  shouldShowLink(linkRoles: string[]): boolean {
    return this.userRole ? linkRoles.includes(this.userRole) : false;
  }
}
