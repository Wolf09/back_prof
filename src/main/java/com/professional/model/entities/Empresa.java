package com.professional.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "empresas")
public class Empresa extends Profesional implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "nombre_empresa")
    private String nombreEmpresa;

    @Column(name = "registro_empresa")
    private String registroDeEmpresa;
    @Column(name = "licencia_comercial")
    private String licenciaComercial;

    @Column(name = "foto_Titulo")
    private String fotoTitulo;

    @Column(name = "area_trabajo")
    @NotBlank
    private String areaTrabajo;

    // Relación Uno a Muchos con TrabajoEmpresa
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // Evita ciclos infinitos al serializar a JSON
    private List<TrabajoEmpresa> trabajosEmpresas;

    public Empresa() {
        this.trabajosEmpresas=new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getRegistroDeEmpresa() {
        return registroDeEmpresa;
    }

    public void setRegistroDeEmpresa(String registroDeEmpresa) {
        this.registroDeEmpresa = registroDeEmpresa;
    }

    public String getLicenciaComercial() {
        return licenciaComercial;
    }

    public void setLicenciaComercial(String licenciaComercial) {
        this.licenciaComercial = licenciaComercial;
    }

    public String getAreaTrabajo() {
        return areaTrabajo;
    }

    public void setAreaTrabajo(String areaTrabajo) {
        this.areaTrabajo = areaTrabajo;
    }

    public List<TrabajoEmpresa> getTrabajosEmpresas() {
        return trabajosEmpresas;
    }

    public void setTrabajosEmpresas(List<TrabajoEmpresa> trabajosEmpresas) {
        this.trabajosEmpresas = trabajosEmpresas;
    }

    // Métodos para gestionar la relación con TrabajoEmpresa
    public void addTrabajoEmpresa(TrabajoEmpresa trabajoEmpresa){
        trabajosEmpresas.add(trabajoEmpresa);
        trabajoEmpresa.setEmpresa(this);
    }

    public void removeTrabajoEmpresa(TrabajoEmpresa trabajoEmpresa){
        trabajosEmpresas.remove(trabajoEmpresa);
        trabajoEmpresa.setEmpresa(null);
    }

    public String getFotoTitulo() {
        return fotoTitulo;
    }

    public void setFotoTitulo(String fotoTitulo) {
        this.fotoTitulo = fotoTitulo;
    }

    @Serial
    private static final long serialVersionUID = 1L;
}
