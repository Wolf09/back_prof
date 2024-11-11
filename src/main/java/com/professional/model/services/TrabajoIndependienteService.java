package com.professional.model.services;

import com.professional.model.entities.TrabajoIndependiente;

import java.util.List;

public interface TrabajoIndependienteService {

    /**
     * Obtener todos los Trabajos Independientes.
     *
     * @return Lista de Trabajos Independientes.
     */
    List<TrabajoIndependiente> getAllTrabajosIndependientes();

    /**
     * Obtener un Trabajo Independiente por su ID.
     *
     * @param id ID del Trabajo Independiente.
     * @return Trabajo Independiente encontrado.
     */
    TrabajoIndependiente getTrabajoIndependienteById(Long id);

    /**
     * Crear un nuevo Trabajo Independiente.
     *
     * @param trabajoIndependiente Datos del Trabajo Independiente a crear.
     * @return Trabajo Independiente creado.
     */
    TrabajoIndependiente createTrabajoIndependiente(TrabajoIndependiente trabajoIndependiente);

    /**
     * Actualizar un Trabajo Independiente existente.
     *
     * @param id                      ID del Trabajo Independiente a actualizar.
     * @param trabajoIndependienteDetalles Datos actualizados del Trabajo Independiente.
     * @return Trabajo Independiente actualizado.
     */
    TrabajoIndependiente updateTrabajoIndependiente(Long id, TrabajoIndependiente trabajoIndependienteDetalles);

    /**
     * Eliminar un Trabajo Independiente por su ID.
     *
     * @param id ID del Trabajo Independiente a eliminar.
     */
    void deleteTrabajoIndependiente(Long id);

    List<TrabajoIndependiente> getAllTrabajosIndependientesActivos();

    /**
     * Guardar o actualizar un TrabajoIndependiente.
     *
     * @param trabajoIndependiente La entidad TrabajoIndependiente a guardar.
     * @return TrabajoIndependiente guardado.
     */
    TrabajoIndependiente saveTrabajoIndependiente(TrabajoIndependiente trabajoIndependiente);
}
