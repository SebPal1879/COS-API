import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Usuario } from '../models/usuario';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private apiUrl = 'http://localhost:8080/usuarios';

  constructor(private http: HttpClient) { }

  obtenerUsuariosActivos(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(`${this.apiUrl}/activos`);
  }

  agregarUsuario(usuario: Usuario): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/registrar`, usuario);
  }

  actualizarUsuario(id: number, usuario: Usuario): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/actualizar/${id}`, usuario);
  }

  eliminarUsuario(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/eliminar/${id}`);
  }
}
