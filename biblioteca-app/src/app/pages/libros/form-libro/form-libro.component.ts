import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { LibroService } from '../../../services/libro.service';
import { Libro } from '../../../models/libro';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-form-libro',
  imports: [ReactiveFormsModule, MatFormFieldModule, MatInputModule,MatSelectModule, MatButtonModule, CommonModule],
  templateUrl: './form-libro.component.html',
  styleUrl: './form-libro.component.scss'
})
export class FormLibroComponent implements OnInit {
  libroForm!: FormGroup;
  isEdit: boolean = false;

  constructor(
    private fb: FormBuilder,
    private libroService: LibroService,
    public dialogRef: MatDialogRef<FormLibroComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { libro: Libro }
  ) { }

  ngOnInit(): void {
    this.isEdit = !!this.data?.libro;

    this.libroForm = this.fb.group({
      titulo: [this.data?.libro?.titulo || '', Validators.required],
      autor: [this.data?.libro?.autor || '', Validators.required],
      genero: [this.data?.libro?.genero || '', Validators.required],
      unidades: [this.data?.libro?.unidades || 1, [Validators.required, Validators.min(1)]],
      estado: [this.data?.libro?.estado || 'ACTIVO', Validators.required]
    });
  }

  guardarLibro(): void {
    if (this.libroForm.invalid) return;

    const libro: Libro = this.libroForm.value;

    if (this.isEdit) {
      this.libroService.actualizarLibro(this.data.libro.id!, libro).subscribe(() => {
        this.dialogRef.close(true);
      });
    } else {
      this.libroService.agregarLibro(libro).subscribe(() => {
        this.dialogRef.close(true);
      });
    }
  }

}
