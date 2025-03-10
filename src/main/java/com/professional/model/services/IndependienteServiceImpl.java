package com.professional.model.services;

import com.professional.controller.exceptions.ResourceAlreadyExistsException;
import com.professional.model.dto.TrabajoIndependienteDTO;
import com.professional.model.entities.Independiente;
import com.professional.model.entities.TrabajoIndependiente;
import com.professional.model.exceptions.ResourceNotFoundException;
import com.professional.model.repositories.IndependienteRepository;
import com.professional.model.repositories.TrabajoIndependienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IndependienteServiceImpl implements IndependienteService {

    private final IndependienteRepository independienteRepository;
    private final PasswordEncoder passwordEncoder;
    private final TrabajoIndependienteRepository trabajoIndependienteRepository;

    private final TrabajoIndependienteService trabajoIndependienteService;

    @Autowired
    public IndependienteServiceImpl(IndependienteRepository independienteRepository,
                                    PasswordEncoder passwordEncoder, TrabajoIndependienteService trabajoIndependienteService, TrabajoIndependienteRepository trabajoIndependienteRepository, TrabajoIndependienteService trabajoIndependienteService1) {
        this.independienteRepository = independienteRepository;
        this.passwordEncoder = passwordEncoder;
        this.trabajoIndependienteRepository = trabajoIndependienteRepository;
        this.trabajoIndependienteService = trabajoIndependienteService1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Independiente> getAllIndependientes() {
        return independienteRepository.findByActivoTrue();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Independiente getIndependienteById(Long id) {
        Independiente independiente = independienteRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Independiente no encontrado con ID: " + id));

        if (!independiente.getActivo()) {
            throw new IllegalStateException("El usuario con ID: " + id + " está deshabilitado.");
        }

        return independiente;
    }

    @Transactional(readOnly = true)
    @Override
    public Independiente findByCorreo(String correo) {
        return independienteRepository.findByCorreo(correo)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa no encontrada con correo: " + correo));
    }

    /**
     * {@inheritDoc}
     * TODO, estoy aqui: d. Repositorio ClienteRepository
     */
    @Override
    @Transactional
    public Independiente createIndependiente(Independiente independiente) {
        return (Independiente) independienteRepository.findByCorreoAndActivoTrue(independiente.getCorreo())
                .map(indDB -> {
                    throw new ResourceAlreadyExistsException("Independiente con correo " + independiente.getCorreo() + " ya existe.");
                })
                .orElseGet(() ->{
                    independiente.setPassword(passwordEncoder.encode(independiente.getPassword()));
                    independiente.setActivo(true); // Asegurar que activo sea true al crear
                    return independienteRepository.save(independiente);
        });

    }


    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Independiente updateIndependiente(Long id, Independiente independienteDetalles) {
        Independiente existente = independienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Independiente no encontrado con ID: " + id));

        // Obtener el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Verificar que el usuario autenticado es el propietario de la cuenta
        if (!existente.getCorreo().equals(currentUsername)) {
            throw new AccessDeniedException("No tienes permiso para actualizar esta cuenta");
        }
        // Actualizar campos según sea necesario.
        existente.setNombres(independienteDetalles.getNombres());
        existente.setApellidos(independienteDetalles.getApellidos());
        existente.setCelular(independienteDetalles.getCelular());
        existente.setCorreo(independienteDetalles.getCorreo());
        existente.setCartaPresentacion(independienteDetalles.getCartaPresentacion());
        existente.setPais(independienteDetalles.getPais());
        existente.setCiudad(independienteDetalles.getCiudad());

        if (independienteDetalles.getMision() != null && !independienteDetalles.getMision().isEmpty()) {
            existente.setMision(independienteDetalles.getMision());
        }
        if (independienteDetalles.getFotoRepresentante() != null && !independienteDetalles.getFotoRepresentante().isEmpty()) {
            existente.setFotoRepresentante(independienteDetalles.getFotoRepresentante());
        }
        if (independienteDetalles.getFotoTitulo() != null && !independienteDetalles.getFotoTitulo().isEmpty()) {
            existente.setFotoTitulo(independienteDetalles.getFotoTitulo());
        }

        if (independienteDetalles.getVision() != null && !independienteDetalles.getVision().isEmpty()) {
            existente.setVision(independienteDetalles.getVision());
        }

        if (independienteDetalles.getDniAnverso() != null && !independienteDetalles.getDniAnverso().isEmpty()) {
            existente.setDniAnverso(independienteDetalles.getDniAnverso());
        }

        if (independienteDetalles.getDniReverso() != null && !independienteDetalles.getDniReverso().isEmpty()) {
            existente.setDniReverso(independienteDetalles.getDniReverso());
        }

        if (independienteDetalles.getFechaPagoInicio() != null) {
            existente.setFechaPagoInicio(independienteDetalles.getFechaPagoInicio());
        }

        if (independienteDetalles.getFechaPagoFin() != null) {
            existente.setFechaPagoFin(independienteDetalles.getFechaPagoFin());
        }

        if (independienteDetalles.getActivo() != null) {
            existente.setActivo(independienteDetalles.getActivo());
        }

        // Si se actualiza la contraseña, encriptarla
        if (independienteDetalles.getPassword() != null && !independienteDetalles.getPassword().isEmpty()) {
            existente.setPassword(passwordEncoder.encode(independienteDetalles.getPassword()));
        }
        // Manejar relaciones si es necesario.
        // Por ejemplo, actualizar la lista de trabajos independientes.

        return independienteRepository.save(existente);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteIndependiente(Long id) {
        Independiente existente = independienteRepository.findById(id)
                .filter(Independiente::getActivo)
                .orElseThrow(() -> new ResourceNotFoundException("Independiente no encontrado con ID: " + id+ " o el cliente ya esta Inactivo, puede actualizarlo para cambiar esto"));
        existente.setActivo(false); // Establecer activo a false
        independienteRepository.save(existente); // Guardar el cambio
    }

    @Override
    @Transactional(readOnly = true)
    public List<Independiente> getAllIndependientesTodos() {
        return independienteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrabajoIndependiente> getTrabajosIndependientesByIndependiente(Long independienteId) {
        Independiente independiente = getIndependienteById(independienteId);
        return trabajoIndependienteService.getTrabajosIndependientesByIndependiente(independiente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrabajoIndependienteDTO> misTrabajosIndependientes(Long independienteId) {
        Independiente independiente = getIndependienteById(independienteId);
        if (independiente == null) {
            throw new ResourceNotFoundException("Independiente no encontrado con ID: " + independienteId);
        }
        // Invocar el método del repositorio que retorna la lista de DTOs
        return trabajoIndependienteRepository.misTrabajosIndependientes(independienteId);
    }






}

