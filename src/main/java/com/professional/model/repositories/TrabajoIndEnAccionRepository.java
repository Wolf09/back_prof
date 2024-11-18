package com.professional.model.repositories;

import com.professional.model.entities.EstadoTrabajo;
import com.professional.model.entities.TrabajoIndEnAccion;
import com.professional.model.entities.TrabajoIndependiente;
import com.professional.model.entities.Cliente; // Asegúrate de importar Cliente
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TrabajoIndEnAccionRepository extends JpaRepository<TrabajoIndEnAccion, Long> {

    /**
     * Obtener todas las acciones de un trabajo independiente específico.
     *
     * @param trabajoIndependiente Trabajo independiente del cual se desean las acciones.
     * @return Lista de acciones asociadas al trabajo independiente.
     */
    List<TrabajoIndEnAccion> findByTrabajoIndependiente(TrabajoIndependiente trabajoIndependiente);


    /**
     * Buscar trabajos en acción que están activos.
     *
     * @param activo Estado de actividad.
     * @return Lista de trabajos en acción que coinciden con el estado.
     */
    List<TrabajoIndEnAccion> findByActivo(Boolean activo);

    /**
     * Buscar trabajos en acción por estado de trabajo.
     *
     * @param estadoTrabajo Estado de trabajo.
     * @return Lista de trabajos en acción que coinciden con el estado.
     */
    List<TrabajoIndEnAccion> findByEstadoTrabajo(EstadoTrabajo estadoTrabajo);

    /**
     * Buscar trabajos en acción asociados a un cliente específico.
     *
     * @param cliente Cliente asociado a los trabajos en acción.
     * @return Lista de trabajos en acción asociados al cliente.
     */
    List<TrabajoIndEnAccion> findByClienteAndActivoTrue(Cliente cliente);

    /**
     * Buscar trabajos en acción asociados a un cliente específico.
     *
     * @param cliente Cliente asociado a los trabajos en acción.
     * @return Lista de trabajos en acción asociados al cliente.
     */
    List<TrabajoIndEnAccion> findByTrabajoIndependiente_Cliente(Cliente cliente);
}
