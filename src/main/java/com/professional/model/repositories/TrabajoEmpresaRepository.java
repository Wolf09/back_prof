package com.professional.model.repositories;

import com.professional.model.entities.Cliente;
import com.professional.model.entities.Empresa;
import com.professional.model.entities.TrabajoEmpEnAccion;
import com.professional.model.entities.TrabajoEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrabajoEmpresaRepository extends JpaRepository<TrabajoEmpresa, Long> {

    /**
     * Buscar trabajos de empresa por nombre.
     *
     * @param trabajo Nombre del trabajo.
     * @return Lista de trabajos que coinciden con el nombre.
     */
    List<TrabajoEmpresa> findByTrabajo(String trabajo);

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

    /**
     * Verificar si existe un trabajo de empresa con un nombre específico.
     *
     * @param trabajo Nombre del trabajo.
     * @return true si existe, false de lo contrario.
     */
    boolean existsByTrabajo(String trabajo);
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

}

