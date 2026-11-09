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

interface TokenClaims {
  exp?: number;
  roles?: string[];
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
    const token = this.getToken();
    if (!token) return false;
    const claims = this.readClaims(token);
    return !!claims && typeof claims.exp === 'number' && claims.exp * 1000 > Date.now();
  }

  hasAnyRole(...roles: string[]): boolean {
    const token = this.getToken();
    const tokenRoles = token ? this.readClaims(token)?.roles ?? [] : [];
    return this.isAuthenticated() && roles.some((role) => tokenRoles.includes(role));
  }

  private readClaims(token: string): TokenClaims | null {
    try {
      const payload = token.split('.')[1];
      if (!payload) return null;
      const base64 = payload.replace(/-/g, '+').replace(/_/g, '/');
      return JSON.parse(window.atob(base64)) as TokenClaims;
    } catch {
      return null;
    }
  }

  private saveToken(token: string): void {
    if (isPlatformBrowser(this.platformId)) {
      window.sessionStorage.setItem(this.tokenKey, token);
    }
  }
}
