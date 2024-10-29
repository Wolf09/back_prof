package com.professional.model.repositories;

import com.professional.model.entities.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    /**
     * Buscar una Empresa por su nombre.
     *
     * @param nombreEmpresa El nombre de la Empresa.
     * @return Un Optional que contiene la Empresa si se encuentra, o vacío si no.
     */
    Optional<Empresa> findByNombreEmpresa(String nombreEmpresa);

    /**
     * Verificar si existe una Empresa con un nombre específico.
     *
     * @param nombreEmpresa El nombre a verificar.
     * @return true si existe, false de lo contrario.
     */
    boolean existsByNombreEmpresa(String nombreEmpresa);

    /**
     * Verificar si existe una Empresa con un correo electrónico específico.
     *
     * @param correo El correo electrónico a verificar.
     * @return true si existe, false de lo contrario.
     */
    boolean existsByCorreo(String correo);
    /**
     * Buscar Empresas por área de trabajo.
     *
     * @param areaTrabajo El área de trabajo de la Empresa.
     * @return Lista de Empresas que coinciden con el área de trabajo.
     */
    List<Empresa> findByAreaTrabajo(String areaTrabajo);

    /**
     * Buscar Empresas por registro de empresa.
     *
     * @param registroDeEmpresa El registro de empresa a buscar.
     * @return Un Optional que contiene la Empresa si se encuentra, o vacío si no.
     */
    Optional<Empresa> findByRegistroDeEmpresa(String registroDeEmpresa);

    /**
     * Buscar Empresas por licencia comercial.
     *
     * @param licenciaComercial La licencia comercial a buscar.
     * @return Lista de Empresas que coinciden con la licencia comercial.
     */
    List<Empresa> findByLicenciaComercial(String licenciaComercial);

    // Agrega métodos de consulta personalizados aquí si los necesitas.
}

