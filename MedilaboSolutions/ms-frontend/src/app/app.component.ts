import { Component, HostListener } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Medilabo Solutions';
  username: string = localStorage.getItem('username') || '';

  constructor(private router: Router, private authService: AuthService) {
    this.authService.username$.subscribe(username => {
      this.username = username || '';
    });
  }

  ngOnInit() {
    this.adjustParallax();
  }

  logout() {
    this.authService.clearAuthData();
    this.router.navigate(['/auth']);
  }

  @HostListener('window:scroll', [])
  onWindowScroll() {
    const scrolly = window.scrollY;
    const header = document.querySelector('header') as HTMLElement;
    const scrollMain = document.getElementById('scroll-animate-main') as HTMLElement;
    const documentHeight = document.body.scrollHeight;

    scrollMain.style.top = `-${scrolly}px`;
    header.style.backgroundPositionY = `${50 - (scrolly * 100 / documentHeight)}%`;
  }

  adjustParallax() {
    const windowHeight = window.innerHeight;
    const content = document.querySelector('.main-content') as HTMLElement;
    const scrollContainer = document.getElementById('scroll-animate') as HTMLElement;
    const scrollMain = document.getElementById('scroll-animate-main') as HTMLElement;

    const contentHeight = content.offsetHeight;
    const totalHeight = windowHeight + contentHeight;

    scrollContainer.style.height = `${totalHeight}px`;
    scrollMain.style.height = `${totalHeight}px`;
  }
}
