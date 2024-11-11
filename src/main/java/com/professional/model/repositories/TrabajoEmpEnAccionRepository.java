package com.professional.model.repositories;

import com.professional.model.entities.Cliente;
import com.professional.model.entities.EstadoTrabajo;
import com.professional.model.entities.TrabajoEmpEnAccion;
import com.professional.model.entities.TrabajoEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrabajoEmpEnAccionRepository extends JpaRepository<TrabajoEmpEnAccion, Long> {

    /**
     * Obtener todas las acciones de un trabajo de empresa específico.
     *
     * @param trabajoEmpresa Trabajo de empresa del cual se desean las acciones.
     * @return Lista de acciones asociadas al trabajo de empresa.
     */
    List<TrabajoEmpEnAccion> findByTrabajoEmpresa(TrabajoEmpresa trabajoEmpresa);

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
     * @return Lista de trabajos en acción que coinciden con el estado.
     */
    List<TrabajoEmpEnAccion> findByEstadoTrabajo(EstadoTrabajo estadoTrabajo);

    /**
     * Buscar trabajos en acción asociados a un cliente específico a través de TrabajoEmpresa.
     *
     * @param cliente Cliente asociado a los trabajos en acción.
     * @return Lista de trabajos en acción asociados al cliente.
     */
    List<TrabajoEmpEnAccion> findByTrabajoEmpresa_Cliente(Cliente cliente);
}


