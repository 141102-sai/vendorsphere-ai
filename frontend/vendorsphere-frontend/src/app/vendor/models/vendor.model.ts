export type RiskLevel = 'LOW' | 'MEDIUM' | 'HIGH' | 'CRITICAL';

export interface Vendor {
  id: number;
  vendorCode: string;
  vendorName: string;
  email: string;
  phoneNumber: string;
  address: string;
  industry: string;
  riskLevel: RiskLevel;
  active: boolean;
}

export interface VendorRequest {
  vendorName: string;
  email: string;
  phoneNumber: string;
  address: string;
  industry: string;
}
