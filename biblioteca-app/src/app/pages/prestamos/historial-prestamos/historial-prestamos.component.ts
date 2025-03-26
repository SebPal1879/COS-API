import { Component, OnInit } from '@angular/core';
import { Prestamo } from '../../../models/prestamo';
import { PrestamoService } from '../../../services/prestamo.service';
import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';

@Component({
  selector: 'app-historial-prestamos',
  imports: [MatTableModule, CommonModule, MatFormFieldModule, MatInputModule, MatSelectModule, MatDialogModule, MatButtonModule],
  templateUrl: './historial-prestamos.component.html',
  styleUrl: './historial-prestamos.component.scss'
})
export class HistorialPrestamosComponent implements OnInit {
  displayedColumns: string[] = ['usuario', 'libro', 'fechaPrestamo', 'fechaDevolucion', 'estado'];
  historialPrestamos = new MatTableDataSource<Prestamo>([]);

  constructor(private prestamoService: PrestamoService, private router: Router) {} // ✅ Eliminado MatDialogRef

  ngOnInit(): void {
    this.cargarHistorial();
  }

  cargarHistorial(): void {
    this.prestamoService.obtenerTotalPrestamos().subscribe(data => {
      this.historialPrestamos.data = data;
    });
  }

  cerrarVentana(): void {
    this.router.navigate(['/prestamos']); // ✅ Redirige a la lista de préstamos o donde necesites
  }
}
