import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { PageEvent } from '@angular/material/paginator';
import { Sort } from '@angular/material/sort';
import { Subject, Subscription, debounceTime, distinctUntilChanged, takeUntil } from 'rxjs';
import { ConfirmDialogComponent } from '../../../../../../shared/confirm-dialog.component';
import { RiskLevel, Vendor, VendorRequest } from '../../../../../models/vendor.model';
import { VendorService } from '../../../../../services/vendor/services/vendor';
import { VendorDetailDialog } from '../../../../../components/vendor-detail-dialog/vendor-detail-dialog';
import { VendorFormDialog } from '../../../../../components/vendor-form-dialog/vendor-form-dialog';

@Component({
  selector: 'app-vendor-list',
  standalone: false,
  styleUrl: './vendor-list.css',
  templateUrl: './vendor-list.html',
})
export class VendorList implements OnInit, OnDestroy {
  readonly displayedColumns = ['vendorCode', 'vendorName', 'industry', 'riskLevel', 'actions'];
  readonly riskLevels: RiskLevel[] = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'];
  readonly searchControl = new FormControl('', { nonNullable: true });
  readonly riskControl = new FormControl<RiskLevel | null>(null);

  vendors: Vendor[] = [];
  loading = false;
  pageIndex = 0;
  pageSize = 10;
  totalElements = 0;
  sortBy = 'vendorName';
  direction: 'ASC' | 'DESC' = 'ASC';

  private readonly destroyed$ = new Subject<void>();
  private vendorsRequest?: Subscription;

  get materialSortDirection(): Sort['direction'] {
    return this.direction.toLowerCase() as Sort['direction'];
  }

  constructor(
    private readonly vendorService: VendorService,
    private readonly dialog: MatDialog,
    private readonly snackBar: MatSnackBar,
  ) {}

  ngOnInit(): void {
    this.searchControl.valueChanges.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      takeUntil(this.destroyed$),
    ).subscribe(() => this.resetAndLoad());

    this.riskControl.valueChanges.pipe(
      distinctUntilChanged(),
      takeUntil(this.destroyed$),
    ).subscribe(() => this.resetAndLoad());

    this.loadVendors();
  }

  ngOnDestroy(): void {
    this.vendorsRequest?.unsubscribe();
    this.destroyed$.next();
    this.destroyed$.complete();
  }

  onPage(event: PageEvent): void {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadVendors();
  }

  onSort(event: Sort): void {
    if (!event.direction) {
      this.sortBy = 'vendorName';
      this.direction = 'ASC';
    } else {
      this.sortBy = event.active;
      this.direction = event.direction.toUpperCase() as 'ASC' | 'DESC';
    }
    this.pageIndex = 0;
    this.loadVendors();
  }

  clearFilters(): void {
    this.searchControl.setValue('', { emitEvent: false });
    this.riskControl.setValue(null, { emitEvent: false });
    this.resetAndLoad();
  }

  openCreateDialog(): void {
    this.openVendorForm(null);
  }

  openEditDialog(vendor: Vendor): void {
    this.openVendorForm(vendor);
  }

  openDetails(vendor: Vendor): void {
    this.dialog.open(VendorDetailDialog, {
      width: '560px',
      maxWidth: '94vw',
      data: vendor,
    });
  }

  confirmDelete(vendor: Vendor): void {
    const ref = this.dialog.open<ConfirmDialogComponent, string, boolean>(ConfirmDialogComponent, {
      width: '420px',
      data: `Deactivate ${vendor.vendorName}? It will be hidden from active vendor lists.`,
    });

    ref.afterClosed().pipe(takeUntil(this.destroyed$)).subscribe((confirmed) => {
      if (!confirmed) return;
      this.vendorService.deleteVendor(vendor.id).pipe(takeUntil(this.destroyed$)).subscribe({
        next: () => {
          this.snackBar.open('Vendor deactivated.', 'Dismiss', { duration: 3000 });
          this.loadVendors();
        },
        error: (error: unknown) => this.showError(error, 'Unable to deactivate vendor.'),
      });
    });
  }

  riskClass(level: RiskLevel): string {
    return `risk--${level.toLowerCase()}`;
  }

  private openVendorForm(vendor: Vendor | null): void {
    const ref = this.dialog.open<VendorFormDialog, Vendor | null, VendorRequest | undefined>(VendorFormDialog, {
      width: '680px',
      maxWidth: '96vw',
      data: vendor,
    });

    ref.afterClosed().pipe(takeUntil(this.destroyed$)).subscribe((request) => {
      if (!request) return;

      const save$ = vendor
        ? this.vendorService.updateVendor(vendor.id, request)
        : this.vendorService.createVendor(request);

      save$.pipe(takeUntil(this.destroyed$)).subscribe({
        next: () => {
          this.snackBar.open(vendor ? 'Vendor updated.' : 'Vendor created.', 'Dismiss', { duration: 3000 });
          this.loadVendors();
        },
        error: (error: unknown) => this.showError(error, 'Unable to save vendor.'),
      });
    });
  }

  private resetAndLoad(): void {
    this.pageIndex = 0;
    this.loadVendors();
  }

  private loadVendors(): void {
    this.vendorsRequest?.unsubscribe();
    this.loading = true;
    this.vendorsRequest = this.vendorService.getVendors({
      search: this.searchControl.value,
      riskLevel: this.riskControl.value,
      page: this.pageIndex,
      size: this.pageSize,
      sortBy: this.sortBy,
      direction: this.direction,
    }).subscribe({
      next: (result) => {
        this.vendors = result.content;
        this.totalElements = result.totalElements;
      },
      error: (error: unknown) => {
        this.vendors = [];
        this.loading = false;
        this.showError(error, 'Unable to load vendors.');
      },
      complete: () => { this.loading = false; },
    });
  }

  private showError(error: unknown, fallback: string): void {
    const message = error instanceof HttpErrorResponse
      ? error.error?.message ?? fallback
      : fallback;
    this.snackBar.open(message, 'Dismiss', { duration: 5000 });
  }
}
