package com.cobranza.notificaciones.dto;

import java.util.Date;

public class DeudaDTO {
    private String nombre;
    private String correo;
    private String celular;
    private double monto;
    private Date fechaLimite;

    public DeudaDTO() {
    }

    public DeudaDTO(String nombre, String correo, String celular, double monto, Date fechaLimite) {
        this.nombre = nombre;
        this.correo = correo;
        this.celular = celular;
        this.monto = monto;
        this.fechaLimite = fechaLimite;
    }

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

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }
}
