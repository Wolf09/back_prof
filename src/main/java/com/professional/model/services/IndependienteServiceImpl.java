package com.professional.model.services;

import com.professional.model.entities.Independiente;
import com.professional.model.exceptions.ResourceNotFoundException;
import com.professional.model.repositories.IndependienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// TODO ESTE SERVICIO YA ESTA, SOLO FALTAN LOGICAS ADICIONALES REFERENTES A LAS RELACIONES CON LOS OTROS ENTITIES
@Service
public class IndependienteServiceImpl implements IndependienteService {

    private final IndependienteRepository independienteRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public IndependienteServiceImpl(IndependienteRepository independienteRepository,
                                    PasswordEncoder passwordEncoder) {
        this.independienteRepository = independienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Independiente> getAllIndependientes() {
        return independienteRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Independiente getIndependienteById(Long id) {
        return independienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Independiente no encontrado con ID: " + id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Independiente createIndependiente(Independiente independiente) {
        // Puedes agregar validaciones adicionales aquí si es necesario.
        // Por ejemplo, verificar si el correo ya existe
        if (independienteRepository.existsByCorreo(independiente.getCorreo())){
            throw new IllegalStateException("El correo electrónico ya está en uso.");
        }
        independiente.setPassword(passwordEncoder.encode(independiente.getPassword()));
        return independienteRepository.save(independiente);
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

        if (independienteDetalles.getPagado() != null) {
            existente.setPagado(independienteDetalles.getPagado());
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
                .orElseThrow(() -> new ResourceNotFoundException("Independiente no encontrado con ID: " + id));
        independienteRepository.delete(existente);
    }
}

