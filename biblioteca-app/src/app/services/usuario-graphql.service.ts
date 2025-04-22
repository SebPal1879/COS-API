import { Injectable } from '@angular/core';
import { Apollo } from 'apollo-angular';
import { map } from 'rxjs/operators';
import { Usuario } from '../models/usuario';
import gql from 'graphql-tag';
import { Observable } from 'rxjs';
import {
  UsuarioRegistradoResponse,
  UsuarioActualizadoResponse,
  UsuarioEliminadoResponse
} from '../graphql/usuario-subscriptions.types';
import { FetchResult } from '@apollo/client/core';

import { USUARIOS_ACTIVOS } from '../graphql/usuario.queries';
import { ELIMINAR_USUARIO, ACTUALIZAR_USUARIO, REGISTRAR_USUARIO } from '../graphql/usuario.mutations';

@Injectable({
  providedIn: 'root'
})
export class UsuarioGraphQLService {
  constructor(private apollo: Apollo) {}

  obtenerUsuariosActivos() {
    return this.apollo
      .watchQuery<any>({
        query: USUARIOS_ACTIVOS
      })
      .valueChanges.pipe(map(result => result.data.usuariosActivos));
  }

  registrarUsuario(usuario: Usuario) {
    return this.apollo.mutate({
      mutation: REGISTRAR_USUARIO,
      variables: {
        UsuarioInput: {
          nombre: usuario.nombre,
          email: usuario.email,
          direccion: usuario.direccion,
          cedula: usuario.cedula,
          estado: usuario.estado
        }
      },
      refetchQueries: ['usuariosActivos']
    });
  }

  actualizarUsuario(id: number, usuario: Usuario) {
    return this.apollo.mutate({
      mutation: ACTUALIZAR_USUARIO,
      variables: {
        id,
        UsuarioInput: {
          nombre: usuario.nombre,
          email: usuario.email,
          direccion: usuario.direccion,
          cedula: usuario.cedula,
          estado: usuario.estado
        }
      },
      refetchQueries: ['usuariosActivos']
    });
  }

  eliminarUsuario(id: number) {
    return this.apollo.mutate({
      mutation: ELIMINAR_USUARIO,
      variables: { id },
      refetchQueries: ['usuariosActivos']
    });
  }

  suscribirseAUsuarios(): Observable<any> {
    return this.apollo.subscribe({
      query: gql`
        subscription {
          usuarioRegistrado {
            id
            nombre
            correo
            activo
          }
        }
      `
    });
  }

  suscribirseAUsuarioActualizado(): Observable<FetchResult<UsuarioActualizadoResponse>> {
    return this.apollo.subscribe<UsuarioActualizadoResponse>({
      query: gql`
        subscription {
          usuarioActualizado {
            id
            nombre
            cedula
            direccion
            email
            estado
          }
        }
      `
    });
  }

  suscribirseAUsuarioEliminado():Observable<FetchResult<UsuarioEliminadoResponse>> {
    return this.apollo.subscribe<UsuarioEliminadoResponse> ({
      query: gql`
        subscription {
          usuarioEliminado {
            id
          }
        }
      `
    });
  }

}
