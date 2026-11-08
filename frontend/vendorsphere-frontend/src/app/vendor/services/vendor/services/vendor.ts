import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Vendor, VendorRequest } from '../../../models/vendor.model';

export interface VendorPage {
  content: Vendor[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
  first: boolean;
  last: boolean;
}

@Injectable({ providedIn: 'root' })
export class VendorService {
  private readonly apiUrl = 'http://localhost:8080/api/vendors';

  constructor(private readonly http: HttpClient) {}

  getVendors(page = 0, size = 5, sortBy = 'vendorName'): Observable<VendorPage> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size)
      .set('sortBy', sortBy);

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

  searchVendors(vendorName: string): Observable<Vendor[]> {
    const params = new HttpParams().set('vendorName', vendorName);
    return this.http.get<Vendor[]>(`${this.apiUrl}/search`, { params });
  }
}
