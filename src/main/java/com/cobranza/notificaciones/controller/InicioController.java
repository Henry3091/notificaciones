package com.cobranza.notificaciones.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {

    @GetMapping("/")
    public String inicio() {
        return "inicio"; // Thymeleaf buscará inicio.html en templates
    }
}


