import { Component } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { environment } from '../../environments/environment';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css'],
})
export class AuthComponent {
  credentials = { username: '', password: ''};
  errorMessage: string = '';

  constructor(private http: HttpClient, private router : Router, private authService: AuthService) {}

  onLogin() {
    this.http.post<any>(environment.AUTH_URL, this.credentials).subscribe({
      next: (response) => {
        console.log('Login successful', response);

        if (response.username) {
          this.authService.setUsername(response.username);
          console.log("Nom d'utilisateur stocké : ", response.username);
        }

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
