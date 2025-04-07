package com.example.biblioteca.controllers;

import com.example.biblioteca.dto.MensajeResponse;
import com.example.biblioteca.models.Libro;
import com.example.biblioteca.graphql.InputLibro;
import com.example.biblioteca.services.GraphQLLibroService;
import com.example.biblioteca.services.LibroService;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Controller
public class GraphQLLibroController {

    private final LibroService libroService;
    private final GraphQLLibroService graphQLLibroService;

    public GraphQLLibroController(LibroService libroService, GraphQLLibroService graphQLLibroService) {
        this.libroService = libroService;
        this.graphQLLibroService = graphQLLibroService;
    }

    @QueryMapping(name = "listarLibros")
    public List<Libro> obtenerTodos() {
        return graphQLLibroService.obtenerTodos();
    }

    @QueryMapping(name = "librosDisponibles")
    public List<Libro> obtenerLibrosDisponibles() {
        return graphQLLibroService.obtenerLibrosDisponibles();
    }

    @QueryMapping(name = "librosPrestados")
    public List<Libro> obtenerLibrosPrestados () {
        return graphQLLibroService.obtenerLibrosPrestados();
    }

    @QueryMapping(name = "libroPorId")
    public Optional<Libro> obtenerporId (@Argument Long id){
        return graphQLLibroService.obtenerPorId(id);
    }

    @QueryMapping(name = "librosPorTitulo")
    public List<Libro> buscarPorTitulo(@Argument String titulo) {
        return graphQLLibroService.obtenerPorTitulo(titulo);
    }

    @MutationMapping
    public String agregarLibro(@Argument("LibroInput") InputLibro inputLibro) {
        Libro libro = new Libro();
        libro.setTitulo(inputLibro.getTitulo());
        libro.setAutor(inputLibro.getAutor());
        libro.setGenero(inputLibro.getGenero());
        libro.setUnidades(inputLibro.getUnidades());
        libro.setEstado("ACTIVO");
        graphQLLibroService.guardarLibro(libro);
        return "Libro guardado correctamente";
    }

    @MutationMapping
    public String actualizarLibro(@Argument Long id, @Argument("LibroInput") InputLibro libroActualizado) {
        graphQLLibroService.actualizarLibro(id, libroActualizado);
        return "Libro actualizado correctamente";
    }

    @MutationMapping
    public String eliminarLibro(@Argument Long id) {
        graphQLLibroService.eliminarLibro(id);
        return "Libro eliminado correctamente";
    }
}
