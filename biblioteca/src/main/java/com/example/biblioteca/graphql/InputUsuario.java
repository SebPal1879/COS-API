package com.example.biblioteca.graphql;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class InputUsuario {

    //private Long id;
    private String nombre;
    private String email;
    private String direccion;
    private String cedula;
    private String estado;
}
