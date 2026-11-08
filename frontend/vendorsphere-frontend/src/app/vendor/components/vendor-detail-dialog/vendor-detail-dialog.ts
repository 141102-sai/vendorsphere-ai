import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Vendor } from '../../models/vendor.model';

@Component({
  selector: 'app-vendor-detail-dialog',
  standalone: false,
  templateUrl: './vendor-detail-dialog.html',
  styleUrl: './vendor-detail-dialog.css',
})
export class VendorDetailDialog {
  constructor(@Inject(MAT_DIALOG_DATA) readonly vendor: Vendor) {}
}
