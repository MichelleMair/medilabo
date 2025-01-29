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
    this.http.post<any>('http://localhost:8082/api/auth', this.credentials).subscribe({
      next: (response) => {
        console.log('Login successful', response);

        if(response.token) {
        //Stocker le token JWT dans le localStorage
        localStorage.setItem('token', response.token);
        console.log("Token en localStorage : ", localStorage.getItem('token'));
        } else {
          console.error("Aucun token reçu.");
        }

        if (response.role) {
          localStorage.setItem('role', response.role);
          console.log("Rôle stocké dans localStorage: " , response.role) //Stocker le role de l'user
        }

        console.log("Redirecting to /patients...");
        this.router.navigate(['/patients']);
      },
      error: (err) => {
        console.error('Login failed', err);
        this.errorMessage = 'Invalid username or password';
      },
    });
  }
}
