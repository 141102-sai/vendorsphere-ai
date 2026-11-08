import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { authGuard } from './auth/auth.guard';
import { LoginComponent } from './auth/login.component';
import { RegisterComponent } from './auth/register.component';
import { VendorList } from './vendor/components/vendor-list/vendor/components/vendor-list/vendor-list';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'vendors', component: VendorList, canActivate: [authGuard] },
  { path: '', pathMatch: 'full', redirectTo: 'vendors' },
  { path: '**', redirectTo: 'vendors' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
