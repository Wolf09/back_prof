package com.professional.model.services;

import com.professional.model.entities.Cliente;
import com.professional.model.entities.TrabajoEmpresa;
import com.professional.model.entities.Empresa;

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
    TrabajoEmpresa createTrabajoEmpresa(TrabajoEmpresa trabajoEmpresa);

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
     * Buscar trabajos de empresa por nombre.
     *
     * @param trabajo Nombre del trabajo.
     * @return Lista de trabajos que coinciden con el nombre.
     */
    List<TrabajoEmpresa> findByTrabajo(String trabajo);

    /**
     * Verificar si existe un trabajo de empresa con un nombre específico.
     *
     * @param trabajo Nombre del trabajo.
     * @return true si existe, false de lo contrario.
     */
    boolean existsByTrabajo(String trabajo);

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

}

