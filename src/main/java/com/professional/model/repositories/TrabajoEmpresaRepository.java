package com.professional.model.repositories;

import com.professional.model.dto.FiltrosConsultasEmpresasDTO;
import com.professional.model.dto.FiltrosConsultasIndependientesDTO;
import com.professional.model.entities.Cliente;
import com.professional.model.entities.Empresa;
import com.professional.model.entities.TrabajoEmpEnAccion;
import com.professional.model.entities.TrabajoEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrabajoEmpresaRepository extends JpaRepository<TrabajoEmpresa, Long> {



    /**
     * Buscar trabajos de empresa por empresa.
     *
     * @param empresa Nombre de la empresa.
     * @return Lista de trabajos que coinciden con el nombre.
     */
    List<TrabajoEmpresa> findByEmpresa(Empresa empresa);

    /**
     * Buscar trabajos de empresa por cliente.
     *
     * @param cliente Cliente asociado a los trabajos.
     * @return Lista de trabajos asociados al cliente.
     */
    List<TrabajoEmpresa> findByCliente(Cliente cliente);

    /**
     * Buscar trabajos de empresa que están activos.
     *
     * @param activo Estado de actividad.
     * @return Lista de trabajos que coinciden con el estado.
     */
    List<TrabajoEmpresa> findByActivo(Boolean activo);
    /**
     * Buscar un trabajo de empresa por su ID.
     *
     * @param id ID del trabajo de empresa.
     * @return Un Optional que contiene el trabajo si se encuentra, o vacío si no.
     */
    Optional<TrabajoEmpresa> findById(Long id);



    Optional<TrabajoEmpEnAccion> findByIdAndActivoTrue(Long id);

    /**
     * Busca trabajos de empresas por descripción utilizando LIKE y filtra por activo=true.
     *
     * @param descripcion Parte de la descripción a buscar.
     * @return Lista de trabajos de empresas que coinciden.
     */
    List<TrabajoEmpresa> findByDescripcionContainingIgnoreCaseAndActivoTrue(String descripcion);

    /**
     * Busca trabajos de empresa por descripción utilizando LIKE, activo=true, y dentro de un rango de calificación.
     *
     * @param descripcion Parte de la descripción a buscar.
     * @param minRating  Calificación mínima (inclusive).
     * @param maxRating  Calificación máxima (inclusive).
     * @return Lista de trabajos de empresa que coinciden.
     */
    List<TrabajoEmpresa> findByDescripcionContainingIgnoreCaseAndActivoTrueAndAverageRatingBetween(
            String descripcion, Double minRating, Double maxRating);

    /**
     * Consulta personalizada que devuelve los datos requeridos en el DTO,
     * haciendo join con la entidad Independiente.
     */
    @Query("select new com.professional.model.dto.FiltrosConsultasEmpresasDTO("+
            "e.id,t.id,e.nombreEmpresa,e.fotoRepresentante,e.tipoUsuario,e.areaTrabajo, "+
            "t.descripcion, t.averageRating, t.precio) " +
            "from TrabajoEmpresa t "+
            "join t.empresa e "+
            "where lower(t.descripcion) like lower(concat('%', :descripcion, '%')) " +
            "and lower(e.areaTrabajo) like lower(concat('%', :areaTrabajo, '%')) " +
            "and t.activo = true "+
            "and e.activo=true")
    List<FiltrosConsultasEmpresasDTO> findFiltrosByDescripcion(@Param("descripcion") String descripcion, @Param("areaTrabajo") String areaTrabajo);

    @Query("select new com.professional.model.dto.FiltrosConsultasEmpresasDTO("+
            "e.id,t.id,e.nombreEmpresa,e.fotoRepresentante,e.tipoUsuario,e.areaTrabajo, "+
            "t.descripcion, t.averageRating, t.precio) " +
            "from TrabajoEmpresa t "+
            "join t.empresa e "+
            "where lower(t.descripcion) like lower(concat('%', :descripcion, '%')) " +
            "and lower(e.areaTrabajo) like lower(concat('%', :areaTrabajo, '%')) " +
            "and t.activo = true "+
            "and e.activo=true "+
            "and t.averageRating>= 4.0 "+
            "order by t.precio asc")
    List<FiltrosConsultasEmpresasDTO> findFiltrosByDescripcionPrecioAsc4(@Param("descripcion") String descripcion,@Param("areaTrabajo") String areaTrabajo);

    @Query("select new com.professional.model.dto.FiltrosConsultasEmpresasDTO("+
            "e.id,t.id,e.nombreEmpresa,e.fotoRepresentante,e.tipoUsuario,e.areaTrabajo, "+
            "t.descripcion, t.averageRating, t.precio) " +
            "from TrabajoEmpresa t "+
            "join t.empresa e "+
            "where lower(t.descripcion) like lower(concat('%', :descripcion, '%')) " +
            "and lower(e.areaTrabajo) like lower(concat('%', :areaTrabajo, '%')) " +
            "and t.activo = true "+
            "and e.activo=true "+
            "and t.averageRating>= 3.0 "+
            "order by t.precio asc")
    List<FiltrosConsultasEmpresasDTO> findFiltrosByDescripcionPrecioAsc3(@Param("descripcion") String descripcion,@Param("areaTrabajo") String areaTrabajo);


    @Query("select new com.professional.model.dto.FiltrosConsultasEmpresasDTO("+
            "e.id,t.id,e.nombreEmpresa,e.fotoRepresentante,e.tipoUsuario,e.areaTrabajo, "+
            "t.descripcion, t.averageRating, t.precio) " +
            "from TrabajoEmpresa t "+
            "join t.empresa e "+
            "where lower(t.descripcion) like lower(concat('%', :descripcion, '%')) " +
            "and lower(e.areaTrabajo) like lower(concat('%', :areaTrabajo, '%')) " +
            "and t.activo = true "+
            "and e.activo=true "+
            "and t.averageRating>= 4.0 "+
            "order by t.precio desc")
    List<FiltrosConsultasEmpresasDTO> findFiltrosByDescripcionPrecioDesc4(@Param("descripcion") String descripcion,@Param("areaTrabajo") String areaTrabajo);


    @Query("select new com.professional.model.dto.FiltrosConsultasEmpresasDTO("+
            "e.id,t.id,e.nombreEmpresa,e.fotoRepresentante,e.tipoUsuario,e.areaTrabajo, "+
            "t.descripcion, t.averageRating, t.precio) " +
            "from TrabajoEmpresa t "+
            "join t.empresa e "+
            "where lower(t.descripcion) like lower(concat('%', :descripcion, '%')) " +
            "and lower(e.areaTrabajo) like lower(concat('%', :areaTrabajo, '%')) " +
            "and t.activo = true "+
            "and e.activo=true "+
            "and t.averageRating>= 3.0 "+
            "order by t.precio desc")
    List<FiltrosConsultasEmpresasDTO> findFiltrosByDescripcionPrecioDesc3(@Param("descripcion") String descripcion,@Param("areaTrabajo") String areaTrabajo);

    @Query("select distinct new com.professional.model.dto.FiltrosConsultasEmpresasDTO("+
            "e.id,t.id,e.nombreEmpresa,e.fotoRepresentante,e.tipoUsuario,e.areaTrabajo, "+
            "t.descripcion, t.averageRating, t.precio, t.ventas) " +
            "from TrabajoEmpEnAccion a "+
            "join a.trabajoEmpresa t "+
            "join t.empresa e "+
            "where lower(t.descripcion) like lower(concat('%', :descripcion, '%')) " +
            "and lower(e.areaTrabajo) like lower(concat('%', :areaTrabajo, '%')) " +
            "and a.activo = true "+
            "and t.activo = true "+
            "and e.activo = true "+
            "and t.averageRating>=3.0 "+
            "order by t.ventas asc")
    List<FiltrosConsultasEmpresasDTO> findFiltrosByVentas3(@Param("descripcion") String descripcion,@Param("areaTrabajo") String areaTrabajo);

    @Query("select distinct new com.professional.model.dto.FiltrosConsultasEmpresasDTO("+
            "e.id,t.id,e.nombreEmpresa,e.fotoRepresentante,e.tipoUsuario,e.areaTrabajo, "+
            "t.descripcion, t.averageRating, t.precio, t.ventas) " +
            "from TrabajoEmpEnAccion a "+
            "join a.trabajoEmpresa t "+
            "join t.empresa e "+
            "where lower(t.descripcion) like lower(concat('%', :descripcion, '%')) " +
            "and lower(e.areaTrabajo) like lower(concat('%', :areaTrabajo, '%')) " +
            "and a.activo = true "+
            "and t.activo = true "+
            "and e.activo = true "+
            "and t.averageRating>=4.0 "+
            "order by t.ventas asc")
    List<FiltrosConsultasEmpresasDTO> findFiltrosByVentas4(@Param("descripcion") String descripcion,@Param("areaTrabajo") String areaTrabajo);

}

