import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { MaterialModule } from '../../../core/material.module';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MaterialModule,
    RouterModule
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  loginForm: FormGroup;
  error: string | null = null;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  onLogin() {
    if (this.loginForm.valid) {
      console.log('LoginComponent - form data:', this.loginForm.value);
      console.log("hellooo", this.loginForm.value.email);
      localStorage.setItem('email', this.loginForm.value.email);

      this.authService.login(this.loginForm.value).subscribe({
        next: (res) => {
          console.log('LoginComponent - login success, navigating to dashboard', res);
          // Small delay to ensure token is saved before navigation
          setTimeout(() => this.router.navigate(['/dashboard']), 100);
        },
        error: (err: any) => {
          console.error('LoginComponent - login failed:', err);
          this.error = err.error?.message || 'Login failed. Please try again.';
        }
      });
    } else {
      console.warn('LoginComponent - form invalid');
    }
  }
}
