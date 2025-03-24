package com.professional.model.services;

import com.professional.model.dto.TrabajoEnAccionDTO;
import com.professional.model.entities.Cliente;
import com.professional.model.entities.HistorialEmpresas;
import com.professional.model.enums.EstadoTrabajo;
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
     * Eliminar lógicamente un TrabajoEmpEnAccion por su ID.
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
    HistorialEmpresas updateEstadoTrabajo(Long id, EstadoTrabajo estadoTrabajo);

    /**
     * Buscar trabajos en acción que están activos.
     *
     * @param activo Estado de actividad.
     * @return Lista de trabajos en acción que coinciden con el estado.
     */
    List<TrabajoEmpEnAccion> findByActivo(Boolean activo);

    /**
     * Buscar trabajos en acción por estado de trabajo.
     *
     * @param estadoTrabajo Estado de trabajo.
     * @return Lista de trabajos que coinciden con el estado.
     */
    List<TrabajoEmpEnAccion> findByEstadoTrabajo(EstadoTrabajo estadoTrabajo);

    /**
     * Obtener todas las acciones asociadas a un Cliente específico.
     *
     * @param cliente Cliente del cual se desean las acciones.
     * @return Lista de TrabajoEmpEnAccion asociados al cliente.
     */
    List<TrabajoEmpEnAccion> getTrabajosEmpEnAccionByCliente(Cliente cliente);

    void updateEstadoTrabajoEnAccion(Long id, EstadoTrabajo estadoTrabajo);
}
