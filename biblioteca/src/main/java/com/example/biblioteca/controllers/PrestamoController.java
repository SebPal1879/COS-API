package com.example.biblioteca.controllers;

import com.example.biblioteca.dto.MensajeResponse;
import com.example.biblioteca.models.Prestamo;
import com.example.biblioteca.services.ExcelExportService;
import com.example.biblioteca.services.PrestamoService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/prestamos")
@CrossOrigin(origins = "http://localhost:4200")
public class PrestamoController {
    @Autowired
    private final PrestamoService prestamoService;

    @Autowired
    private ExcelExportService excelExportService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @GetMapping("/total")
    public ResponseEntity<?> obtenerTodos() {
        return prestamoService.obtenerTodos();
    }

    @GetMapping("/activos")
    public ResponseEntity<?> obtenerPrestamosActivos() {
        return prestamoService.prestamosActivos();
    }

    @PostMapping("/prestar/{libroId}/{usuarioId}")
    public ResponseEntity<MensajeResponse> registrarPrestamo(@PathVariable Long libroId, @PathVariable Long usuarioId) {
        return prestamoService.registrarPrestamo(libroId, usuarioId);
    }

    @PutMapping("/devolver/{prestamoId}")
    public ResponseEntity<MensajeResponse> devolverPrestamo(@PathVariable Long prestamoId) {
        return prestamoService.devolverPrestamo(prestamoId);
    }


    @GetMapping("/exportExcel")
    public void exportPrestamosToExcel(HttpServletResponse response) throws IOException {
        ResponseEntity<?> responseEntity = prestamoService.obtenerTodos();

        if (responseEntity.getStatusCode() == HttpStatus.NOT_FOUND) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("No hay préstamos registrados para exportar.");
            return;
        }

        List<Prestamo> prestamos = (List<Prestamo>) responseEntity.getBody();
        excelExportService.export(prestamos, response);

    }
}
