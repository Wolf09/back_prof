package com.professional.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "independientes")
public class Independiente extends Profesional implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String profesion;

    @Column(name = "foto_Titulo")
    private String fotoTitulo;

    @Column(name = "area_trabajo")
    @NotBlank
    private String areaTrabajo;

    // Relación Uno a Muchos con TrabajoIndependiente
    @OneToMany(mappedBy = "independiente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // Evita ciclos infinitos al serializar a JSON
    private List<TrabajoIndependiente> trabajosIndependientes;

    public Independiente() {
        this.trabajosIndependientes=new ArrayList<>();
        this.setActivo(true);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getFotoTitulo() {
        return fotoTitulo;
    }

    public void setFotoTitulo(String fotoTitulo) {
        this.fotoTitulo = fotoTitulo;
    }

    public List<TrabajoIndependiente> getTrabajosIndependientes() {
        return trabajosIndependientes;
    }

    public void setTrabajosIndependientes(List<TrabajoIndependiente> trabajosIndependientes) {
        this.trabajosIndependientes = trabajosIndependientes;
    }

    public String getAreaTrabajo() {
        return areaTrabajo;
    }

    public void setAreaTrabajo(String areaTrabajo) {
        this.areaTrabajo = areaTrabajo;
    }

    // Métodos para gestionar la relación con TrabajoIndependiente
    public void addTrabajoIndependiente(TrabajoIndependiente trabajoIndependiente){
        trabajosIndependientes.add(trabajoIndependiente);
        trabajoIndependiente.setIndependiente(this);
    }
    public void removeTrabajoIndependiente(TrabajoIndependiente trabajoIndependiente){
        trabajosIndependientes.remove(trabajoIndependiente);
        trabajoIndependiente.setIndependiente(null);
    }

    @Serial
    private static final long serialVersionUID = 1L;
}
