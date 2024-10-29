package com.professional.model.services;

import com.professional.model.entities.HistorialEmpresas;
import com.professional.model.entities.TrabajoEmpresa;
import com.professional.model.entities.Cliente;

import java.util.List;

public interface HistorialEmpresasService {

    /**
     * Obtener todos los registros de HistorialEmpresas.
     *
     * @return Lista de HistorialEmpresas.
     */
    List<HistorialEmpresas> getAllHistorialEmpresas();

    /**
     * Obtener un HistorialEmpresas por su ID.
     *
     * @param id ID del HistorialEmpresas.
     * @return HistorialEmpresas encontrado.
     */
    HistorialEmpresas getHistorialEmpresasById(Long id);

    /**
     * Crear un nuevo HistorialEmpresas.
     *
     * @param historialEmpresas Datos del HistorialEmpresas a crear.
     * @return HistorialEmpresas creado.
     */
    HistorialEmpresas createHistorialEmpresas(HistorialEmpresas historialEmpresas);

    /**
     * Actualizar un HistorialEmpresas existente.
     *
     * @param id                        ID del HistorialEmpresas a actualizar.
     * @param historialEmpresasDetalles Datos actualizados del HistorialEmpresas.
     * @return HistorialEmpresas actualizado.
     */
    HistorialEmpresas updateHistorialEmpresas(Long id, HistorialEmpresas historialEmpresasDetalles);

    /**
     * Eliminar un HistorialEmpresas por su ID.
     *
     * @param id ID del HistorialEmpresas a eliminar.
     */
    void deleteHistorialEmpresas(Long id);

    /**
     * Obtener todos los HistorialEmpresas asociados a un TrabajoEmpresa específico.
     *
     * @param trabajoEmpresa TrabajoEmpresa cuyo historial se desea obtener.
     * @return Lista de HistorialEmpresas asociados.
     */
    List<HistorialEmpresas> getHistorialEmpresasByTrabajoEmpresa(TrabajoEmpresa trabajoEmpresa);

    /**
     * Obtener todos los HistorialEmpresas asociados a un Cliente específico.
     *
     * @param cliente Cliente cuyo historial se desea obtener.
     * @return Lista de HistorialEmpresas asociados.
     */
    List<HistorialEmpresas> getHistorialEmpresasByCliente(Cliente cliente);
}

