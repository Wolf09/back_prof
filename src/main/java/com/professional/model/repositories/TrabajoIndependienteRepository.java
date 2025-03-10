package com.professional.model.repositories;
import com.professional.model.dto.FiltrosConsultasIndependientesDTO;
import com.professional.model.dto.TrabajoIndependienteDTO;
import com.professional.model.entities.TrabajoIndependiente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrabajoIndependienteRepository extends JpaRepository<TrabajoIndependiente, Long> {


    /**
     * Buscar un trabajo independiente por su ID.
     *
     * @param id ID del trabajo independiente.
     * @return Un Optional que contiene el trabajo si se encuentra, o vacío si no.
     */
    Optional<TrabajoIndependiente> findById(Long id);

    /**
     * Buscar trabajos de empresa que están activos.
     *
     * @param activo Estado de actividad.
     * @return Lista de trabajos que coinciden con el estado.
     */
    List<TrabajoIndependiente> findByActivo(Boolean activo);

    /**
     * Buscar un TrabajoIndependiente por su ID y que esté activo.
     *
     * @param id ID del TrabajoIndependiente.
     * @return Optional de TrabajoIndependiente.
     */
    Optional<TrabajoIndependiente> findByIdAndActivoTrue(Long id);


    /**
     * Busca trabajos independientes por descripción utilizando LIKE y filtra por activo=true.
     *
     * @param descripcion Parte de la descripción a buscar.
     * @return Lista de trabajos independientes que coinciden.
     */
    List<TrabajoIndependiente> findByDescripcionContainingIgnoreCaseAndActivoTrue(String descripcion);
    /**
     * Busca trabajos independientes por descripción utilizando LIKE, activo=true, y dentro de un rango de calificación.
     *
     * @param descripcion Parte de la descripción a buscar.
     * @param minRating  Calificación mínima (inclusive).
     * @param maxRating  Calificación máxima (inclusive).
     * @return Lista de trabajos independientes que coinciden.
     */
    List<TrabajoIndependiente> findByDescripcionContainingIgnoreCaseAndActivoTrueAndAverageRatingBetween(
            String descripcion, Double minRating, Double maxRating);


    /**
     * Consulta personalizada para mis-trabajos-independientes
     */
    @Query("select new com.professional.model.dto.TrabajoIndependienteDTO(" +
            "i.id, t.id, i.profesion, i.nombres, i.apellidos, i.cartaPresentacion, i.mision, i.vision, i.pais, " +
            "i.ciudad, i.areaTrabajo,t.fechaCreacion, t.descripcionCorta, t.descripcion, t.averageRating, t.precio, t.ventas, i.fotoTitulo) " +
            "from TrabajoIndependiente t " +
            "join t.independiente i " +
            "where t.activo = true "+
            "and i.activo=true "+
            "and t.independiente.id= :id")
    List<TrabajoIndependienteDTO> misTrabajosIndependientes(@Param("id") Long id);


    /**
     * Consulta personalizada que devuelve los datos requeridos en el DTO,
     * haciendo join con la entidad Independiente. findFiltrosByDescripcion
     */
    @Query("select new com.professional.model.dto.FiltrosConsultasIndependientesDTO(" +
            "i.id, t.id, i.nombres, i.apellidos, i.fotoRepresentante, i.tipoUsuario, i.profesion, i.areaTrabajo, " +
            "t.descripcion, t.averageRating, t.precio) " +
            "from TrabajoIndependiente t " +
            "join t.independiente i " +
            "where lower(t.descripcion) like lower(concat('%', :descripcion, '%')) " +
            "and lower(i.areaTrabajo) like lower(concat('%', :areaTrabajo, '%')) " +
            "and t.activo = true "+
            "and i.activo=true")
    List<FiltrosConsultasIndependientesDTO> findFiltrosByDescripcion(@Param("descripcion") String descripcion,@Param("areaTrabajo") String areaTrabajo);

    /**
     * Consulta personalizada que devuelve los datos requeridos en el DTO,
     * haciendo join con la entidad Independiente. findFiltrosByDescripcionPrecioAsc4
     * TODO: implementar desde aqui igual para las busquedas en: TrabajoEmpresaRepository
     */
    @Query("select new com.professional.model.dto.FiltrosConsultasIndependientesDTO(" +
            "i.id, t.id, i.nombres, i.apellidos, i.fotoRepresentante, i.tipoUsuario, i.profesion, i.areaTrabajo, " +
            "t.descripcion, t.averageRating, t.precio) " +
            "from TrabajoIndependiente t " +
            "join t.independiente i " +
            "where lower(t.descripcion) like lower(concat('%', :descripcion, '%')) " +
            "and lower(i.areaTrabajo) like lower(concat('%', :areaTrabajo, '%')) " +
            "and t.activo = true "+
            "and i.activo=true "+
            "and t.averageRating >= 4.0 " +
            "order by t.precio asc")
    List<FiltrosConsultasIndependientesDTO> findFiltrosByDescripcionPrecioAsc4(@Param("descripcion") String descripcion,@Param("areaTrabajo") String areaTrabajo);

    /**
     * Consulta personalizada que devuelve los datos requeridos en el DTO,
     * haciendo join con la entidad Independiente. findFiltrosByDescripcionPrecioAsc3
     */
    @Query("select new com.professional.model.dto.FiltrosConsultasIndependientesDTO(" +
            "i.id, t.id, i.nombres, i.apellidos, i.fotoRepresentante, i.tipoUsuario, i.profesion, i.areaTrabajo, " +
            "t.descripcion, t.averageRating, t.precio) " +
            "from TrabajoIndependiente t " +
            "join t.independiente i " +
            "where lower(t.descripcion) like lower(concat('%', :descripcion, '%')) " +
            "and lower(i.areaTrabajo) like lower(concat('%', :areaTrabajo, '%')) " +
            "and t.activo = true "+
            "and i.activo=true "+
            "and t.averageRating >= 3.0 " +
            "order by t.precio asc")
    List<FiltrosConsultasIndependientesDTO> findFiltrosByDescripcionPrecioAsc3(@Param("descripcion") String descripcion,@Param("areaTrabajo") String areaTrabajo);

    /**
     * Consulta personalizada que devuelve los datos requeridos en el DTO,
     * haciendo join con la entidad Independiente. findFiltrosByDescripcionPrecioDesc4
     */
    @Query("select new com.professional.model.dto.FiltrosConsultasIndependientesDTO(" +
            "i.id, t.id, i.nombres, i.apellidos, i.fotoRepresentante, i.tipoUsuario, i.profesion, i.areaTrabajo, " +
            "t.descripcion, t.averageRating, t.precio) " +
            "from TrabajoIndependiente t " +
            "join t.independiente i " +
            "where lower(t.descripcion) like lower(concat('%', :descripcion, '%')) " +
            "and lower(i.areaTrabajo) like lower(concat('%', :areaTrabajo, '%')) " +
            "and t.activo = true "+
            "and i.activo=true "+
            "and t.averageRating >= 4.0 " +
            "order by t.precio desc")
    List<FiltrosConsultasIndependientesDTO> findFiltrosByDescripcionPrecioDesc4(@Param("descripcion") String descripcion,@Param("areaTrabajo") String areaTrabajo);

    /**
     * Consulta personalizada que devuelve los datos requeridos en el DTO,
     * haciendo join con la entidad Independiente. findFiltrosByDescripcionPrecioDesc3
     */
    @Query("select new com.professional.model.dto.FiltrosConsultasIndependientesDTO(" +
            "i.id, t.id, i.nombres, i.apellidos, i.fotoRepresentante, i.tipoUsuario, i.profesion, i.areaTrabajo, " +
            "t.descripcion, t.averageRating, t.precio) " +
            "from TrabajoIndependiente t " +
            "join t.independiente i " +
            "where lower(t.descripcion) like lower(concat('%', :descripcion, '%')) " +
            "and lower(i.areaTrabajo) like lower(concat('%', :areaTrabajo, '%')) " +
            "and t.activo = true "+
            "and i.activo=true "+
            "and t.averageRating >= 3.0 " +
            "order by t.precio desc")
    List<FiltrosConsultasIndependientesDTO> findFiltrosByDescripcionPrecioDesc3(@Param("descripcion") String descripcion,@Param("areaTrabajo") String areaTrabajo);


    @Query("select distinct new com.professional.model.dto.FiltrosConsultasIndependientesDTO(" +
            "i.id, t.id, i.nombres, i.apellidos, i.fotoRepresentante, i.tipoUsuario, i.profesion, i.areaTrabajo, " +
            "t.descripcion, t.averageRating, t.precio, t.ventas) " +
            "from TrabajoIndEnAccion a " +
            "join a.trabajoIndependiente t " +
            "join t.independiente i " +
            "where lower(t.descripcion) like lower(concat('%', :descripcion, '%')) " +
            "and lower(i.areaTrabajo) like lower(concat('%', :areaTrabajo, '%')) " +
            "and a.activo = true " +
            "and t.activo = true " +
            "and i.activo = true " +
            "and t.averageRating >= 3.0 " +
            "order by t.ventas asc")
    List<FiltrosConsultasIndependientesDTO> findFiltrosByVentas3(@Param("descripcion") String descripcion,
                                                                                @Param("areaTrabajo") String areaTrabajo);

    @Query("select distinct new com.professional.model.dto.FiltrosConsultasIndependientesDTO(" +
            "i.id, t.id, i.nombres, i.apellidos, i.fotoRepresentante, i.tipoUsuario, i.profesion, i.areaTrabajo, " +
            "t.descripcion, t.averageRating, t.precio, t.ventas) " +
            "from TrabajoIndEnAccion a " +
            "join a.trabajoIndependiente t " +
            "join t.independiente i " +
            "where lower(t.descripcion) like lower(concat('%', :descripcion, '%')) " +
            "and lower(i.areaTrabajo) like lower(concat('%', :areaTrabajo, '%')) " +
            "and a.activo = true " +
            "and t.activo = true " +
            "and i.activo = true " +
            "and t.averageRating >= 4.0 " +
            "order by t.ventas asc")
    List<FiltrosConsultasIndependientesDTO> findFiltrosByVentas4(@Param("descripcion") String descripcion,
                                                                 @Param("areaTrabajo") String areaTrabajo);


}

