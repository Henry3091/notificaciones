package com.cobranza.notificaciones.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ContactoForm {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Email(message = "Correo no válido")
    @NotBlank(message = "El correo es obligatorio")
    private String correo;

    @NotBlank(message = "El mensaje es obligatorio")
    private String mensaje;

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
