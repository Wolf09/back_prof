package com.professional.model.repositories;

import com.professional.model.entities.Cliente;
import com.professional.model.entities.HistorialEmpresas;
import com.professional.model.entities.TrabajoEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistorialEmpresasRepository extends JpaRepository<HistorialEmpresas, Long> {

    /**
     * Obtener el historial de un trabajo de empresa específico.
     *
     * @param trabajoEmpresa Trabajo de empresa cuyo historial se desea obtener.
     * @return Lista de registros de historial asociados al trabajo de empresa.
     */
    List<HistorialEmpresas> findByTrabajo(TrabajoEmpresa trabajoEmpresa);


    /**
     * Obtener el historial de un cliente específico.
     *
     * @param cliente Trabajo de empresa cuyo historial se desea obtener.
     * @return Lista de registros de historial asociados al trabajo de empresa.
     */
    List<HistorialEmpresas> findByCliente(Cliente cliente);

    // Agrega métodos de consulta personalizados aquí si es necesario.
}

