package com.example.biblioteca.services;


import com.example.biblioteca.models.Libro;
import com.example.biblioteca.models.Prestamo;
import com.example.biblioteca.repositories.LibroRepository;
import com.example.biblioteca.repositories.PrestamoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.biblioteca.dto.MensajeResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LibroService {
    private final LibroRepository libroRepository;
    private final PrestamoRepository prestamoRepository;

    public LibroService(LibroRepository libroRepository, PrestamoRepository prestamoRepository) {
        this.libroRepository = libroRepository;
        this.prestamoRepository = prestamoRepository;
    }

    public ResponseEntity<?> obtenerTodos() {
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MensajeResponse("No hay libros registrados"));
        }
        return ResponseEntity.ok(libros);
    }

    public ResponseEntity<?> obtenerLibrosDisponibles() {
        List<Libro> disponibles = libroRepository.findByEstado("ACTIVO");
        if (disponibles.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MensajeResponse("No hay libros disponibles"));
        }
        return ResponseEntity.ok(disponibles);
    }

    public ResponseEntity<?> obtenerLibrosPrestados() {
        List<Prestamo> prestamosActivos = prestamoRepository.findByEstado("PRESTADO");
        List<Libro> librosPrestados = prestamosActivos.stream()
                .map(Prestamo::getLibro)
                .distinct()
                .collect(Collectors.toList());

        if (librosPrestados.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MensajeResponse("No hay libros prestados"));
        }
        return ResponseEntity.ok(librosPrestados);
    }

    public ResponseEntity<?> obtenerPorId(Long id) {
        Optional<Libro> libroOptional = libroRepository.findById(id);
        if (libroOptional.isPresent()) {
            return ResponseEntity.ok(libroOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MensajeResponse("Libro no encontrado"));
        }
    }
    public ResponseEntity<?> obtenerPorTitulo(String titulo) {
        List<Libro> libros = libroRepository.findByTituloContainingIgnoreCaseAndEstado(titulo, "ACTIVO");
        if (libros.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MensajeResponse("No se encontraron libros activos con el término: " + titulo));
        }
        return ResponseEntity.ok(libros);
    }

    public ResponseEntity<MensajeResponse> guardarLibro(Libro libro) {
        libro.setEstado("ACTIVO");
        libroRepository.save(libro);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MensajeResponse("Libro guardado correctamente"));
    }

    public ResponseEntity<MensajeResponse> actualizarLibro(Long id, Libro libroActualizado) {
        return libroRepository.findById(id).map(libro -> {
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

            return ResponseEntity.ok(new MensajeResponse("Libro actualizado correctamente"));
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensajeResponse("Libro no encontrado")));
    }

    public ResponseEntity<MensajeResponse> eliminarLibro(Long id) {
        Optional<Libro> libroOptional = libroRepository.findById(id);
        if (libroOptional.isPresent()) {
            Libro libro = libroOptional.get();
            libro.setEstado("ELIMINADO");
            libroRepository.save(libro);
            return ResponseEntity.ok(new MensajeResponse("Libro eliminado correctamente"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensajeResponse("Libro no encontrado"));
        }
    }
}
