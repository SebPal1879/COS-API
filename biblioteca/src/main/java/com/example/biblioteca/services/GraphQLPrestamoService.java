package com.example.biblioteca.services;

import com.example.biblioteca.models.Libro;
import com.example.biblioteca.models.Prestamo;
import com.example.biblioteca.models.Usuario;
import com.example.biblioteca.repositories.LibroRepository;
import com.example.biblioteca.repositories.PrestamoRepository;
import com.example.biblioteca.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GraphQLPrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final LibroRepository libroRepository;
    private final UsuarioRepository usuarioRepository;

    public GraphQLPrestamoService(PrestamoRepository prestamoRepository, LibroRepository libroRepository, UsuarioRepository usuarioRepository) {
        this.prestamoRepository = prestamoRepository;
        this.libroRepository = libroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Prestamo> obtenerTodos() {
        List<Prestamo> prestamos = prestamoRepository.findAll();
        if (prestamos.isEmpty()) {
            throw new RuntimeException("No hay préstamos registrados");
        }
        return prestamos;
    }

    public List<Prestamo> prestamosActivos() {
        List<Prestamo> prestamos = prestamoRepository.findByEstado("PRESTADO");
        if (prestamos.isEmpty()) {
            throw new RuntimeException("No hay libros prestados");
        }
        return prestamos;
    }

    public String registrarPrestamo(Long libroId, Long usuarioId) {
        Libro libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if ("ELIMINADO".equals(libro.getEstado())) {
            throw new RuntimeException("No se puede prestar un libro eliminado");
        }

        if ("ELIMINADO".equals(usuario.getEstado())) {
            throw new RuntimeException("No se puede prestar un libro a un usuario eliminado");
        }

        List<Prestamo> prestamosActivos = prestamoRepository.findByUsuarioAndEstado(usuario, "PRESTADO");
        if (prestamosActivos.size() >= 5) {
            throw new RuntimeException("El usuario ya tiene 5 libros prestados");
        }

        if ("SIN UNIDADES".equals(libro.getEstado())) {
            throw new RuntimeException("No hay unidades disponibles para prestar");
        }

        libro.setUnidades(libro.getUnidades() - 1);
        if (libro.getUnidades() == 0) {
            libro.setEstado("SIN UNIDADES");
        }
        libroRepository.save(libro);

        Prestamo prestamo = new Prestamo();
        prestamo.setLibro(libro);
        prestamo.setUsuario(usuario);
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setEstado("PRESTADO");
        prestamoRepository.save(prestamo);

        return "Préstamo registrado correctamente";
    }

    public String devolverPrestamo(Long prestamoId) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));

        if ("DEVUELTO".equals(prestamo.getEstado())) {
            throw new RuntimeException("El préstamo ya fue devuelto");
        }

        prestamo.setEstado("DEVUELTO");
        prestamo.setFechaDevolucion(LocalDate.now());

        Libro libro = prestamo.getLibro();
        libro.setUnidades(libro.getUnidades() + 1);
        libro.setEstado("ACTIVO");
        libroRepository.save(libro);

        prestamoRepository.save(prestamo);

        return "Préstamo devuelto correctamente";
    }
}
