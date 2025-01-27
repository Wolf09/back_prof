package com.professional.model.services;

import com.professional.model.dto.HistorialDTO;
import com.professional.model.entities.Cliente;
import com.professional.model.entities.HistorialEmpresas;
import com.professional.model.entities.TrabajoEmpresa;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface HistorialEmpresasService {

    /**
     * Crear un nuevo HistorialEmpresas.
     *
     * @param historial Objeto HistorialEmpresas a crear.
     * @return HistorialEmpresas creado.
     */
    HistorialEmpresas createHistorialEmpresas(HistorialEmpresas historial);

    /**
     * Listar todos los HistorialEmpresas activos.
     *
     * @return Lista de HistorialEmpresas activos.
     */
    List<HistorialEmpresas> getAllHistorialEmpresas();

    /**
     * Listar todos los HistorialEmpresas, incluyendo inactivos.
     *
     * @return Lista de todos los HistorialEmpresas.
     */
    List<HistorialEmpresas> getAllHistorialEmpresasIncludingInactive();

    /**
     * Obtener un HistorialEmpresas por su ID (solo si está activo).
     *
     * @param id ID del HistorialEmpresas.
     * @return HistorialEmpresas encontrado.
     */
    HistorialEmpresas getHistorialEmpresasById(Long id);

    /**
     * Actualizar un HistorialEmpresas existente.
     *
     * @param id        ID del HistorialEmpresas a actualizar.
     * @param historial Datos actualizados del HistorialEmpresas.
     * @return HistorialEmpresas actualizado.
     */
    HistorialEmpresas updateHistorialEmpresas(Long id, HistorialEmpresas historial);

    /**
     * Eliminar (lógicamente) un HistorialEmpresas por su ID.
     *
     * @param id ID del HistorialEmpresas a eliminar.
     */
    void deleteHistorialEmpresas(Long id);

    /**
     * Buscar HistorialEmpresas por Cliente y TrabajoEmpresa activos.
     *
     * @param clienteId ID del Cliente.
     * @param trabajoId ID del TrabajoEmpresa.
     * @return Lista de HistorialEmpresas activos encontrados.
     */
    List<HistorialEmpresas> findByClienteAndTrabajo(Long clienteId, Long trabajoId);

    @Transactional(readOnly = true)
    List<HistorialEmpresas> getHistorialEmpresasByTrabajoEmpresa(TrabajoEmpresa trabajoEmpresa);

    @Transactional(readOnly = true)
    List<HistorialEmpresas> getHistorialEmpresasByCliente(Cliente cliente);

    HistorialDTO createHistorialEmpresasDTO(HistorialEmpresas historial);
}
