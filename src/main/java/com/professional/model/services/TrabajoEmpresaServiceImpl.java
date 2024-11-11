package com.professional.model.services;

import com.professional.model.entities.Cliente;
import com.professional.model.entities.TrabajoEmpresa;
import com.professional.model.entities.Empresa;
import com.professional.model.exceptions.ResourceNotFoundException;
import com.professional.model.repositories.TrabajoEmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TrabajoEmpresaServiceImpl implements TrabajoEmpresaService {

    private final TrabajoEmpresaRepository trabajoEmpresaRepository;

    @Autowired
    public TrabajoEmpresaServiceImpl(TrabajoEmpresaRepository trabajoEmpresaRepository) {
        this.trabajoEmpresaRepository = trabajoEmpresaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public TrabajoEmpresa getTrabajoEmpresaById(Long id) {
        return trabajoEmpresaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TrabajoEmpresa no encontrado con ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrabajoEmpresa> getAllTrabajosEmpresaActivos() {
        return trabajoEmpresaRepository.findByActivo(true);
    }


    @Override
    @Transactional
    public TrabajoEmpresa saveTrabajoEmpresa(TrabajoEmpresa trabajoEmpresa) {
        return trabajoEmpresaRepository.save(trabajoEmpresa);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrabajoEmpresa> getAllTrabajosEmpresa() {
        return trabajoEmpresaRepository.findAll();
    }

    @Override
    @Transactional
    public TrabajoEmpresa createTrabajoEmpresa(TrabajoEmpresa trabajoEmpresa) {
        // Establecer 'activo' en true al crear
        trabajoEmpresa.setActivo(true);
        return trabajoEmpresaRepository.save(trabajoEmpresa);
    }

    @Override
    @Transactional
    public TrabajoEmpresa updateTrabajoEmpresa(Long id, TrabajoEmpresa trabajoEmpresaDetalles) {
        TrabajoEmpresa existente = trabajoEmpresaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TrabajoEmpresa no encontrado con ID: " + id));

        // Actualizar campos permitidos
        existente.setTrabajo(trabajoEmpresaDetalles.getTrabajo());
        existente.setEmpresa(trabajoEmpresaDetalles.getEmpresa());
        existente.setCliente(trabajoEmpresaDetalles.getCliente());
        // El averageRating no se actualiza directamente, se recalcula mediante las calificaciones

        return trabajoEmpresaRepository.save(existente);
    }

    @Override
    @Transactional
    public void deleteTrabajoEmpresa(Long id) {
        TrabajoEmpresa existente = trabajoEmpresaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TrabajoEmpresa no encontrado con ID: " + id));
        // Eliminación lógica: establecer 'activo' a false
        existente.setActivo(false);
        trabajoEmpresaRepository.save(existente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrabajoEmpresa> getTrabajosEmpresaByCliente(Cliente cliente) {

        List<TrabajoEmpresa> trabajos = trabajoEmpresaRepository.findByCliente(cliente);
        if (trabajos.isEmpty()) {
            throw new ResourceNotFoundException("No hay trabajos empresariales asociados al Cliente ID: " + cliente.getId());
        }
        return trabajos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<TrabajoEmpresa> findByTrabajo(String trabajo) {
        return trabajoEmpresaRepository.findByTrabajo(trabajo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public boolean existsByTrabajo(String trabajo) {
        return trabajoEmpresaRepository.existsByTrabajo(trabajo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<TrabajoEmpresa> getTrabajosEmpresaByEmpresa(Empresa empresa) {
        return trabajoEmpresaRepository.findByEmpresa(empresa);
    }
}
