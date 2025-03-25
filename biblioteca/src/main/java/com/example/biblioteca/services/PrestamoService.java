package com.example.biblioteca.services;

import com.example.biblioteca.dto.MensajeResponse;
import com.example.biblioteca.models.Libro;
import com.example.biblioteca.models.Prestamo;
import com.example.biblioteca.models.Usuario;
import com.example.biblioteca.repositories.LibroRepository;
import com.example.biblioteca.repositories.PrestamoRepository;
import com.example.biblioteca.repositories.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PrestamoService {
    private final PrestamoRepository prestamoRepository;
    private final LibroRepository libroRepository;
    private final UsuarioRepository usuarioRepository;

    public PrestamoService(PrestamoRepository prestamoRepository, LibroRepository libroRepository, UsuarioRepository usuarioRepository) {
        this.prestamoRepository = prestamoRepository;
        this.libroRepository = libroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public ResponseEntity<?> obtenerTodos() {
        List<Prestamo> prestamos = prestamoRepository.findAll();
        if (prestamos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MensajeResponse("No hay prestamos registrados"));
        }
        return ResponseEntity.ok(prestamos);
    }

    public ResponseEntity<?> prestamosActivos() {
        List<Prestamo> prestamos = prestamoRepository.findByEstado("PRESTADO");
        if (prestamos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MensajeResponse("No hay libros prestados"));
        }
        return ResponseEntity.ok(prestamos);
    }

    public ResponseEntity<MensajeResponse> registrarPrestamo(Long libroId, Long usuarioId) {
        Optional<Libro> libroOpt = libroRepository.findById(libroId);
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);

        if (libroOpt.isPresent() && usuarioOpt.isPresent()) {
            Libro libro = libroOpt.get();
            Usuario usuario = usuarioOpt.get();

            // Validar si el libro está eliminado
            if (libro.getEstado().equals("ELIMINADO")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new MensajeResponse("No se puede prestar un libro eliminado"));
            }

            // Validar si el usuario está eliminado
            if (usuario.getEstado().equals("ELIMINADO")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new MensajeResponse("No se puede prestar un libro a un usuario eliminado"));
            }

            // Validar que el usuario no tenga más de 5 libros prestados
            List<Prestamo> prestamosActivos = prestamoRepository.findByUsuarioAndEstado(usuario, "PRESTADO");
            if (prestamosActivos.size() >= 5) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new MensajeResponse("El usuario ya tiene 5 libros prestados"));
            }

            // Validar si hay unidades disponibles
            if (libro.getEstado().equals("SIN UNIDADES")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new MensajeResponse("No hay unidades disponibles para prestar"));
            }

            // Restar 1 unidad
            libro.setUnidades(libro.getUnidades() - 1);
            if (libro.getUnidades() == 0) {
                libro.setEstado("SIN UNIDADES");
            }
            libroRepository.save(libro);

            // Registrar el préstamo
            Prestamo prestamo = new Prestamo();
            prestamo.setLibro(libro);
            prestamo.setUsuario(usuario);
            prestamo.setFechaPrestamo(LocalDate.now());
            prestamo.setEstado("PRESTADO");

            prestamoRepository.save(prestamo);
            return ResponseEntity.ok(new MensajeResponse("Préstamo registrado correctamente"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MensajeResponse("Libro o Usuario no encontrado"));
        }
    }

    public ResponseEntity<MensajeResponse> devolverPrestamo(Long prestamoId) {
        Optional<Prestamo> prestamoOpt = prestamoRepository.findById(prestamoId);

        if (prestamoOpt.isPresent()) {
            Prestamo prestamo = prestamoOpt.get();

            if (prestamo.getEstado().equals("DEVUELTO")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MensajeResponse("El préstamo ya fue devuelto"));
            }

            prestamo.setEstado("DEVUELTO");
            prestamo.setFechaDevolucion(LocalDate.now());

            Libro libro = prestamo.getLibro();
            libro.setUnidades(libro.getUnidades() + 1);
            libro.setEstado("ACTIVO");
            libroRepository.save(libro);

            prestamoRepository.save(prestamo);
            return ResponseEntity.ok(new MensajeResponse("Préstamo devuelto correctamente"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensajeResponse("Préstamo no encontrado"));
        }
    }
}
