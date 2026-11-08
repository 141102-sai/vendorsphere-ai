import { HttpClient } from '@angular/common/http';
import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { Observable, tap } from 'rxjs';
import { environment } from '../../environments/environment';

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
}

export interface LoginResponse {
  token: string;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly apiUrl = `${environment.apiBaseUrl}/api/v1/users`;
  private readonly tokenKey = 'vendorsphere.access-token';

  constructor(
    private readonly http: HttpClient,
    @Inject(PLATFORM_ID) private readonly platformId: object,
  ) {}

  register(request: RegisterRequest): Observable<unknown> {
    return this.http.post(`${this.apiUrl}/create`, request);
  }

  login(request: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, request).pipe(
      tap((response) => this.saveToken(response.token)),
    );
  }

  logout(): void {
    if (isPlatformBrowser(this.platformId)) {
      window.sessionStorage.removeItem(this.tokenKey);
    }
  }

  getToken(): string | null {
    return isPlatformBrowser(this.platformId)
      ? window.sessionStorage.getItem(this.tokenKey)
      : null;
  }

  isAuthenticated(): boolean {
    return this.getToken() !== null;
  }

  private saveToken(token: string): void {
    if (isPlatformBrowser(this.platformId)) {
      window.sessionStorage.setItem(this.tokenKey, token);
    }
  }
}
