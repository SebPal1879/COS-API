package com.example.biblioteca.controllers;


import com.example.biblioteca.dto.MensajeResponse;
import com.example.biblioteca.models.Prestamo;
import com.example.biblioteca.services.GraphQLPrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GraphQLPrestamoController {

    @Autowired
    private final GraphQLPrestamoService graphQLPrestamoService;

    public GraphQLPrestamoController(GraphQLPrestamoService graphQLPrestamoService) {
        this.graphQLPrestamoService = graphQLPrestamoService;
    }

    @QueryMapping(name = "obtenerTodosLosPrestamos")
    public List<Prestamo> obtenerTodosLosPrestamos() {
        return graphQLPrestamoService.obtenerTodos();
    }

    @QueryMapping(name = "prestamosActivos")
    public List<Prestamo> prestamosActivos() {
        return graphQLPrestamoService.prestamosActivos();
    }

    @MutationMapping(name = "registrarPrestamo")
    public MensajeResponse registrarPrestamo(@Argument Long libroId, @Argument Long usuarioId) {
        String mensaje =  graphQLPrestamoService.registrarPrestamo(libroId, usuarioId);
        return new MensajeResponse(mensaje);
    }

    @MutationMapping(name = "devolverPrestamo")
    public MensajeResponse devolverPrestamo(@Argument Long prestamoId) {
        String mensaje = graphQLPrestamoService.devolverPrestamo(prestamoId);
        return new MensajeResponse(mensaje);
    }

}
