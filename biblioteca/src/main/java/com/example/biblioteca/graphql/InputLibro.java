package com.example.biblioteca.graphql;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class InputLibro {

    private String titulo;
    private String autor;
    private String genero;
    private int unidades;
    private String estado;
}
