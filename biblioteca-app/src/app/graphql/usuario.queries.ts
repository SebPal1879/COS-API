import { Query } from "@angular/core";
import { gql } from "apollo-angular";

export const USUARIOS_ACTIVOS = gql`
  query usuariosActivos {
    usuariosActivos {
      id
      nombre
      email
      direccion
      cedula
      estado
    }
  }
`;
