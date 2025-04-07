package com.example.biblioteca.services;

import com.example.biblioteca.dto.MensajeResponse;
import com.example.biblioteca.models.Libro;
import com.example.biblioteca.graphql.InputLibro;
import com.example.biblioteca.models.Prestamo;
import com.example.biblioteca.repositories.LibroRepository;
import com.example.biblioteca.repositories.PrestamoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GraphQLLibroService {
    private final LibroRepository libroRepository;
    private final PrestamoRepository prestamoRepository;

    public GraphQLLibroService(LibroRepository libroRepository, PrestamoRepository prestamoRepository) {
        this.libroRepository = libroRepository;
        this.prestamoRepository = prestamoRepository;
    }

    public List<Libro> obtenerTodos() {
        return libroRepository.findAll();
    }

    public List<Libro> obtenerLibrosDisponibles(){
        return libroRepository.findByEstado("ACTIVO");
    }

    public List<Libro> obtenerLibrosPrestados() {
        List<Prestamo> prestamosActivos = prestamoRepository.findByEstado("PRESTADO");
        return prestamosActivos.stream()
                .map(Prestamo::getLibro)
                .distinct()
                .collect(Collectors.toList()); // Retorna lista vacía si no hay libros prestados
    }

    public Optional<Libro> obtenerPorId(Long id) {
        return libroRepository.findById(id);
    }

    public List<Libro> obtenerPorTitulo(String titulo) {
        return libroRepository.findByTituloContainingIgnoreCaseAndEstado(titulo, "ACTIVO");
    }

    public Libro guardarLibro(Libro libro) {
        libro.setEstado("ACTIVO");
        return libroRepository.save(libro);
    }

    public String actualizarLibro(Long id, InputLibro libroActualizado) {
        Optional<Libro> libroOptional = libroRepository.findById(id);

        if (libroOptional.isPresent()) {
            Libro libro = libroOptional.get();
            libro.setTitulo(libroActualizado.getTitulo());
            libro.setAutor(libroActualizado.getAutor());
            libro.setGenero(libroActualizado.getGenero());
            libro.setUnidades(libroActualizado.getUnidades());

            if (libro.getUnidades() == 0) {
                libro.setEstado("SIN UNIDADES");
            } else {
                libro.setEstado("ACTIVO");
            }

            libroRepository.save(libro);
            return "Libro actualizado correctamente";
        } else {
            throw new RuntimeException("Libro no encontrado");
        }
    }

    public String eliminarLibro(Long id) {
        Optional<Libro> libroOptional = libroRepository.findById(id);
        if (libroOptional.isPresent()) {
            Libro libro = libroOptional.get();
            libro.setEstado("ELIMINADO");
            libroRepository.save(libro);
            return "Libro eliminado correctamente";
        } else {
            throw new RuntimeException("Libro no encontrado");
        }
    }
}
