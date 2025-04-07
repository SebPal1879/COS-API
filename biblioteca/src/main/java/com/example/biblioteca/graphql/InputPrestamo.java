package com.example.biblioteca.graphql;

import com.example.biblioteca.models.Libro;
import com.example.biblioteca.models.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;

@Data
public class InputPrestamo {

    private String libroId;
    private String usuarioId;
    private String fechaPrestamo;
    private String fechaDevolucion;
    private String estado;
}
