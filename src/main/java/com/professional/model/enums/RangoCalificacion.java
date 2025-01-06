package com.professional.model.enums;

/**
 * Enum para definir los rangos de calificación.
 */
public enum RangoCalificacion {
    RANGO_0_3("0.0 - 3.0"),
    RANGO_3_3_5("3.0 - 3.5"),
    RANGO_3_5_4_0("3.5 - 4.0"),
    RANGO_4_0_4_5("4.0 - 4.5"),
    RANGO_4_5_5_0("4.5 - 5.0");

    private final String descripcion;

    RangoCalificacion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}

