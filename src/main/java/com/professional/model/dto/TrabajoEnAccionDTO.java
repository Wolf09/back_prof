package com.professional.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.professional.model.enums.EstadoTrabajo;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class TrabajoEnAccionDTO {

    private Long id;

    @NotNull
    private EstadoTrabajo estadoTrabajo;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime fechaCambio;

    private Boolean activo;

    private HistorialDTO historial;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public EstadoTrabajo getEstadoTrabajo() {
        return estadoTrabajo;
    }

    public LocalDateTime getFechaCambio() {
        return fechaCambio;
    }

    public Boolean getActivo() {
        return activo;
    }

    public HistorialDTO getHistorial() {
        return historial;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEstadoTrabajo(EstadoTrabajo estadoTrabajo) {
        this.estadoTrabajo = estadoTrabajo;
    }

    public void setFechaCambio(LocalDateTime fechaCambio) {
        this.fechaCambio = fechaCambio;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public void setHistorial(HistorialDTO historial) {
        this.historial = historial;
    }
}

