import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LibroService } from '../../services/libro.service';
import { Libro } from '../../models/libro';
import { FormLibroComponent } from './form-libro/form-libro.component';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatTooltipModule } from '@angular/material/tooltip';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

@Component({
  selector: 'app-libros',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatIconModule, MatDialogModule, MatButtonModule, MatTooltipModule],
  templateUrl: './libros.component.html',
  styleUrl: './libros.component.scss'
})
export class LibrosComponent implements OnInit {
  displayedColumns: string[] = ['titulo', 'autor', 'genero', 'unidades', 'estado', 'acciones'];
  libros = new MatTableDataSource<Libro>([]);

  constructor(private libroService: LibroService, private dialog: MatDialog, private router: Router) {}

  ngOnInit(): void {
    this.cargarLibros();
  }

  cargarLibros(): void {
    this.libroService.obtenerTodos().subscribe(data => {
      this.libros.data = data;
    });
  }

  abrirFormulario(libro?: Libro): void {
    const dialogRef = this.dialog.open(FormLibroComponent, {
      width: '400px',
      data: { libro }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) this.cargarLibros();
    });
  }

  eliminarLibro(libro: Libro): void {
    Swal.fire({
      title: '¿Estás seguro?',
      text: `¿Quieres eliminar el libro "${libro.titulo}"?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.libroService.eliminarLibro(libro.id!).subscribe(() => {
          Swal.fire('Eliminado', 'El libro ha sido eliminado correctamente', 'success');
          this.cargarLibros();
        }, error => {
          Swal.fire('Error', 'No se pudo eliminar el libro', 'error');
        });
      }
    });
  }


  volverAlInicio(): void {
    this.router.navigate(['/']);
  }
}
