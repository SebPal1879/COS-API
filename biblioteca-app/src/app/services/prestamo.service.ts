import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Prestamo } from '../models/prestamo';

@Injectable({
  providedIn: 'root'
})
export class PrestamoService {
  private apiUrl = 'http://localhost:8080/prestamos';

  constructor(private http: HttpClient) { }

  obtenerTotalPrestamos(): Observable<Prestamo[]> {
    return this.http.get<Prestamo[]>(`${this.apiUrl}/total`);
  }

  obtenerPrestamosActivos(): Observable<Prestamo[]> {
    return this.http.get<Prestamo[]>(`${this.apiUrl}/activos`);
  }

  registrarPrestamo(libroId: number, usuarioId: number): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/prestar/${libroId}/${usuarioId}`, {});
  }

  devolverPrestamo(prestamoId: number): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/devolver/${prestamoId}`, {});
  }
}
