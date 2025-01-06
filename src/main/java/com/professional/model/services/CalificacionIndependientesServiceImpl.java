package com.professional.model.services;

import com.professional.controller.exceptions.InvalidOperationException;
import com.professional.model.entities.*;
import com.professional.model.enums.EstadoTrabajo;
import com.professional.model.exceptions.ResourceNotFoundException;

import com.professional.model.repositories.CalificacionIndependientesRepository;
import com.professional.model.repositories.TrabajoIndEnAccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CalificacionIndependientesServiceImpl implements CalificacionIndependientesService {

    private final CalificacionIndependientesRepository calificacionRepository;
    private final TrabajoIndependienteService trabajoIndependienteService;
    private final ClienteService clienteService;
    private final TrabajoIndEnAccionRepository trabajoIndEnAccion;

    @Autowired
    public CalificacionIndependientesServiceImpl(CalificacionIndependientesRepository calificacionRepository,
                                                 TrabajoIndependienteService trabajoIndependienteService,
                                                 ClienteService clienteService,
                                                 TrabajoIndEnAccionRepository trabajoIndEnAccion) {
        this.calificacionRepository = calificacionRepository;
        this.trabajoIndependienteService = trabajoIndependienteService;
        this.clienteService = clienteService;
        this.trabajoIndEnAccion = trabajoIndEnAccion;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CalificacionIndependientes> getAllCalificaciones() {
        // Obtener todas las calificaciones
        return calificacionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public CalificacionIndependientes getCalificacionById(Long id) {
        return calificacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CalificacionIndependientes no encontrada con ID: " + id));
    }

    @Override
    @Transactional
    public CalificacionIndependientes createCalificacion(CalificacionIndependientes calificacion) {
        // Obtener Cliente y TrabajoIndEnAccion
        Cliente cliente = clienteService.getClienteById(calificacion.getCliente().getId());
        TrabajoIndEnAccion trabajoIndEnAccion = this.trabajoIndEnAccion.findById(calificacion.getTrabajoIndEnAccion().getId())
                .orElseThrow(() -> new ResourceNotFoundException("TrabajoIndEnAccion no encontrado con ID: " + calificacion.getTrabajoIndEnAccion().getId()));

        // Verificar que el Cliente es el creador del TrabajoIndEnAccion
        if (!trabajoIndEnAccion.getCliente().getId().equals(cliente.getId())) {
            throw new InvalidOperationException("El cliente no es el creador de este TrabajoIndEnAccion");
        }

        // Verificar que el TrabajoIndEnAccion está finalizado
        if (!trabajoIndEnAccion.getEstadoTrabajo().equals(EstadoTrabajo.FINALIZADO)) {
            throw new InvalidOperationException("No se puede calificar un TrabajoIndEnAccion que no ha sido finalizado");
        }

        // Verificar si ya existe una calificación de este cliente para este TrabajoIndEnAccion
        if (calificacionRepository.existsByClienteAndTrabajoIndEnAccion(cliente, trabajoIndEnAccion)) {
            throw new InvalidOperationException("El cliente ya ha calificado este TrabajoIndEnAccion");
        }

        // Establecer relaciones
        calificacion.setCliente(cliente);
        calificacion.setTrabajoIndEnAccion(trabajoIndEnAccion);

        CalificacionIndependientes creada = calificacionRepository.save(calificacion);

        // Actualizar el promedio de calificación en TrabajoIndependiente
        actualizarPromedioCalificacion(trabajoIndEnAccion);

        return creada;
    }

    @Override
    @Transactional
    public CalificacionIndependientes updateCalificacion(Long id, CalificacionIndependientes calificacionDetalles) {
        CalificacionIndependientes existente = calificacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CalificacionIndependientes no encontrada con ID: " + id));

        // Actualizar campos permitidos
        existente.setRating(calificacionDetalles.getRating());
        existente.setComentarios(calificacionDetalles.getComentarios());

        CalificacionIndependientes actualizado = calificacionRepository.save(existente);

        // Actualizar el promedio de calificación en TrabajoIndEnAccion
        actualizarPromedioCalificacion(actualizado.getTrabajoIndEnAccion());

        return actualizado;
    }

    @Override
    @Transactional
    public void deleteCalificacion(Long id) {
        CalificacionIndependientes existente = calificacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CalificacionIndependientes no encontrada con ID: " + id));
        calificacionRepository.delete(existente);

        // Actualizar el promedio de calificación en TrabajoIndEnAccion
        actualizarPromedioCalificacion(existente.getTrabajoIndEnAccion());
    }

    @Override
    @Transactional(readOnly = true)
    public CalificacionIndependientes getCalificacionByClienteAndTrabajo(Cliente cliente, TrabajoIndEnAccion trabajoIndependiente) {
        return calificacionRepository.findByClienteAndTrabajoIndEnAccion(cliente, trabajoIndependiente)
                .orElseThrow(() -> new ResourceNotFoundException("CalificacionIndependientes no encontrada para el Cliente ID: "
                        + cliente.getId() + " y TrabajoIndEnAccion ID: " + trabajoIndependiente.getId()));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsCalificacionByClienteAndTrabajo(Cliente cliente, TrabajoIndEnAccion trabajoIndEnAccion) {
        return calificacionRepository.existsByClienteAndTrabajoIndEnAccion(cliente, trabajoIndEnAccion);
    }



    /**
     * Método para actualizar el promedio de calificaciones de un TrabajoIndEnAccion
     *
     * @param trabajoIndEnAccion El TrabajoIndEnAccion cuyo promedio se actualizará
     */
    private void actualizarPromedioCalificacion(TrabajoIndEnAccion trabajoIndEnAccion) {
        Double promedio = calificacionRepository.findAverageRatingByTrabajoIndEnAccion(trabajoIndEnAccion);
        if (promedio == null) {
            promedio = 5.0; // Valor por defecto si no hay calificaciones
        }
        trabajoIndEnAccion.getTrabajoIndependiente().setAverageRating(promedio);
        trabajoIndependienteService.saveTrabajoIndependiente(trabajoIndEnAccion.getTrabajoIndependiente());
    }
}
