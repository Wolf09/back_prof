package com.professional.model.dto;

import com.professional.model.validation.ClienteGroup;
import com.professional.model.validation.EmpresaGroup;
import com.professional.model.validation.IndependienteGroup;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.Default;

public class RegistroDTO {
    @NotBlank(message = "El tipo de usuario es obligatorio")
    private String tipoUsuario; // "Cliente", "Empresa", "Independiente"

    // Campos comunes: se validan para todos
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 60, message = "El nombre debe tener entre 2 y 60 caracteres", groups = {ClienteGroup.class, EmpresaGroup.class, IndependienteGroup.class})
    private String nombres;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(min = 2, max = 60, message = "Los apellidos deben tener entre 2 y 60 caracteres", groups = {ClienteGroup.class, EmpresaGroup.class, IndependienteGroup.class})
    private String apellidos;

    @NotBlank(message = "El celular es obligatorio")
    @Pattern(regexp = "^(1|[5-9][0-9]{2})\\d{7,10}$", message = "El celular debe tener un formato válido, por ejemplo: 59170735671",
            groups = {ClienteGroup.class, EmpresaGroup.class, IndependienteGroup.class})
    private String celular;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe tener un formato válido", groups = {ClienteGroup.class, EmpresaGroup.class, IndependienteGroup.class})
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 60, message = "La contraseña debe tener entre 8 y 60 caracteres")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,60}$",
            message = "La contraseña debe incluir al menos una letra mayúscula, un número y un carácter especial")
    private String password;

    // Campos extendidos (opcional para Cliente, pero obligatorios para Empresa e Independiente)
    private String fotoRepresentante;

    private String cartaPresentacion;

    private String mision;
    private String vision;

    private String pais;

    private String ciudad;

    private String dniAnverso;
    private String dniReverso;


    private String profesion;

    // Si es obligatorio para Independiente, descomenta la siguiente línea
    // @NotBlank(message = "La foto de título es obligatoria", groups = IndependienteGroup.class)
    private String fotoTitulo;


    private String nombreEmpresa;

    private String registroDeEmpresa;
    private String licenciaComercial;
    @Column(name = "area_trabajo")
    private String areaTrabajo;

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getFotoRepresentante() {
        return fotoRepresentante;
    }

    public void setFotoRepresentante(String fotoRepresentante) {
        this.fotoRepresentante = fotoRepresentante;
    }

    public String getCartaPresentacion() {
        return cartaPresentacion;
    }

    public void setCartaPresentacion(String cartaPresentacion) {
        this.cartaPresentacion = cartaPresentacion;
    }

    public String getMision() {
        return mision;
    }

    public void setMision(String mision) {
        this.mision = mision;
    }

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public String getDniAnverso() {
        return dniAnverso;
    }

    public void setDniAnverso(String dniAnverso) {
        this.dniAnverso = dniAnverso;
    }

    public String getDniReverso() {
        return dniReverso;
    }

    public void setDniReverso(String dniReverso) {
        this.dniReverso = dniReverso;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
}
