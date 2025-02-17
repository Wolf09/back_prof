package com.professional.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "trabajos_independiente")
public class TrabajoIndependiente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La descripción del trabajo es obligatoria")
    private String descripcion;

    // almacena el promedio de las calificaciones recibidas para este trabajo
    @Column(name = "average_rating", nullable = false)
    private Double averageRating;

    // Nuevo campo para manejo lógico de eliminación
    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @Column(name = "precio", nullable = false)
    private Double precio;
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "ventas", nullable = false)
    private Long ventas;

    // Relación Muchos a Uno con Independiente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "independiente_id", nullable = false)
    @NotNull(message = "El independiente es obligatorio")
    @JsonIgnore
    private Independiente independiente;

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
    }

    @PrePersist
    public void establecerAverage(){
        this.averageRating = 5.0;
        this.activo=true;
        this.ventas=0L;
        this.fechaCreacion = LocalDateTime.now();
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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



    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
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

    public Long getVentas() {
        return ventas;
    }

    public void setVentas(Long ventas) {
        this.ventas = ventas;
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

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
// toString (opcional)

    @Override
    public String toString() {
        return "TrabajoIndependiente{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", averageRating=" + averageRating +
                '}';
    }

    // Serial UID
    @Serial
    private static final long serialVersionUID = 1L;
}
