import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Medilabo Solutions';
  username: string = localStorage.getItem('username') || '';

  constructor(private router: Router) {}

  logout() {
    localStorage.clear();
    this.router.navigate(['/auth']);
  }
}
