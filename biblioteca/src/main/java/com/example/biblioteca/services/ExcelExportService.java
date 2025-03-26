package com.example.biblioteca.services;

import com.example.biblioteca.models.Prestamo;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ExcelExportService {

    public void export(List<Prestamo> prestamos, HttpServletResponse response) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Préstamos");

        Row headerRow = sheet.createRow(0);
        String[] columns = {"ID", "Usuario", "Libro", "Fecha Préstamo", "Fecha Devolución"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        int rowIdx = 1;
        for (Prestamo prestamo : prestamos) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(prestamo.getId());
            row.createCell(1).setCellValue(prestamo.getUsuario().getNombre());
            row.createCell(2).setCellValue(prestamo.getLibro().getTitulo());
            row.createCell(3).setCellValue(prestamo.getFechaPrestamo().toString());
            //row.createCell(4).setCellValue(prestamo.getFechaDevolucion().toString());
            if (prestamo.getFechaDevolucion() != null) {
                row.createCell(4).setCellValue(prestamo.getFechaDevolucion().toString());
            } else {
                row.createCell(4).setCellValue("Pendiente"); // O cualquier otro valor por defecto
            }
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=prestamos.xlsx";
        response.setHeader(headerKey, headerValue);

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
