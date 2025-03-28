package com.professional.model.services;

import com.professional.model.dto.TrabajoEmpresaEnAccionDTO;
import com.professional.model.entities.Cliente;
import com.professional.model.entities.HistorialEmpresas;
import com.professional.model.entities.TrabajoEmpresa;
import com.professional.model.entities.Empresa;
import com.professional.model.enums.EstadoTrabajo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TrabajoEmpresaService {

    /**
     * Obtener todos los trabajos de empresa.
     *
     * @return Lista de TrabajoEmpresa.
     */
    List<TrabajoEmpresa> getAllTrabajosEmpresa();

    /**
     * Obtener un TrabajoEmpresa por su ID.
     *
     * @param id ID del TrabajoEmpresa.
     * @return TrabajoEmpresa encontrado.
     */
    TrabajoEmpresa getTrabajoEmpresaById(Long id);

    List<TrabajoEmpresa> getAllTrabajosEmpresaActivos();

    /**
     * Guardar o actualizar un TrabajoEmpresa.
     *
     * @param trabajoEmpresa La entidad TrabajoEmpresa a guardar.
     * @return TrabajoEmpresa guardado.
     */
    TrabajoEmpresa saveTrabajoEmpresa(TrabajoEmpresa trabajoEmpresa);
    /**
     * Crear un nuevo TrabajoEmpresa.
     *
     * @param trabajoEmpresa Datos del TrabajoEmpresa a crear.
     * @return TrabajoEmpresa creado.
     */
    TrabajoEmpresa createTrabajoEmpresa(Long id, TrabajoEmpresa trabajoEmpresa);

    /**
     * Actualizar un TrabajoEmpresa existente.
     *
     * @param id                  ID del TrabajoEmpresa a actualizar.
     * @param trabajoEmpresaDetalles Datos actualizados del TrabajoEmpresa.
     * @return TrabajoEmpresa actualizado.
     */
    TrabajoEmpresa updateTrabajoEmpresa(Long id, TrabajoEmpresa trabajoEmpresaDetalles);

    /**
     * Eliminar un TrabajoEmpresa por su ID.
     *
     * @param id ID del TrabajoEmpresa a eliminar.
     */
    void deleteTrabajoEmpresa(Long id);


    /**
     * Obtener todos los TrabajosEmpresa asociados a una Empresa específica.
     *
     * @param empresa Empresa de la cual se desean los trabajos.
     * @return Lista de TrabajoEmpresa asociados.
     */
    List<TrabajoEmpresa> getTrabajosEmpresaByEmpresa(Empresa empresa);
    /**
     * Obtener todos los TrabajosEmpresa asociados a un Cliente (Empresa).
     *
     * @param cliente El Cliente (Empresa).
     * @return Lista de TrabajoEmpresa.
     */
    List<TrabajoEmpresa> getTrabajosEmpresaByCliente(Cliente cliente);

    List<TrabajoEmpresaEnAccionDTO> misTrabajosEmpresasEnAccion(Long trabajoEmpresaId);


}

