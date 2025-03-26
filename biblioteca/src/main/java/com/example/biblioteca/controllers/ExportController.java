package com.example.biblioteca.controllers;
import com.example.biblioteca.models.Prestamo;
import com.example.biblioteca.services.ExcelExportService;
import com.example.biblioteca.services.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/export")
@CrossOrigin(origins = "http://localhost:4200")
public class ExportController {

    @Autowired
    private PrestamoService prestamoService;

    @Autowired
    private ExcelExportService excelExportService;

    @GetMapping("/prestamos/excel")
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
