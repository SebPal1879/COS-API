import { Usuario } from './../models/usuario';


export interface UsuarioRegistradoResponse {
  usuarioRegistrado: Usuario;
}

export interface UsuarioActualizadoResponse {
  usuarioActualizado: Usuario;
}

export interface UsuarioEliminadoResponse {
  usuarioEliminado: { id: number };
}
