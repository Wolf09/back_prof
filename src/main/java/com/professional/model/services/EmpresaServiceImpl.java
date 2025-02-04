package com.professional.model.services;

import com.professional.model.entities.Empresa;
import com.professional.model.exceptions.ResourceNotFoundException;
import com.professional.model.repositories.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmpresaServiceImpl(EmpresaRepository empresaRepository,
                              PasswordEncoder passwordEncoder) {
        this.empresaRepository = empresaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Empresa> getAllEmpresas() {

        return empresaRepository.findByActivoTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Empresa> getAllEmpresasTodos() {
        return empresaRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Empresa getEmpresaById(Long id) {
        Empresa empresa= empresaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa no encontrada con ID: " + id));

        if (!empresa.getActivo()){
            throw new IllegalStateException("El usuario con ID: " + id + " está deshabilitado.");
        }
        return empresa;

    }

    @Transactional(readOnly = true)
    @Override
    public Empresa findByCorreo(String correo) {
        return empresaRepository.findByCorreo(correo)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa no encontrada con correo: " + correo));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Empresa createEmpresa(Empresa empresa) {
        // Puedes agregar validaciones adicionales aquí si es necesario.
        // Por ejemplo, verificar si el correo ya existe
        if (empresaRepository.existsByCorreo(empresa.getCorreo())){
            throw new IllegalStateException("El correo electrónico ya está en uso.");
        }
        empresa.setPassword(passwordEncoder.encode(empresa.getPassword()));
        empresa.setActivo(true);
        return empresaRepository.save(empresa);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Empresa updateEmpresa(Long id, Empresa empresaDetalles) {
        Empresa existente = empresaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa no encontrada con ID: " + id));

        // Obtener el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Verificar que el usuario autenticado es el propietario de la cuenta
        if (!existente.getCorreo().equals(currentUsername)) {
            throw new AccessDeniedException("No tienes permiso para actualizar esta cuenta");
        }

        // Actualizar campos obligatorios
        existente.setNombres(empresaDetalles.getNombres());
        existente.setApellidos(empresaDetalles.getApellidos());
        existente.setCelular(empresaDetalles.getCelular());
        existente.setCorreo(empresaDetalles.getCorreo());
        existente.setCartaPresentacion(empresaDetalles.getCartaPresentacion());
        existente.setNombreEmpresa(empresaDetalles.getNombreEmpresa());
        existente.setAreaTrabajo(empresaDetalles.getAreaTrabajo());

        // Actualizar campos opcionales solo si no son null o vacíos
        if (empresaDetalles.getMision() != null && !empresaDetalles.getMision().isEmpty()) {
            existente.setMision(empresaDetalles.getMision());
        }
        if (empresaDetalles.getFotoRepresentante() != null && !empresaDetalles.getFotoRepresentante().isEmpty()) {
            existente.setFotoRepresentante(empresaDetalles.getFotoRepresentante());
        }

        if (empresaDetalles.getVision() != null && !empresaDetalles.getVision().isEmpty()) {
            existente.setVision(empresaDetalles.getVision());
        }

        if (empresaDetalles.getDniAnverso() != null && !empresaDetalles.getDniAnverso().isEmpty()) {
            existente.setDniAnverso(empresaDetalles.getDniAnverso());
        }

        if (empresaDetalles.getDniReverso() != null && !empresaDetalles.getDniReverso().isEmpty()) {
            existente.setDniReverso(empresaDetalles.getDniReverso());
        }

        if (empresaDetalles.getFechaPagoInicio() != null) {
            existente.setFechaPagoInicio(empresaDetalles.getFechaPagoInicio());
        }

        if (empresaDetalles.getFechaPagoFin() != null) {
            existente.setFechaPagoFin(empresaDetalles.getFechaPagoFin());
        }

        if (empresaDetalles.getActivo() != null) {
            existente.setActivo(empresaDetalles.getActivo());
        }

        if (empresaDetalles.getRegistroDeEmpresa() != null && !empresaDetalles.getRegistroDeEmpresa().isEmpty()) {
            existente.setRegistroDeEmpresa(empresaDetalles.getRegistroDeEmpresa());
        }

        if (empresaDetalles.getLicenciaComercial() != null && !empresaDetalles.getLicenciaComercial().isEmpty()) {
            existente.setLicenciaComercial(empresaDetalles.getLicenciaComercial());
        }

        // Si se actualiza la contraseña, encriptarla
        if (empresaDetalles.getPassword() != null && !empresaDetalles.getPassword().isEmpty()) {
            existente.setPassword(passwordEncoder.encode(empresaDetalles.getPassword()));
        }

        // Manejar relaciones si es necesario.
        // Por ejemplo, actualizar la lista de trabajos de la empresa.

        return empresaRepository.save(existente);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteEmpresa(Long id) {
        Empresa existente = empresaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa no encontrada con ID: " + id));
        existente.setActivo(false);
        empresaRepository.save(existente);
    }

}

