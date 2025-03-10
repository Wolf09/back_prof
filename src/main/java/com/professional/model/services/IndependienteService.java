package com.professional.model.services;

import com.professional.model.dto.TrabajoIndependienteDTO;
import com.professional.model.entities.Independiente;
import com.professional.model.entities.TrabajoIndependiente;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IndependienteService {

    /**
     * Obtener todos los Independientes. solo cuando activo sea true
     *
     * @return Lista de Independientes.
     */
    List<Independiente> getAllIndependientes();

    /**
     * Obtener todos los independientes esten activos o no
     */
    List<Independiente> getAllIndependientesTodos();
    /**
     * Obtener un Independiente por su ID.
     *
     * @param id ID del Independiente.
     * @return Independiente encontrado.
     */
    Independiente getIndependienteById(Long id);

    @Transactional(readOnly = true)
    Independiente findByCorreo(String correo);

    /**
     * Crear un nuevo Independiente.
     *
     * @param independiente Datos del Independiente a crear.
     * @return Independiente creado.
     */
    Independiente createIndependiente(Independiente independiente);

    /**
     * Actualizar un Independiente existente.
     *
     * @param id del Independiente a actualizar.
     * @param independienteDetalles Datos actualizados del Independiente.
     * @return Independiente actualizado.
     */
    Independiente updateIndependiente(Long id, Independiente independienteDetalles);

    /**
     * Eliminar un Independiente por su ID.
     *
     * @param id ID del Independiente a eliminar.
     */
    void deleteIndependiente(Long id);

    List<TrabajoIndependiente> getTrabajosIndependientesByIndependiente(Long independienteId);

    List<TrabajoIndependienteDTO> misTrabajosIndependientes(Long independienteId);
}

