package com.example.biblioteca.controllers;

import com.example.biblioteca.dto.MensajeResponse;
import com.example.biblioteca.models.Libro;
import com.example.biblioteca.services.LibroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/libros")
public class LibroController {
    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping
    public ResponseEntity<?> obtenerTodos() {
        return libroService.obtenerTodos();
    }

    @GetMapping("/disponibles")
    public ResponseEntity<?> obtenerLibrosDisponibles() {
        return libroService.obtenerLibrosDisponibles();
    }

    @GetMapping("/prestados")
    public ResponseEntity<?> obtenerLibrosPrestados() {
        return libroService.obtenerLibrosPrestados();
    }

    @GetMapping("/buscar_id/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        return libroService.obtenerPorId(id);
    }

    @GetMapping("/buscar_libro")
    public ResponseEntity<?> buscarPorTitulo(@RequestParam String titulo) {
        return libroService.obtenerPorTitulo(titulo);
    }

    @PostMapping("/agregar_libro")
    public ResponseEntity<MensajeResponse> agregarLibro(@RequestBody Libro libro) {
        return libroService.guardarLibro(libro);
    }

    @PutMapping("/actualizar_libro/{id}")
    public ResponseEntity<MensajeResponse> actualizarLibro(@PathVariable Long id, @RequestBody Libro libroActualizado) {
        return libroService.actualizarLibro(id, libroActualizado);
    }

    @DeleteMapping("/eliminar_libro/{id}")
    public ResponseEntity<MensajeResponse> eliminarLibro(@PathVariable Long id) {
        return libroService.eliminarLibro(id);
    }
}
