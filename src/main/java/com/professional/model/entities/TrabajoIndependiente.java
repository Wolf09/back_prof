package com.professional.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trabajos_independiente")
public class TrabajoIndependiente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La descripción del trabajo es obligatoria")
    private String trabajo;

    // Nuevo campo para almacenar el promedio de calificaciones
    @Column(name = "average_rating", nullable = false)
    private Double averageRating;

    // Relación Muchos a Uno con Independiente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "independiente_id", nullable = false)
    @NotNull(message = "El independiente es obligatorio")
    @JsonIgnore
    private Independiente independiente;

    // Relación Muchos a Uno con Cliente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    @NotNull(message = "El cliente es obligatorio")
    @JsonIgnore
    private Cliente cliente;

    // Relación Uno a Muchos con HistorialIndependientes
    @OneToMany(mappedBy = "trabajo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<HistorialIndependientes> historialIndependientes;

    // Relación Uno a Muchos con CalificacionIndependientes
    @OneToMany(mappedBy = "trabajo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<CalificacionIndependientes> calificaciones;

    // Relación Uno a Muchos con TrabajoIndEnAccion
    @OneToMany(mappedBy = "trabajoIndependiente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<TrabajoIndEnAccion> trabajosEnAccion;

    // Constructor por defecto
    public TrabajoIndependiente() {
        this.historialIndependientes = new ArrayList<>();
        this.calificaciones = new ArrayList<>();
        this.trabajosEnAccion = new ArrayList<>();
        this.averageRating = 5.0; // Establecer promedio inicial en 5.0
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public String getTrabajo() {
        return trabajo;
    }

    public void setTrabajo(String trabajo) {
        this.trabajo = trabajo;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Independiente getIndependiente() {
        return independiente;
    }

    public void setIndependiente(Independiente independiente) {
        this.independiente = independiente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<HistorialIndependientes> getHistorialIndependientes() {
        return historialIndependientes;
    }

    public void setHistorialIndependientes(List<HistorialIndependientes> historialIndependientes) {
        this.historialIndependientes = historialIndependientes;
    }

    public List<CalificacionIndependientes> getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(List<CalificacionIndependientes> calificaciones) {
        this.calificaciones = calificaciones;
    }

    public List<TrabajoIndEnAccion> getTrabajosEnAccion() {
        return trabajosEnAccion;
    }

    public void setTrabajosEnAccion(List<TrabajoIndEnAccion> trabajosEnAccion) {
        this.trabajosEnAccion = trabajosEnAccion;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Métodos para gestionar las relaciones

    public void addTrabajoIndEnAccion(TrabajoIndEnAccion trabajoIndEnAccion) {
        trabajosEnAccion.add(trabajoIndEnAccion);
        trabajoIndEnAccion.setTrabajoIndependiente(this);
    }

    public void removeTrabajoIndEnAccion(TrabajoIndEnAccion trabajoIndEnAccion) {
        trabajosEnAccion.remove(trabajoIndEnAccion);
        trabajoIndEnAccion.setTrabajoIndependiente(null);
    }

    public void addHistorialIndependientes(HistorialIndependientes historial) {
        historialIndependientes.add(historial);
        historial.setTrabajo(this);
    }

    public void removeHistorialIndependientes(HistorialIndependientes historial) {
        historialIndependientes.remove(historial);
        historial.setTrabajo(null);
    }

    public void addCalificacionIndependientes(CalificacionIndependientes calificacionIndependientes) {
        calificaciones.add(calificacionIndependientes);
        calificacionIndependientes.setTrabajo(this);
    }

    public void removeCalificacionIndependientes(CalificacionIndependientes calificacionIndependientes) {
        calificaciones.remove(calificacionIndependientes);
        calificacionIndependientes.setTrabajo(null);
    }

    // toString (opcional)

    @Override
    public String toString() {
        return "TrabajoIndependiente{" +
                "id=" + id +
                ", trabajo='" + trabajo + '\'' +
                ", averageRating=" + averageRating +
                '}';
    }

    // Serial UID
    @Serial
    private static final long serialVersionUID = 1L;
}
