import { EventEmitter, inject, Injectable, OnDestroy, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { JwtHelperService } from '@auth0/angular-jwt';
import { catchError, Observable, take, takeUntil, tap } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService implements OnDestroy {
  private apiAuthUrl = 'http://localhost:8080/api/auth';
  private token: string | null = null;
  loggedIn = signal<boolean>(false);

  private http = inject(HttpClient);
  private jwtHelper = inject(JwtHelperService);

  private destroy$ = new EventEmitter<void>();

  constructor() {
    this.checkToken();
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }

  //using localstorage, not very safe.
  login(credentials: { username: string; password: string }): Observable<any> {
    return this.http.post(`${this.apiAuthUrl}/token`, credentials).pipe(
      take(1),
      tap((token: any) => {
        // this.token = token;
        localStorage.setItem('access_token', token);
        this.loggedIn.set(true);
      }),
    );
  }

  logout(): void {
    localStorage.removeItem('access_token');
    this.loggedIn.set(false);
  }

  getToken(): string | null {
    return localStorage.getItem('access_token');
  }

  private checkToken(): void {
    const token = this.getToken();
    this.loggedIn.set(!!token && !this.jwtHelper.isTokenExpired(token));
  }
}
