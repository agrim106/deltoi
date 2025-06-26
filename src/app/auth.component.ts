import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from './services/user.service';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  imports: [CommonModule, ReactiveFormsModule],
})
export class AuthComponent {
  loginForm: FormGroup;
  signupForm: FormGroup;
  isLogin = true;

  constructor(private fb: FormBuilder, private userService: UserService) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      remember: [false]
    });
    this.signupForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      confirmPassword: ['', Validators.required]
    });
  }

  switchTab(tab: 'login' | 'signup') {
    this.isLogin = tab === 'login';
  }

  onLogin() {
    if (this.loginForm.valid) {
      this.userService.login(this.loginForm.value).subscribe();
    }
  }

  onSignup() {
    if (this.signupForm.valid) {
      this.userService.signup(this.signupForm.value).subscribe();
    }
  }
}
