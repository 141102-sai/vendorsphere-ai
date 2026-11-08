import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { VendorList } from './vendor/components/vendor-list/vendor/components/vendor-list/vendor-list';
import { VendorFormDialog } from './vendor/components/vendor-form-dialog/vendor-form-dialog';
import { VendorDetailDialog } from './vendor/components/vendor-detail-dialog/vendor-detail-dialog';
import { LoginComponent } from './auth/login.component';
import { RegisterComponent } from './auth/register.component';
import { AuthTokenInterceptor } from './auth/auth-token.interceptor';
import { ConfirmDialogComponent } from './shared/confirm-dialog.component';
import { MaterialModule } from './shared/material.module';

@NgModule({
  declarations: [
    App,
    VendorList,
    VendorFormDialog,
    VendorDetailDialog,
    LoginComponent,
    RegisterComponent,
    ConfirmDialogComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    ReactiveFormsModule,
    MaterialModule,
    AppRoutingModule,
  ],
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideClientHydration(),
    { provide: HTTP_INTERCEPTORS, useClass: AuthTokenInterceptor, multi: true },
  ],
  bootstrap: [App],
})
export class AppModule {}
