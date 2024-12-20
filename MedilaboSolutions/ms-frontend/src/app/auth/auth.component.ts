import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrl: './auth.component.css',
  imports: [FormsModule],
})
export class AuthComponent {
  credentials = { username: '', password: ''};

  onLogin() {
    console.log('Logging in with', this.credentials);
    //TODO: connect to backend for authentication
  }
}
