package com.professional.model.repositories;

import com.professional.model.dto.*;
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
     * Consulta personalizada para mis-trabajos-empresas
     */
    @Query("select new com.professional.model.dto.TrabajoEmpresaDTO(" +
            "i.id, t.id, i.nombres, i.apellidos, i.celular,i.direccion,i.fotoRepresentante,i.cartaPresentacion, i.mision, i.vision, i.pais, " +
            "i.ciudad, i.nombreEmpresa,i.registroDeEmpresa,i.licenciaComercial,i.fotoTitulo,i.areaTrabajo,t.descripcionCorta, t.descripcion, t.averageRating, t.activo, t.precio,t.ventas) " +
            "from TrabajoEmpresa t " +
            "join t.empresa i " +
            "where t.activo = true "+
            "and i.activo=true "+
            "and t.empresa.id= :id")
    List<TrabajoEmpresaDTO> misTrabajosEmpresas(@Param("id") Long id);


    /**
     * Consulta personalizada que devuelve los datos requeridos en el DTO,
     * haciendo join con la entidad Independiente.
     */
    @Query("select new com.professional.model.dto.FiltrosConsultasEmpresasDTO("+
            "e.id,t.id,e.nombreEmpresa,e.fotoRepresentante,e.tipoUsuario,e.areaTrabajo, "+
            "t.descripcionCorta, t.averageRating, t.precio,t.ventas,e.pais,e.ciudad) " +
            "from TrabajoEmpresa t "+
            "join t.empresa e "+
            "where lower(e.pais) like lower(concat('%', :pais, '%')) " +
            "and lower(e.ciudad) like lower(concat('%', :ciudad, '%')) " +
            "and lower(t.descripcionCorta) like lower(concat('%', :descripcionCorta, '%')) " +
            "and t.activo = true "+
            "and e.activo=true")
    List<FiltrosConsultasEmpresasDTO> findFiltrosByDescripcion(@Param("pais") String pais,@Param("ciudad") String ciudad,@Param("descripcionCorta") String descripcionCorta);

    @Query("select new com.professional.model.dto.FiltrosConsultasEmpresasDTO("+
            "e.id,t.id,e.nombreEmpresa,e.fotoRepresentante,e.tipoUsuario,e.areaTrabajo, "+
            "t.descripcionCorta, t.averageRating, t.precio,t.ventas,e.pais,e.ciudad) " +
            "from TrabajoEmpresa t "+
            "join t.empresa e "+
            "where lower(e.pais) like lower(concat('%', :pais, '%')) " +
            "and lower(e.ciudad) like lower(concat('%', :ciudad, '%')) " +
            "and lower(t.descripcionCorta) like lower(concat('%', :descripcionCorta, '%')) " +
            "and t.activo = true "+
            "and e.activo=true "+
            "and t.averageRating>= 4.0 "+
            "order by t.precio asc")
    List<FiltrosConsultasEmpresasDTO> findFiltrosByDescripcionPrecioAsc4(@Param("pais") String pais,@Param("ciudad") String ciudad,@Param("descripcionCorta") String descripcionCorta);

    @Query("select new com.professional.model.dto.FiltrosConsultasEmpresasDTO("+
            "e.id,t.id,e.nombreEmpresa,e.fotoRepresentante,e.tipoUsuario,e.areaTrabajo, "+
            "t.descripcionCorta, t.averageRating, t.precio,t.ventas,e.pais,e.ciudad) " +
            "from TrabajoEmpresa t "+
            "join t.empresa e "+
            "where lower(e.pais) like lower(concat('%', :pais, '%')) " +
            "and lower(e.ciudad) like lower(concat('%', :ciudad, '%')) " +
            "and lower(t.descripcionCorta) like lower(concat('%', :descripcionCorta, '%')) " +
            "and t.activo = true "+
            "and e.activo=true "+
            "and t.averageRating>= 3.0 "+
            "order by t.precio asc")
    List<FiltrosConsultasEmpresasDTO> findFiltrosByDescripcionPrecioAsc3(@Param("pais") String pais,@Param("ciudad") String ciudad,@Param("descripcionCorta") String descripcionCorta);


    @Query("select new com.professional.model.dto.FiltrosConsultasEmpresasDTO("+
            "e.id,t.id,e.nombreEmpresa,e.fotoRepresentante,e.tipoUsuario,e.areaTrabajo, "+
            "t.descripcionCorta, t.averageRating, t.precio,t.ventas,e.pais,e.ciudad) " +
            "from TrabajoEmpresa t "+
            "join t.empresa e "+
            "where lower(e.pais) like lower(concat('%', :pais, '%')) " +
            "and lower(e.ciudad) like lower(concat('%', :ciudad, '%')) " +
            "and lower(t.descripcionCorta) like lower(concat('%', :descripcionCorta, '%')) " +
            "and t.activo = true "+
            "and e.activo=true "+
            "and t.averageRating>= 4.0 "+
            "order by t.precio desc")
    List<FiltrosConsultasEmpresasDTO> findFiltrosByDescripcionPrecioDesc4(@Param("pais") String pais,@Param("ciudad") String ciudad,@Param("descripcionCorta") String descripcionCorta);


    @Query("select new com.professional.model.dto.FiltrosConsultasEmpresasDTO("+
            "e.id,t.id,e.nombreEmpresa,e.fotoRepresentante,e.tipoUsuario,e.areaTrabajo, "+
            "t.descripcionCorta, t.averageRating, t.precio,t.ventas,e.pais,e.ciudad) " +
            "from TrabajoEmpresa t "+
            "join t.empresa e "+
            "where lower(e.pais) like lower(concat('%', :pais, '%')) " +
            "and lower(e.ciudad) like lower(concat('%', :ciudad, '%')) " +
            "and lower(t.descripcionCorta) like lower(concat('%', :descripcionCorta, '%')) " +
            "and t.activo = true "+
            "and e.activo=true "+
            "and t.averageRating>= 3.0 "+
            "order by t.precio desc")
    List<FiltrosConsultasEmpresasDTO> findFiltrosByDescripcionPrecioDesc3(@Param("pais") String pais,@Param("ciudad") String ciudad,@Param("descripcionCorta") String descripcionCorta);

    @Query("select new com.professional.model.dto.FiltrosConsultasEmpresasDTO("+
            "e.id,t.id,e.nombreEmpresa,e.fotoRepresentante,e.tipoUsuario,e.areaTrabajo, "+
            "t.descripcionCorta, t.averageRating, t.precio,t.ventas,e.pais,e.ciudad) " +
            "from TrabajoEmpresa t "+
            "join t.empresa e "+
            "where lower(e.pais) like lower(concat('%', :pais, '%')) " +
            "and lower(e.ciudad) like lower(concat('%', :ciudad, '%')) " +
            "and lower(t.descripcionCorta) like lower(concat('%', :descripcionCorta, '%')) " +
            "and t.activo = true "+
            "and e.activo=true "+
            "and t.averageRating>=3.0 "+
            "order by t.ventas asc")
    List<FiltrosConsultasEmpresasDTO> findFiltrosByVentas3(@Param("pais") String pais,@Param("ciudad") String ciudad,@Param("descripcionCorta") String descripcionCorta);

    @Query("select new com.professional.model.dto.FiltrosConsultasEmpresasDTO("+
            "e.id,t.id,e.nombreEmpresa,e.fotoRepresentante,e.tipoUsuario,e.areaTrabajo, "+
            "t.descripcionCorta, t.averageRating, t.precio,t.ventas,e.pais,e.ciudad) " +
            "from TrabajoEmpresa t "+
            "join t.empresa e "+
            "where lower(e.pais) like lower(concat('%', :pais, '%')) " +
            "and lower(e.ciudad) like lower(concat('%', :ciudad, '%')) " +
            "and lower(t.descripcionCorta) like lower(concat('%', :descripcionCorta, '%')) " +
            "and t.activo = true "+
            "and e.activo=true "+
            "and t.averageRating>=4.0 "+
            "order by t.ventas asc")
    List<FiltrosConsultasEmpresasDTO> findFiltrosByVentas4(@Param("pais") String pais,@Param("ciudad") String ciudad,@Param("descripcionCorta") String descripcionCorta);

    @Query("select new com.professional.model.dto.FiltroTrabajoEmpresaDTO("+
            "e.id,t.id,e.nombreEmpresa,e.fotoRepresentante,e.tipoUsuario,e.areaTrabajo, "+
            "t.descripcionCorta, t.descripcion,t.averageRating, t.precio,t.ventas,e.pais,e.ciudad,e.celular) " +
            "from TrabajoEmpresa t "+
            "join t.empresa e "+
            "where t.id= :id " +
            "and t.activo = true "+
            "and e.activo=true")
    FiltroTrabajoEmpresaDTO findTrabajosEmpByCliente(@Param("id") Long id);


    @Query("select new com.professional.model.dto.FiltroTrabajoIndependienteDTO("+
            "e.id,t.id,e.nombres,e.apellidos,e.fotoRepresentante,e.tipoUsuario,e.profesion,e.areaTrabajo, "+
            "e.pais,e.ciudad,t.descripcionCorta, t.descripcion,t.averageRating, t.precio,t.ventas,e.celular) " +
            "from TrabajoIndependiente t "+
            "join t.independiente e "+
            "where t.id= :id " +
            "and t.activo = true "+
            "and e.activo=true")
    FiltroTrabajoIndependienteDTO findTrabajosIndByCliente(@Param("id") Long id);




}

