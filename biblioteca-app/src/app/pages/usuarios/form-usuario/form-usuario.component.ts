import { Component, Inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { UsuarioService } from '../../../services/usuario.service';
import { Usuario } from '../../../models/usuario';
import { UsuarioGraphQLService } from '../../../services/usuario-graphql.service';

@Component({
  selector: 'app-form-usuario',
  imports: [CommonModule, ReactiveFormsModule, MatFormFieldModule, MatInputModule,MatSelectModule, MatIconModule, MatButtonModule],
  templateUrl: './form-usuario.component.html',
  styleUrl: './form-usuario.component.scss'
})
export class FormUsuarioComponent implements OnInit{
  usuarioForm!: FormGroup;
  isEdit: boolean = false;

  constructor(
    private fb: FormBuilder,
    private usuarioService: UsuarioGraphQLService,
    public dialogRef: MatDialogRef<FormUsuarioComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { usuario: Usuario }
  ) {}

  ngOnInit(): void {
    this.isEdit = !!this.data?.usuario;

    this.usuarioForm = this.fb.group({
      nombre: [this.data?.usuario?.nombre || '', Validators.required],
      cedula: [this.data?.usuario?.cedula || '', [Validators.required, Validators.minLength(8)]],
      direccion: [this.data?.usuario?.direccion || '', Validators.required],
      email: [this.data?.usuario?.email || '', [Validators.required, Validators.email]],
      estado: [this.data?.usuario?.estado || 'ACTIVO', Validators.required]
    });
  }

  guardarUsuario(): void {
    if (this.usuarioForm.invalid) return;

    const usuario: Usuario = this.usuarioForm.value;

    if (this.isEdit) {
      this.usuarioService.actualizarUsuario(this.data.usuario.id!, usuario).subscribe(() => {
        this.dialogRef.close(true);
      });
    } else {
      this.usuarioService.registrarUsuario(usuario).subscribe(() => {
        this.dialogRef.close(true);
      });
    }
  }

  cancelar() {
    this.usuarioForm.reset();
    this.dialogRef.close();
  }

}
