import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
//import { UsuarioService } from '../../services/usuario.service';
import { UsuarioGraphQLService } from '../../services/usuario-graphql.service';
import { Usuario } from '../../models/usuario';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import Swal from 'sweetalert2';
import { FormUsuarioComponent } from './form-usuario/form-usuario.component';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';

@Component({
  selector: 'app-usuarios',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatIconModule, MatTooltipModule, MatButtonModule],
  templateUrl: './usuarios.component.html',
  styleUrls: ['./usuarios.component.scss']
})
export class UsuariosComponent implements OnInit {
  displayedColumns: string[] = ['nombre', 'cedula', 'direccion', 'email', 'estado', 'acciones'];
  usuarios = new MatTableDataSource<Usuario>([]);

  constructor(private usuarioService: UsuarioGraphQLService, private dialog: MatDialog, private router: Router) {}

  ngOnInit(): void {
    this.cargarUsuarios();

    this.usuarioService.suscribirseAUsuarios().subscribe({
      next: (data) => {
        const nuevo = data.data?.usuarioRegistrado;
        if (!nuevo) return;

        this.usuarios.data = [...this.usuarios.data, nuevo];
        console.log('🆕 Usuario registrado (por suscripción):', nuevo);
      },
      error: (err) => {
        console.error('Error en la suscripción:', err);
      }
    });

    this.usuarioService.suscribirseAUsuarioActualizado().subscribe({
      next: (data) => {
        const actualizado = data.data?.usuarioActualizado;
        if (!actualizado) return;

        const index = this.usuarios.data.findIndex(u => u.id === actualizado.id);
        if (index !== -1) {
          this.usuarios.data[index] = actualizado;
          this.usuarios._updateChangeSubscription();
          console.log('♻️ Usuario actualizado (por suscripción):', actualizado);
        }
      },
      error: (err) => console.error('Error en suscripción actualizar:', err),
    });

    this.usuarioService.suscribirseAUsuarioEliminado().subscribe({
      next: (data) => {
        const eliminadoId = data.data?.usuarioEliminado?.id;
        if (!eliminadoId) return;

        this.usuarios.data = this.usuarios.data.filter(u => u.id !== eliminadoId);
        console.log('❌ Usuario eliminado (por suscripción):', eliminadoId);
      },
      error: (err) => console.error('Error en suscripción eliminar:', err),
    });
  }

  cargarUsuarios(): void {
    this.usuarioService.obtenerUsuariosActivos().subscribe(data => {
      this.usuarios.data = data;
    });
  }

  abrirFormulario(usuario?: Usuario): void {
    const dialogRef = this.dialog.open(FormUsuarioComponent, {
      width: '400px',
      data: { usuario }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) this.cargarUsuarios();
    });
  }

  eliminarUsuario(usuario: Usuario): void {
    Swal.fire({
      title: '¿Estás seguro?',
      text: `¿Quieres eliminar a "${usuario.nombre}"?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.usuarioService.eliminarUsuario(usuario.id!).subscribe(() => {
          Swal.fire('Eliminado', 'El usuario ha sido eliminado correctamente', 'success');
          this.cargarUsuarios();
        }, error => {
          Swal.fire('Error', 'No se pudo eliminar al usuario', 'error');
        });
      }
    });
  }

  volverAlInicio(): void {
    this.router.navigate(['/']);
  }
}

