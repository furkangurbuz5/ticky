import {Component, computed, inject, Signal, signal} from '@angular/core';
import {RouterLink, RouterLinkActive, RouterOutlet} from '@angular/router';
import {ScrumBoardComponent} from "./board/board";
import {Logout} from './logout/logout';
import {AuthService} from './auth/auth.service';

@Component({
  selector: 'app-root',
  imports: [Logout, RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('ticky');
  private authService: AuthService = inject(AuthService);

  isLoggedIn: Signal<boolean> = computed(() => this.authService.loggedIn());

}
