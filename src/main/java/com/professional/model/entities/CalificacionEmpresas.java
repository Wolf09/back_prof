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

/*
TODO: SE DEBE REALIZAR LA CONSULTA A CHATGPT: Un Cliente crea un TrabajoEmpEnAccion el cual tiene un campo estadoTrabajo que actualiza el Independiente,
una vez que el TrabajoEmpEnAccion en su estadoTrabajo pase a estar finalizado entonces y solo entonces el Cliente puede calificar
este trabajoIndEnAccion que el mismo creo y fue aprobado por un independiente
1.	Hay detalles que no se están tomando en cuenta la entidad: Independiente, que representa a una persona independiente,
tiene su lista de TrabajoIndependiente, donde puede ver todos sus TrabajoIndependiente y las calificaciones relacionadas a este
TrabajoIndependiente, es decir puede ver quien califico es decir puede ver el Cliente que califico su TrabajoIndependiente.
2.	La entidad CalificacionIndependiente tiene una eliminación física, es decir si se elimina de la base de datos
3. ademas la eliminacion en: CalificacionIndependientes deberia ser fisica y no logica
 */
@Entity
@Table(name = "calificaciones", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"cliente_id", "trabajo_id"})
})
public class CalificacionEmpresas implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación Muchos a Uno con Cliente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    @NotNull(message = "El cliente es obligatorio")
    @JsonIgnore
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trabajo_id", nullable = false)
    @NotNull(message = "El trabajo es obligatorio")
    @JsonIgnore
    private TrabajoEmpresa trabajo;

    @Column(name = "rating", nullable = false)
    @NotNull(message = "La calificación es obligatoria")
    @Min(value = 1, message = "La calificación mínima es 1 estrella")
    @Max(value = 5, message = "La calificación máxima es 5 estrellas")
    private Integer rating;

    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    @Column(name = "fecha_calificacion", nullable = false, updatable = false)
    private LocalDateTime fechaCalificacion;
    private String comentarios;



    @PrePersist
    protected void onCreate() {
        this.fechaCalificacion = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public TrabajoEmpresa getTrabajo() {
        return trabajo;
    }

    public void setTrabajo(TrabajoEmpresa trabajo) {
        this.trabajo = trabajo;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public LocalDateTime getFechaCalificacion() {
        return fechaCalificacion;
    }

    public void setFechaCalificacion(LocalDateTime fechaCalificacion) {
        this.fechaCalificacion = fechaCalificacion;
    }

    @Serial
    private static final long serialVersionUID = 1L;
}
