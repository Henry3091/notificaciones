package com.cobranza.notificaciones.service;

import com.cobranza.notificaciones.dto.DeudaDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ExcelNotificacionService {

    @Autowired
    private CorreoService correoService;

    public List<DeudaDTO> leerExcelYNotificar(InputStream archivoExcel) {
        List<DeudaDTO> listaDeudas = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try (Workbook libro = new XSSFWorkbook(archivoExcel)) {
            Sheet hoja = libro.getSheetAt(0);

            for (int fila = 1; fila <= hoja.getLastRowNum(); fila++) {
                Row row = hoja.getRow(fila);

                String nombre = row.getCell(0).getStringCellValue();
                String correo = row.getCell(1).getStringCellValue();

                // Lectura robusta del celular
                Cell cell = row.getCell(2); // columna celular
                String celular;
                if (cell == null) {
                    celular = "";
                } else {
                    switch (cell.getCellType()) {
                        case STRING:
                            celular = cell.getStringCellValue().trim();
                            break;
                        case NUMERIC:
                            celular = String.format("%.0f", cell.getNumericCellValue());
                            break;
                        case FORMULA:
                            if (cell.getCachedFormulaResultType() == CellType.STRING) {
                                celular = cell.getStringCellValue().trim();
                            } else if (cell.getCachedFormulaResultType() == CellType.NUMERIC) {
                                celular = String.format("%.0f", cell.getNumericCellValue());
                            } else {
                                celular = "";
                            }
                            break;
                        default:
                            celular = "";
                    }
                }

                // Agregar cero inicial si es necesario (ejemplo: 9 dígitos y no empieza con 0)
                if (!celular.startsWith("0") && celular.length() == 9) {
                    celular = "0" + celular;
                }

                double monto = row.getCell(3).getNumericCellValue();
                Date fechaLimite = row.getCell(4).getDateCellValue();

                DeudaDTO dto = new DeudaDTO(nombre, correo, celular, monto, fechaLimite);
                listaDeudas.add(dto);

                // Enviar correo
                String mensaje = String.format("""
                    Estimado %s,
                    
                    Tiene una deuda pendiente de $%.2f. 
                    Fecha límite de pago: %s.
                    
                    Por favor, regularice su situación a la brevedad.
                    
                    Saludos cordiales.
                    """, nombre, monto, sdf.format(fechaLimite));

                correoService.enviarCorreo(correo, "Notificación de deuda pendiente", mensaje);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaDeudas;
    }
}

