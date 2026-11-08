import { Component, Inject, inject } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Vendor, VendorRequest } from '../../models/vendor.model';

@Component({
  selector: 'app-vendor-form-dialog',
  standalone: false,
  templateUrl: './vendor-form-dialog.html',
  styleUrl: './vendor-form-dialog.css',
})
export class VendorFormDialog {
  private readonly formBuilder = inject(FormBuilder);
  readonly form = this.formBuilder.nonNullable.group({
    vendorName: ['', [Validators.required, Validators.maxLength(120)]],
    email: ['', [Validators.required, Validators.email, Validators.maxLength(254)]],
    phoneNumber: ['', [Validators.required, Validators.pattern(/^[+0-9().\-\s]{7,25}$/)]],
    address: ['', [Validators.required, Validators.maxLength(300)]],
    industry: ['', [Validators.required, Validators.maxLength(100)]],
  });

  constructor(
    private readonly dialogRef: MatDialogRef<VendorFormDialog, VendorRequest | undefined>,
    @Inject(MAT_DIALOG_DATA) readonly vendor: Vendor | null,
  ) {
    if (vendor) {
      this.form.patchValue({
        vendorName: vendor.vendorName,
        email: vendor.email,
        phoneNumber: vendor.phoneNumber,
        address: vendor.address,
        industry: vendor.industry,
      });
    }
  }

  get isEditing(): boolean {
    return this.vendor !== null;
  }

  submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    this.dialogRef.close(this.form.getRawValue());
  }

  cancel(): void {
    this.dialogRef.close(undefined);
  }
}
