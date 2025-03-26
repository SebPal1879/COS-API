import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { PrestamoService } from '../../services/prestamo.service';
import { Prestamo } from '../../models/prestamo';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import Swal from 'sweetalert2';
import { FormPrestamoComponent } from './form-prestamo/form-prestamo.component';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-prestamos',
  standalone: true,
  imports: [CommonModule, MatIconModule, MatTableModule, MatTooltipModule, MatButtonModule],
  templateUrl: './prestamos.component.html',
  styleUrls: ['./prestamos.component.scss']
})
export class PrestamosComponent implements OnInit {
  displayedColumns: string[] = ['usuario', 'libro', 'fechaPrestamo', 'fechaDevolucion', 'estado', 'acciones'];
  prestamos = new MatTableDataSource<Prestamo>([]);
  prestamosActivos: Prestamo[] = [];
  totalActivos: number = 0;
  totalFinalizados: number = 0;
  totalPrestamos: number = 0;

  constructor(private prestamoService: PrestamoService, private dialog: MatDialog, private router: Router) { }

  ngOnInit(): void {
    this.cargarPrestamos();
  }

  cargarPrestamos(): void {
    this.prestamoService.obtenerTotalPrestamos().subscribe(data => {
      this.prestamos.data = data;
      this.prestamosActivos = data.filter(p => p.estado === 'PRESTADO');
      this.actualizarIndicadores();
    });
  }

  actualizarIndicadores(): void {
    this.totalPrestamos = this.prestamos.data.length;
    this.totalActivos = this.prestamos.data.filter(p => p.estado === 'PRESTADO').length;
    this.totalFinalizados = this.prestamos.data.filter(p => p.estado === 'DEVUELTO').length;
  }

  abrirFormulario(): void {
    const dialogRef = this.dialog.open(FormPrestamoComponent, {
      width: '400px'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) this.cargarPrestamos();
    });
  }

  irHistorial(): void {
    this.router.navigate(['/historial-prestamos']);
  }


  devolverPrestamo(prestamo: Prestamo): void {
    Swal.fire({
      title: '¿Devolver libro?',
      text: `¿Confirmas la devolución de "${prestamo.libro.titulo}"?`,
      icon: 'question',
      showCancelButton: true,
      confirmButtonColor: '#28a745',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, devolver',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.prestamoService.devolverPrestamo(prestamo.id!).subscribe(() => {
          Swal.fire('Devuelto', 'El libro ha sido devuelto correctamente', 'success');
          this.cargarPrestamos();
        }, error => {
          Swal.fire('Error', 'No se pudo devolver el libro', 'error');
        });
      }
    });
  }

  volverAlInicio(): void {
    this.router.navigate(['/']);
  }
}
