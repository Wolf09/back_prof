package com.professional.model.services;

import com.professional.model.entities.Independiente;

import java.util.List;

public interface IndependienteService {

    /**
     * Obtener todos los Independientes.
     *
     * @return Lista de Independientes.
     */
    List<Independiente> getAllIndependientes();

    /**
     * Obtener un Independiente por su ID.
     *
     * @param id ID del Independiente.
     * @return Independiente encontrado.
     */
    Independiente getIndependienteById(Long id);

    /**
     * Crear un nuevo Independiente.
     *
     * @param independiente Datos del Independiente a crear.
     * @return Independiente creado.
     */
    Independiente createIndependiente(Independiente independiente);

    /**
     * Actualizar un Independiente existente.
     *
     * @param id del Independiente a actualizar.
     * @param independienteDetalles Datos actualizados del Independiente.
     * @return Independiente actualizado.
     */
    Independiente updateIndependiente(Long id, Independiente independienteDetalles);

    /**
     * Eliminar un Independiente por su ID.
     *
     * @param id ID del Independiente a eliminar.
     */
    void deleteIndependiente(Long id);
}

