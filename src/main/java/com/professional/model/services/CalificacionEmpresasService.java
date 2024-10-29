package com.professional.model.services;

import com.professional.model.entities.CalificacionEmpresas;
import com.professional.model.entities.TrabajoEmpresa;
import com.professional.model.entities.Cliente;

import java.util.List;

public interface CalificacionEmpresasService {

    /**
     * Obtener todas las calificaciones.
     *
     * @return Lista de CalificacionEmpresas.
     */
    List<CalificacionEmpresas> getAllCalificaciones();

    /**
     * Obtener una CalificacionEmpresas por su ID.
     *
     * @param id ID de la CalificacionEmpresas.
     * @return CalificacionEmpresas encontrada.
     */
    CalificacionEmpresas getCalificacionById(Long id);

    /**
     * Crear una nueva CalificacionEmpresas.
     *
     * @param calificacion Datos de la CalificacionEmpresas a crear.
     * @return CalificacionEmpresas creada.
     */
    CalificacionEmpresas createCalificacion(CalificacionEmpresas calificacion);

    /**
     * Actualizar una CalificacionEmpresas existente.
     *
     * @param id                ID de la CalificacionEmpresas a actualizar.
     * @param calificacionDetalles Datos actualizados de la CalificacionEmpresas.
     * @return CalificacionEmpresas actualizada.
     */
    CalificacionEmpresas updateCalificacion(Long id, CalificacionEmpresas calificacionDetalles);

    /**
     * Eliminar una CalificacionEmpresas por su ID.
     *
     * @param id ID de la CalificacionEmpresas a eliminar.
     */
    void deleteCalificacion(Long id);

    /**
     * Obtener una CalificacionEmpresas por Cliente y TrabajoEmpresa.
     *
     * @param cliente          Cliente que realizó la calificación.
     * @param trabajoEmpresa   TrabajoEmpresa calificado.
     * @return CalificacionEmpresas encontrada.
     */
    CalificacionEmpresas getCalificacionByClienteAndTrabajo(Cliente cliente, TrabajoEmpresa trabajoEmpresa);

    /**
     * Verificar si existe una CalificacionEmpresas para un Cliente y TrabajoEmpresa específicos.
     *
     * @param cliente          Cliente que realizó la calificación.
     * @param trabajoEmpresa   TrabajoEmpresa calificado.
     * @return true si existe, false de lo contrario.
     */
    boolean existsCalificacionByClienteAndTrabajo(Cliente cliente, TrabajoEmpresa trabajoEmpresa);
}
