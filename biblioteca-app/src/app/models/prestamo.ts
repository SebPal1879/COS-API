import { Libro } from "./libro";
import { Usuario } from "./usuario";

export interface Prestamo {
    id?: number;
    libro: Libro;
    usuario: Usuario;
    fechaPrestamo: string;
    fechaDevolucion?: string;
    estado: string;
}
