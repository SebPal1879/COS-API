import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { LibrosComponent } from './pages/libros/libros.component';
import { UsuariosComponent } from './pages/usuarios/usuarios.component';
import { PrestamosComponent } from './pages/prestamos/prestamos.component';
import { HistorialPrestamosComponent } from './pages/prestamos/historial-prestamos/historial-prestamos.component';
import { LoginComponent } from './pages/login/login.component';
import { authGuard } from './services/logged.guard';

export const routes: Routes = [
  { path: '', component: HomeComponent, canActivate: [authGuard] },
  { path: 'libros', component: LibrosComponent, canActivate: [authGuard] },
  { path: 'usuarios', component: UsuariosComponent, canActivate: [authGuard] },
  {
    path: 'prestamos',
    component: PrestamosComponent,
    canActivate: [authGuard],
  },
  {
    path: 'historial-prestamos',
    component: HistorialPrestamosComponent,
    canActivate: [authGuard],
  },
  { path: 'login', component: LoginComponent },
  { path: '**', redirectTo: '' },
];
