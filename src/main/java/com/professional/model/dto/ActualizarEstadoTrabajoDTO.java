package com.professional.model.dto;

import com.professional.model.entities.EstadoTrabajo;
import jakarta.validation.constraints.NotNull;

public class ActualizarEstadoTrabajoDTO {

    @NotNull(message = "El estado es obligatorio")
    private EstadoTrabajo estadoTrabajo;

    // Getters y Setters
    public EstadoTrabajo getEstadoTrabajo() {
        return estadoTrabajo;
    }

    public void setEstadoTrabajo(EstadoTrabajo estadoTrabajo) {
        this.estadoTrabajo = estadoTrabajo;
    }
}

