import {Component, inject} from '@angular/core';
import {AuthService} from '../auth/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-logout',
  imports: [],
  templateUrl: './logout.html',
  styleUrl: './logout.css',
})
export class Logout {
  private authService = inject(AuthService);
  private router = inject(Router);
  onLogout() {
    this.authService.logout();
    this.router.navigate(['login']).then(() => {});
  }
}
