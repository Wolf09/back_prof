package com.professional.model.services;

import com.professional.model.dto.TrabajoEmpresaDTO;
import com.professional.model.dto.TrabajoEmpresaEnAccionDTO;
import com.professional.model.entities.*;
import com.professional.model.enums.EstadoTrabajo;
import com.professional.model.exceptions.ResourceNotFoundException;
import com.professional.model.repositories.TrabajoEmpEnAccionRepository;
import com.professional.model.repositories.TrabajoEmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TrabajoEmpresaServiceImpl implements TrabajoEmpresaService {

    private final TrabajoEmpresaRepository trabajoEmpresaRepository;
    private final EmpresaService empresaService;

    private final HistorialEmpresasService historialEmpresasService;

    private final TrabajoEmpEnAccionRepository trabajoEmpEnAccionRepository;

    @Autowired
    public TrabajoEmpresaServiceImpl(TrabajoEmpresaRepository trabajoEmpresaRepository,
                                     EmpresaService empresaService, HistorialEmpresasService historialEmpresasService,
                                     TrabajoEmpEnAccionRepository trabajoEmpEnAccionRepository) {
        this.trabajoEmpresaRepository = trabajoEmpresaRepository;
        this.empresaService = empresaService;
        this.historialEmpresasService = historialEmpresasService;
        this.trabajoEmpEnAccionRepository = trabajoEmpEnAccionRepository;
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
    public TrabajoEmpresa createTrabajoEmpresa(Long id,TrabajoEmpresa trabajoEmpresa) {

        Empresa empresa = empresaService.getEmpresaById(id);
        trabajoEmpresa.setEmpresa(empresa);
        trabajoEmpresa.setActivo(true);
        trabajoEmpresa.setAverageRating(5.0);
        return trabajoEmpresaRepository.save(trabajoEmpresa);
    }

    @Override
    @Transactional
    public TrabajoEmpresa updateTrabajoEmpresa(Long id, TrabajoEmpresa trabajoEmpresaDetalles) {
        HistorialEmpresas historialEmpresas=null;
        TrabajoEmpresa existente = trabajoEmpresaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TrabajoEmpresa no encontrado con ID: " + id));
        // Actualizar campos permitidos
        existente.setDescripcion(trabajoEmpresaDetalles.getDescripcion());
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
    public List<TrabajoEmpresa> getTrabajosEmpresaByEmpresa(Empresa empresa) {
        return trabajoEmpresaRepository.findByEmpresa(empresa);
    }


    @Override
    @Transactional(readOnly = true)
    public List<TrabajoEmpresaEnAccionDTO> misTrabajosEmpresasEnAccion(Long trabajoEmpresaId) {
        TrabajoEmpresa tempresa = getTrabajoEmpresaById(trabajoEmpresaId);
        if (tempresa == null) {
            throw new ResourceNotFoundException("Trabajo Empresa no encontrado con ID: " + trabajoEmpresaId);
        }
        return trabajoEmpEnAccionRepository.misTrabajosEmpresas(trabajoEmpresaId);
    }


}
