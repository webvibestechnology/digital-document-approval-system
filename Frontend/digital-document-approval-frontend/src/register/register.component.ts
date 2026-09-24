import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule, AbstractControl } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { DepartmentService } from '../../services/department.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  // आवश्यक सर्व्हिसेस इंजेक्ट करणे
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private deptService = inject(DepartmentService);
  private router = inject(Router);

  registerForm!: FormGroup;
  departments: any[] = []; // ड्रॉपडाउनसाठी डिपार्टमेंट लिस्ट
  errorMessage: string = '';
  successMessage: string = '';

  ngOnInit(): void {
    // १. फॉर्म फील्ड्स तयार करणे आणि व्हॅलिडेशन लावणे
    this.registerForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required]],
      departmentId: ['', [Validators.required]]
    }, {
      // पासवर्ड आणि कन्फर्म पासवर्ड मॅच करण्यासाठी कस्टम व्हॅलिडेटर
      validators: this.passwordMatchValidator
    });

    // २. ngOnInit() मध्ये ड्रॉपडाउनसाठी डिपार्टमेंट्स लोड करणे
    this.loadDepartments();
  }

  // डिपार्टमेंट्स लोड करण्याचे फंक्शन
  loadDepartments(): void {
    this.deptService.getDepartments().subscribe({
      next: (data) => {
        this.departments = data;
      },
      error: (err) => {
        console.error('डिपार्टमेंट्स लोड करताना एरर आली:', err);
      }
    });
  }

  // ३. पासवर्ड मॅच तपासण्यासाठी कस्टम व्हॅलिडेटर (Validate password match before submit)
  passwordMatchValidator(control: AbstractControl): { [key: string]: boolean } | null {
    const password = control.get('password');
    const confirmPassword = control.get('confirmPassword');
    
    if (password && confirmPassword && password.value !== confirmPassword.value) {
      return { 'passwordMismatch': true }; // मॅच न झाल्यास एरर ऑब्जेक्ट पाठवा
    }
    return null;
  }

  // ४. onSubmit(): फॉर्म सबमिट केल्यावर चालणारी मेथड
  onSubmit(): void {
    if (this.registerForm.invalid) {
      this.errorMessage = 'कृपया सर्व माहिती अचूक भरा!';
      return;
    }

    // फॉर्ममधील डेटा मिळवणे
    const registrationData = this.registerForm.value;

    // authService.register() ला कॉल करणे
    this.authService.register(registrationData).subscribe({
      // यशस्वी (Success) झाल्यावर:
      next: (response) => {
        this.successMessage = 'रजिस्ट्रेशन यशस्वी झाले! आता लॉगिन करा.';
        this.errorMessage = '';
        
        // ३ सेकंदानंतर लॉगिन पेजवर रिडायरेक्ट करा
        setTimeout(() => {
          this.router.navigate(['/login']);
        }, 2000);
      },
      // एरर आल्यावर:
      error: (err) => {
        this.errorMessage = err.error?.message || 'रजिस्ट्रेशन करताना काहीतरी चूक झाली.';
        this.successMessage = '';
      }
    });
  }
}
