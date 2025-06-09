package com.cobranza.notificaciones.controller;

import com.cobranza.notificaciones.model.Cliente;
import com.cobranza.notificaciones.model.Deuda;
import com.cobranza.notificaciones.repository.ClienteRepository;
import com.cobranza.notificaciones.repository.DeudaRepository;
import com.cobranza.notificaciones.service.CorreoService;

import jakarta.validation.Valid;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class GestionController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private DeudaRepository deudaRepository;

    @Autowired
    private CorreoService correoService;

    @GetMapping("/gestion")
    public String mostrarFormulario(Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("deuda", new Deuda());
        return "gestion";
    }

    @PostMapping("/gestion")
    public String procesarFormulario(
            @Valid @ModelAttribute("cliente") Cliente cliente,
            BindingResult clienteErrors,
            @Valid @ModelAttribute("deuda") Deuda deuda,
            BindingResult deudaErrors,
            Model model) {

        if (clienteErrors.hasErrors() || deudaErrors.hasErrors()) {
            return "gestion";
        }

        clienteRepository.save(cliente);
        deuda.setCliente(cliente);
        deudaRepository.save(deuda);

        String asunto = "Aviso de Deuda";
        String cuerpo = "Hola " + cliente.getNombre() +
                ", tienes una deuda de $" + deuda.getMonto() +
                ". Fecha límite: " + deuda.getFechaLimite();
        correoService.enviarCorreo(cliente.getCorreo(), asunto, cuerpo);

        model.addAttribute("mensajeConfirmacion", "Notificación enviada al cliente.");
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("deuda", new Deuda());

        return "gestion";
    }

    @PostMapping("/enviar-excel")
    public String procesarExcel(@RequestParam("archivo") MultipartFile archivo, Model model) {
        try (InputStream is = archivo.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {
            Sheet hoja = workbook.getSheetAt(0);

            for (int i = 1; i <= hoja.getLastRowNum(); i++) {
                Row fila = hoja.getRow(i);
                if (fila == null) continue;

                String nombre = fila.getCell(0).getStringCellValue();
                String correo = fila.getCell(1).getStringCellValue();
                String celular = fila.getCell(2).getStringCellValue();
                double monto = fila.getCell(3).getNumericCellValue();
                Date fechaLimite = fila.getCell(4).getDateCellValue();

                String mensaje = String.format(
                        "Estimado/a %s,\n\nTiene una deuda de $%.2f con fecha límite %s.",
                        nombre, monto, new SimpleDateFormat("yyyy-MM-dd").format(fechaLimite)
                );

                correoService.enviarCorreo(correo, "Notificación de deuda", mensaje);
            }

            model.addAttribute("mensajeExcel", "Notificaciones enviadas desde el archivo Excel.");
        } catch (Exception e) {
            model.addAttribute("mensajeExcel", "Error al procesar el archivo: " + e.getMessage());
        }

        model.addAttribute("cliente", new Cliente());
        model.addAttribute("deuda", new Deuda());
        return "gestion";
    }
}
