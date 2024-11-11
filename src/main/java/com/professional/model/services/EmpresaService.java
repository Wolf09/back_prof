package com.professional.model.services;

import com.professional.model.entities.Empresa;
import com.professional.model.entities.Independiente;

import java.util.List;

public interface EmpresaService {

    /**
     * Obtener todas las Empresas. solo cuando activo sea true
     *
     * @return Lista de Empresas.
     */
    List<Empresa> getAllEmpresas();

    /**
     * Obtener todas las empresas esten activos o no
     */
    List<Empresa> getAllEmpresasTodos();
    /**
     * Obtener una Empresa por su ID.
     *
     * @param id ID de la Empresa.
     * @return Empresa encontrada.
     */
    Empresa getEmpresaById(Long id);

    /**
     * Crear una nueva Empresa.
     *
     * @param empresa Datos de la Empresa a crear.
     * @return Empresa creada.
     */
    Empresa createEmpresa(Empresa empresa);

    /**
     * Actualizar una Empresa existente.
     *
     * @param id               ID de la Empresa a actualizar.
     * @param empresaDetalles  Datos actualizados de la Empresa.
     * @return Empresa actualizada.
     */
    Empresa updateEmpresa(Long id, Empresa empresaDetalles);

    /**
     * Eliminar una Empresa por su ID.
     *
     * @param id ID de la Empresa a eliminar.
     */
    void deleteEmpresa(Long id);
}

