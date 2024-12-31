import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css'],
  imports: [FormsModule, NgIf],
})
export class AuthComponent {
  credentials = { username: '', password: ''};
  errorMessage: string = '';

  constructor(private http: HttpClient, private router : Router) {}

  onLogin() {
    this.http.post('http://localhost:8082/api/auth/login', this.credentials).subscribe({
      next: () => {
        console.log('Login successful');
        this.router.navigate(['/patients']);
      },
      error: (err) => {
        console.error('Login failed', err);
        this.errorMessage = 'Invalid username or password';
      },
    });
  }
}
