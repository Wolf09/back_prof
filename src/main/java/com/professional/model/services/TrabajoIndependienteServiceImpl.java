package com.professional.model.services;

import com.professional.model.entities.TrabajoIndependiente;
import com.professional.model.exceptions.ResourceNotFoundException;
import com.professional.model.repositories.TrabajoIndependienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TrabajoIndependienteServiceImpl implements TrabajoIndependienteService {

    private final TrabajoIndependienteRepository trabajoIndependienteRepository;

    @Autowired
    public TrabajoIndependienteServiceImpl(TrabajoIndependienteRepository trabajoIndependienteRepository) {
        this.trabajoIndependienteRepository = trabajoIndependienteRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<TrabajoIndependiente> getAllTrabajosIndependientes() {
        return trabajoIndependienteRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public TrabajoIndependiente getTrabajoIndependienteById(Long id) {
        return trabajoIndependienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trabajo Independiente no encontrado con ID: " + id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public TrabajoIndependiente createTrabajoIndependiente(TrabajoIndependiente trabajoIndependiente) {
        // Puedes agregar validaciones adicionales aquí si es necesario.
        return trabajoIndependienteRepository.save(trabajoIndependiente);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public TrabajoIndependiente updateTrabajoIndependiente(Long id, TrabajoIndependiente trabajoIndependienteDetalles) {
        TrabajoIndependiente existente = trabajoIndependienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TrabajoIndependiente no encontrado con ID: " + id));

        // Actualizar campos permitidos
        existente.setTrabajo(trabajoIndependienteDetalles.getTrabajo());
        existente.setIndependiente(trabajoIndependienteDetalles.getIndependiente());
        existente.setCliente(trabajoIndependienteDetalles.getCliente());
        // El averageRating no se actualiza directamente, se recalcula mediante las calificaciones

        return trabajoIndependienteRepository.save(existente);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteTrabajoIndependiente(Long id) {
        TrabajoIndependiente existente = trabajoIndependienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trabajo Independiente no encontrado con ID: " + id));
        existente.setActivo(false);
        trabajoIndependienteRepository.save(existente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrabajoIndependiente> getAllTrabajosIndependientesActivos() {
        return trabajoIndependienteRepository.findByActivo(true);
    }

    @Override
    @Transactional
    public TrabajoIndependiente saveTrabajoIndependiente(TrabajoIndependiente trabajoIndependiente) {
        return trabajoIndependienteRepository.save(trabajoIndependiente);
    }
}

