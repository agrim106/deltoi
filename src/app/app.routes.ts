import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/components/login.component';
import { SignupComponent } from './features/auth/components/signup.component';
import { DashboardComponent } from './features/dashboard/components/dashboard.component';
import { ProfileComponent } from './features/profile/components/profile.component';
import { ArchiveComponent } from './features/archive/components/archive.component';
import { TrashComponent } from './features/trash/components/trash.component';
import { AuthGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'profile', component: ProfileComponent, canActivate: [AuthGuard] },
  { path: 'archive', component: ArchiveComponent, canActivate: [AuthGuard] },
  { path: 'trash', component: TrashComponent, canActivate: [AuthGuard] },
];
