import { afterNextRender, Component, inject, viewChild } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { Router } from '@angular/router';
import { FormsModule, NgForm } from '@angular/forms';
import { debounceTime, Subject, take, takeUntil } from 'rxjs';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  username: string | null = null;
  password: string | null = null;
  private authService: AuthService = inject(AuthService);
  private router: Router = inject(Router);

  private form = viewChild.required<NgForm>('loginForm');

  private destroy$ = new Subject<void>();

  constructor() {
    afterNextRender(() => {
      this.form()
        .valueChanges?.pipe(debounceTime(300), takeUntil(this.destroy$))
        .subscribe({
          next: (formValue) => {
            console.log(formValue);
            window.localStorage.setItem('login-form-object', formValue);
          },
        });
    });
  }

  onDestroy() {
    this.destroy$.next();
  }

  onLogin(formData: NgForm): void {
    const username = formData.controls['username'];
    const password = formData.controls['password'];

    if (username.valid && password.valid) {
      console.info('Username or password valid!');
      this.authService
        .login({
          username: username.value,
          password: password.value,
        })
        .pipe(takeUntil(this.destroy$))
        .subscribe({
          next: () => {
            this.router.navigate(['board']).then(() => {});
            formData.form.reset();
          },
          error: (err) => {
            console.error(err.message);
          },
        });
    } else {
      console.error('Username or password invalid or missing.');
    }
  }
}
