import {Component, inject} from '@angular/core';
import {AuthService} from '../auth/auth.service';
import {Router} from '@angular/router';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-login',
  imports: [
    FormsModule
  ],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  username: string | null = null;
  password: string | null = null;
  private authService: AuthService = inject(AuthService);
  private router: Router = inject(Router);

  onLogin(): void {
    if (!!this.username && !!this.password) {
      this.authService.login({
          username: this.username,
          password: this.password
        }
      ).subscribe({
        next: () => {
          this.router.navigate(['board']).then(() => {});
        },
        error: err => {
          console.error(err)
        }
      })
    }
  }
}
