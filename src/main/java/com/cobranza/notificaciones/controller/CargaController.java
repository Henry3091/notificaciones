package com.cobranza.notificaciones.controller;

import com.cobranza.notificaciones.service.ExcelNotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class CargaController {

    @Autowired
    private ExcelNotificacionService excelNotificacionService;

    @GetMapping("/subir-excel")
    public String mostrarFormularioCarga() {
        return "subir_excel";
    }

    @PostMapping("/procesar-excel")
    public String procesarExcel(@RequestParam("archivo") MultipartFile archivo, Model model) {
        try {
            excelNotificacionService.leerExcelYNotificar(archivo.getInputStream());
            model.addAttribute("mensaje", "Correos enviados correctamente desde el Excel.");
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al procesar el archivo: " + e.getMessage());
            e.printStackTrace();
        }
        return "subir_excel";
    }
}

