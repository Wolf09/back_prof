package com.professional.model.services;

import com.professional.model.entities.EstadoTrabajo;
import com.professional.model.entities.TrabajoEmpEnAccion;
import com.professional.model.entities.TrabajoEmpresa;

import java.util.List;

public interface TrabajoEmpEnAccionService {

    /**
     * Obtener todas las acciones de trabajos de empresa.
     *
     * @return Lista de TrabajoEmpEnAccion.
     */
    List<TrabajoEmpEnAccion> getAllTrabajosEmpEnAccion();

    /**
     * Obtener un TrabajoEmpEnAccion por su ID.
     *
     * @param id ID del TrabajoEmpEnAccion.
     * @return TrabajoEmpEnAccion encontrado.
     */
    TrabajoEmpEnAccion getTrabajoEmpEnAccionById(Long id);

    /**
     * Crear un nuevo TrabajoEmpEnAccion.
     *
     * @param trabajoEmpEnAccion Datos del TrabajoEmpEnAccion a crear.
     * @return TrabajoEmpEnAccion creado.
     */
    TrabajoEmpEnAccion createTrabajoEmpEnAccion(TrabajoEmpEnAccion trabajoEmpEnAccion);

    /**
     * Actualizar un TrabajoEmpEnAccion existente.
     *
     * @param id                     ID del TrabajoEmpEnAccion a actualizar.
     * @param trabajoEmpEnAccionDetalles Datos actualizados del TrabajoEmpEnAccion.
     * @return TrabajoEmpEnAccion actualizado.
     */
    TrabajoEmpEnAccion updateTrabajoEmpEnAccion(Long id, TrabajoEmpEnAccion trabajoEmpEnAccionDetalles);

    /**
     * Eliminar un TrabajoEmpEnAccion por su ID.
     *
     * @param id ID del TrabajoEmpEnAccion a eliminar.
     */
    void deleteTrabajoEmpEnAccion(Long id);

    /**
     * Obtener todas las acciones asociadas a un TrabajoEmpresa específico.
     *
     * @param trabajoEmpresa TrabajoEmpresa del cual se desean las acciones.
     * @return Lista de TrabajoEmpEnAccion asociados.
     */
    List<TrabajoEmpEnAccion> getTrabajosEmpEnAccionByTrabajoEmpresa(TrabajoEmpresa trabajoEmpresa);

    /**
     * Actualizar el estadoTrabajo de un TrabajoEmpEnAccion existente.
     *
     * @param id ID del TrabajoEmpEnAccion a actualizar.
     * @param estadoTrabajo Nuevo estadoTrabajo.
     * @return TrabajoEmpEnAccion actualizado.
     */
    TrabajoEmpEnAccion updateEstadoTrabajo(Long id, EstadoTrabajo estadoTrabajo);
}

