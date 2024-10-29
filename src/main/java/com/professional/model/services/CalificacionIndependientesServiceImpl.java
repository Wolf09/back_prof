package com.professional.model.services;

import com.professional.model.entities.CalificacionIndependientes;
import com.professional.model.entities.TrabajoIndependiente;
import com.professional.model.entities.Cliente;
import com.professional.model.exceptions.ResourceNotFoundException;
import com.professional.model.repositories.CalificacionIndependientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CalificacionIndependientesServiceImpl implements CalificacionIndependientesService {

    private final CalificacionIndependientesRepository calificacionRepository;
    private final TrabajoIndependienteService trabajoIndependienteService;
    private final ClienteService clienteService;

    @Autowired
    public CalificacionIndependientesServiceImpl(CalificacionIndependientesRepository calificacionRepository,
                                                 TrabajoIndependienteService trabajoIndependienteService,
                                                 ClienteService clienteService) {
        this.calificacionRepository = calificacionRepository;
        this.trabajoIndependienteService = trabajoIndependienteService;
        this.clienteService = clienteService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CalificacionIndependientes> getAllCalificaciones() {
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
        // Validaciones adicionales
        // Verificar que el Cliente y TrabajoIndependiente existan
        Cliente cliente = clienteService.getClienteById(calificacion.getCliente().getId());
        TrabajoIndependiente trabajo = trabajoIndependienteService.getTrabajoIndependienteById(calificacion.getTrabajo().getId());

        // Verificar si ya existe una calificación de este cliente para este trabajo
        if (calificacionRepository.existsByClienteAndTrabajo(cliente, trabajo)) {
            throw new IllegalStateException("El cliente ya ha calificado este trabajo independiente");
        }

        calificacion.setCliente(cliente);
        calificacion.setTrabajo(trabajo);

        CalificacionIndependientes creado = calificacionRepository.save(calificacion);

        // Actualizar el promedio de calificación en TrabajoIndependiente
        actualizarPromedioCalificacion(trabajo);

        return creado;
    }

    @Override
    @Transactional
    public CalificacionIndependientes updateCalificacion(Long id, CalificacionIndependientes calificacionDetalles) {
        CalificacionIndependientes existente = calificacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CalificacionIndependientes no encontrada con ID: " + id));

        // Actualizar campos permitidos
        existente.setRating(calificacionDetalles.getRating());
        existente.setComentarios(calificacionDetalles.getComentarios());

        // Asumimos que el Cliente y TrabajoIndependiente no se pueden cambiar en una calificación existente

        CalificacionIndependientes actualizado = calificacionRepository.save(existente);

        // Actualizar el promedio de calificación en TrabajoIndependiente
        actualizarPromedioCalificacion(actualizado.getTrabajo());

        return actualizado;
    }

    @Override
    @Transactional
    public void deleteCalificacion(Long id) {
        CalificacionIndependientes existente = calificacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CalificacionIndependientes no encontrada con ID: " + id));
        calificacionRepository.delete(existente);

        // Actualizar el promedio de calificación en TrabajoIndependiente
        actualizarPromedioCalificacion(existente.getTrabajo());
    }

    @Override
    @Transactional(readOnly = true)
    public CalificacionIndependientes getCalificacionByClienteAndTrabajo(Cliente cliente, TrabajoIndependiente trabajoIndependiente) {
        return calificacionRepository.findByClienteAndTrabajo(cliente, trabajoIndependiente)
                .orElseThrow(() -> new ResourceNotFoundException("CalificacionIndependientes no encontrada para el Cliente ID: "
                        + cliente.getId() + " y TrabajoIndependiente ID: " + trabajoIndependiente.getId()));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsCalificacionByClienteAndTrabajo(Cliente cliente, TrabajoIndependiente trabajoIndependiente) {
        return calificacionRepository.existsByClienteAndTrabajo(cliente, trabajoIndependiente);
    }

    /**
     * Método para actualizar el promedio de calificaciones de un TrabajoIndependiente
     *
     * @param trabajoIndependiente El TrabajoIndependiente cuyo promedio se actualizará
     */
    private void actualizarPromedioCalificacion(TrabajoIndependiente trabajoIndependiente) {
        Double promedio = calificacionRepository.findAverageRatingByTrabajo(trabajoIndependiente);
        if (promedio == null) {
            promedio = 0.0; // Puedes establecer un valor por defecto si no hay calificaciones
        }
        trabajoIndependiente.setAverageRating(promedio);
        trabajoIndependienteService.saveTrabajoIndependiente(trabajoIndependiente);
    }
}
