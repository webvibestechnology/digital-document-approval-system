import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from './user.service'; 

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.profile.css']
})
export class ProfileComponent implements OnInit {
  
  profileForm!: FormGroup; 
  currentUserId!: string;   
  isLoading: boolean = true;

  constructor(
    private userService: UserService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.initForm();

    this.currentUserId =
      localStorage.getItem('currentUserId') ?? localStorage.getItem('userId') ?? '';

    this.loadUserProfile();
  }

  private initForm(): void {
    this.profileForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]]
    });
  }

  private loadUserProfile(): void {
    this.userService.getUserById(this.currentUserId).subscribe({
      next: (user) => {
        this.profileForm.patchValue({
          username: user.username,
          email: user.email
        });
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Error occurred while loading user data:', err);
        this.isLoading = false;
      }
    });
  }

  updateProfile(): void {
    if (this.profileForm.valid) {
      const updatedData = this.profileForm.value;
      
      this.userService.updateProfile(this.currentUserId, updatedData).subscribe({
        next: (response) => {
          alert('Profile updated successfully!');
        },
        error: (err) => {
          alert('An error occurred while updating the profile.');
          console.error(err);
        }
      });
    }
  }
}

