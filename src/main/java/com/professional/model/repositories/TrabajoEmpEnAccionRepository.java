package com.professional.model.repositories;

import com.professional.model.dto.TrabajoEmpresaDTO;
import com.professional.model.dto.TrabajoEmpresaEnAccionDTO;
import com.professional.model.entities.Cliente;
import com.professional.model.enums.EstadoTrabajo;
import com.professional.model.entities.TrabajoEmpEnAccion;
import com.professional.model.entities.TrabajoEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("select new com.professional.model.dto.TrabajoEmpresaEnAccionDTO(" +
            "i.id, i.descripcionCorta, i.descripcion, i.precio,t.id, t.estadoTrabajo) " +
            "from TrabajoEmpEnAccion t " +
            "join t.trabajoEmpresa i " +
            "where t.activo = true "+
            "and i.activo=true "+
            //"and t.estadoTrabajo='FINALIZADO' "+
            "and t.trabajoEmpresa.id= :id")
    List<TrabajoEmpresaEnAccionDTO> misTrabajosEmpresas(@Param("id") Long id);
}


