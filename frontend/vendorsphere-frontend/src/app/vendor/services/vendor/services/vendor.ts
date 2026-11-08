import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../../environments/environment';
import { RiskLevel, Vendor, VendorRequest } from '../../../models/vendor.model';

export interface VendorPage {
  content: Vendor[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
  first: boolean;
  last: boolean;
}

export interface VendorQuery {
  search: string;
  riskLevel: RiskLevel | null;
  page: number;
  size: number;
  sortBy: string;
  direction: 'ASC' | 'DESC';
}

@Injectable({ providedIn: 'root' })
export class VendorService {
  private readonly apiUrl = `${environment.apiBaseUrl}/api/vendors`;

  constructor(private readonly http: HttpClient) {}

  getVendors(query: VendorQuery): Observable<VendorPage> {
    let params = new HttpParams()
      .set('page', query.page)
      .set('size', query.size)
      .set('sortBy', query.sortBy)
      .set('direction', query.direction);

    if (query.search.trim()) {
      params = params.set('search', query.search.trim());
    }
    if (query.riskLevel) {
      params = params.set('riskLevel', query.riskLevel);
    }

    return this.http.get<VendorPage>(this.apiUrl, { params });
  }

  getVendor(id: number): Observable<Vendor> {
    return this.http.get<Vendor>(`${this.apiUrl}/${id}`);
  }

  createVendor(request: VendorRequest): Observable<Vendor> {
    return this.http.post<Vendor>(this.apiUrl, request);
  }

  updateVendor(id: number, request: VendorRequest): Observable<Vendor> {
    return this.http.put<Vendor>(`${this.apiUrl}/${id}`, request);
  }

  deleteVendor(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
