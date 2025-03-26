import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { LibrosComponent } from './pages/libros/libros.component';
import { UsuariosComponent } from './pages/usuarios/usuarios.component';
import { PrestamosComponent } from './pages/prestamos/prestamos.component';
import { HistorialPrestamosComponent } from './pages/prestamos/historial-prestamos/historial-prestamos.component';

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'libros', component: LibrosComponent },
    { path: 'usuarios', component: UsuariosComponent },
    { path: 'prestamos', component: PrestamosComponent },
    { path: 'historial-prestamos', component: HistorialPrestamosComponent },
    { path: '**', redirectTo: '' }
];
