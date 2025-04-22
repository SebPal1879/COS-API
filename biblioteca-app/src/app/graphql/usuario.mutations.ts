import { gql } from 'apollo-angular';

export const REGISTRAR_USUARIO = gql`
  mutation RegistrarUsuario($UsuarioInput: UsuarioInput!) {
    registrarUsuario(UsuarioInput: $UsuarioInput) {
      mensaje
    }
  }
`;

export const ACTUALIZAR_USUARIO = gql`
  mutation ActualizarUsuario($id: ID!, $UsuarioInput: UsuarioInput!) {
    actualizarUsuario(id: $id, UsuarioInput: $UsuarioInput) {
      mensaje
    }
  }
`;

export const ELIMINAR_USUARIO = gql`
  mutation EliminarUsuario($id: ID!) {
    eliminarUsuario(id: $id) {
      mensaje
    }
  }
`;
