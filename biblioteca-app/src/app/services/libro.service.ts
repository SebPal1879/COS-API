import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Libro } from '../models/libro';

@Injectable({
  providedIn: 'root'
})
export class LibroService {

  private apiUrl = 'http://localhost:8080/libros';

  constructor(private http: HttpClient) { }

  obtenerTodos(): Observable<Libro[]> {
    return this.http.get<Libro[]>(this.apiUrl);
  }

  obtenerPorId(id: number): Observable<Libro> {
    return this.http.get<Libro>(`${this.apiUrl}/buscar_id/${id}`);
  }

  agregarLibro(libro: Libro): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/agregar_libro`, libro);
  }

  actualizarLibro(id: number, libro: Libro): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/actualizar_libro/${id}`, libro);
  }

  eliminarLibro(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/eliminar_libro/${id}`);
  }
}
