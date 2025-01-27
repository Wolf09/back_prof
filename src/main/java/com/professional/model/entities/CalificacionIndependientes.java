package com.professional.model.entities;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
@Entity
@Table(name = "calificacion_independientes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"cliente_id", "trabajo_ind_en_accion_id"})
})
/*
a. Repositorio CalificacionIndependientesRepository
Cambios Realizados: Actualizar métodos para trabajar con TrabajoIndEnAccion.
 */
public class CalificacionIndependientes implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación Muchos a Uno con Cliente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    @NotNull(message = "El cliente es obligatorio")
    @JsonIgnore
    private Cliente cliente;

    // Relación Muchos a Uno con TrabajoIndEnAccion
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trabajo_ind_en_accion_id", nullable = false)
    @NotNull(message = "El trabajo en acción es obligatorio")
    @JsonIgnore
    private TrabajoIndEnAccion trabajoIndEnAccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trabajo_id", nullable = false)
    @NotNull(message = "El trabajo es obligatorio")
    @JsonIgnore
    private TrabajoIndependiente trabajo;

    @Column(name = "rating", nullable = false)
    @NotNull(message = "La calificación es obligatoria")
    @Min(value = 1, message = "La calificación mínima es 1 estrella")
    @Max(value = 5, message = "La calificación máxima es 5 estrellas")
    private Integer rating;

    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    @Column(name = "fecha_calificacion", nullable = false)
    private LocalDateTime fechaCalificacion;

    private String comentarios;


    public void setId(Long id) {
        this.id = id;
    }

    public void setFechaCalificacion(LocalDateTime fechaCalificacion) {
        this.fechaCalificacion = fechaCalificacion;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
    // Getters y Setters

    @PrePersist
    protected void onCreate() {
        this.fechaCalificacion = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate(){
        this.fechaCalificacion = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }


    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public TrabajoIndEnAccion getTrabajoIndEnAccion() {
        return trabajoIndEnAccion;
    }

    public void setTrabajoIndEnAccion(TrabajoIndEnAccion trabajoIndEnAccion) {
        this.trabajoIndEnAccion = trabajoIndEnAccion;
    }

    public TrabajoIndependiente getTrabajo() {
        return trabajo;
    }

    public void setTrabajo(TrabajoIndependiente trabajo) {
        this.trabajo = trabajo;
    }

    public LocalDateTime getFechaCalificacion() {
        return fechaCalificacion;
    }
    @Serial
    private static final long serialVersionUID = 1L;
}
