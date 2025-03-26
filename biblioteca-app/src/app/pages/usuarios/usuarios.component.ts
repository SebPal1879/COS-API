import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UsuarioService } from '../../services/usuario.service';
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

  constructor(private usuarioService: UsuarioService, private dialog: MatDialog, private router: Router) {}

  ngOnInit(): void {
    this.cargarUsuarios();
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
