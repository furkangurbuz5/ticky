import {Component, inject} from '@angular/core';
import {AuthService} from '../auth/auth.service';
import {Router} from '@angular/router';
import {FormsModule, NgForm} from '@angular/forms';

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

  onLogin(formData: NgForm): void {

    const username = formData.controls['username'];
    const password = formData.controls['password'];

    if (username.valid && password.valid) {
      console.info("Username or password valid!");
      this.authService.login({
          username: username.value,
          password: password.value
        }
      ).subscribe({
        next: () => {
          this.router.navigate(['board']).then(() => {});
        },
        error: err => {
          console.error(err)
        }
      })
    }else{
      console.error("Username or password invalid or missing.");
    }
  }
}
