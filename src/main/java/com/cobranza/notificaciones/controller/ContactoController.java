package com.cobranza.notificaciones.controller;

import com.cobranza.notificaciones.model.ContactoForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactoController {

    @GetMapping("/contacto")
    public String mostrarFormularioContacto(Model model) {
        model.addAttribute("contacto", new ContactoForm()); // Esto evita el error 500
        return "contacto";
    }

    @PostMapping("/contacto")
    public String procesarFormularioContacto(
            @Valid @ModelAttribute("contacto") ContactoForm contactoForm,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            return "contacto"; // vuelve a mostrar formulario con errores
        }

        // Aquí puedes guardar o enviar por correo
        model.addAttribute("mensajeConfirmacion", "Mensaje enviado correctamente");
        model.addAttribute("contacto", new ContactoForm()); // limpia el formulario

        return "contacto";
    }
}
