import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css'],
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
