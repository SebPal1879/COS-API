import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Usuario } from '../../../models/usuario';
import { Libro } from '../../../models/libro';
import { PrestamoService } from '../../../services/prestamo.service';
import { LibroService } from '../../../services/libro.service';
import { UsuarioService } from '../../../services/usuario.service';
import { Prestamo } from '../../../models/prestamo';
import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-form-prestamo',
  imports: [CommonModule, ReactiveFormsModule, MatFormFieldModule, MatInputModule,MatSelectModule, MatDialogModule, MatButtonModule],
  templateUrl: './form-prestamo.component.html',
  styleUrl: './form-prestamo.component.scss'
})
export class FormPrestamoComponent implements OnInit {
  prestamoForm!: FormGroup;
  isEdit: boolean = false;
  usuarios: Usuario[] = [];
  libros: Libro[] = [];

  constructor(
    private fb: FormBuilder,
    private prestamoService: PrestamoService,
    private usuarioService: UsuarioService,
    private libroService: LibroService,
    public dialogRef: MatDialogRef<FormPrestamoComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { prestamo: Prestamo }
  ) {}

  ngOnInit(): void {
    this.isEdit = !!this.data?.prestamo;

    this.prestamoForm = this.fb.group({
      usuarioId: [this.data?.prestamo?.usuario?.id || '', Validators.required],
      libroId: [this.data?.prestamo?.libro?.id || '', Validators.required]
    });

    this.cargarUsuarios();
    this.cargarLibros();
  }

  cargarUsuarios(): void {
    this.usuarioService.obtenerUsuariosActivos().subscribe(data => {
      this.usuarios = data.filter(usuario => usuario.estado !== 'ELIMINADO');
    });
  }

  cargarLibros(): void {
    this.libroService.obtenerTodos().subscribe(data => {
      this.libros = data.filter(libro => libro.estado !== 'SIN UNIDADES' && libro.estado !== 'ELIMINADO');
    });
  }

  guardarPrestamo(): void {
    if (this.prestamoForm.invalid) return;

    const { usuarioId, libroId } = this.prestamoForm.value;

    this.prestamoService.registrarPrestamo(libroId, usuarioId).subscribe(() => {
      this.dialogRef.close(true);
    });
  }
}