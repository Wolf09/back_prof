package com.professional.model.services;

import com.professional.model.entities.Cliente;
import com.professional.model.exceptions.ResourceNotFoundException;
import com.professional.model.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ClienteServiceImpl(ClienteRepository clienteRepository,
                              PasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * {@inheritDoc}
     * Retorna solo clientes activos.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Cliente> getAllClientes() {
        return clienteRepository.findByActivo(true);
    }

    /**
     * {@inheritDoc}
     * Retorna todos los clientes, incluyendo inactivos.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Cliente> getAllClientesTodos() {
        return clienteRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Cliente getClienteById(Long id) {
        return clienteRepository.findByIdAndActivo(id,true)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado o dado de Baja con ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente findClienteByCorreo(String correo) {
        return clienteRepository.findByCorreoAndActivo(correo,true)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado o dado de Baja con correo: " + correo));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Cliente createCliente(Cliente cliente) {
        // Validaciones adicionales pueden agregarse aquí
        // Por ejemplo, verificar si el correo ya existe
        if (clienteRepository.existsByCorreo(cliente.getCorreo())) {
            throw new IllegalStateException("El correo electrónico ya está en uso.");
        }
        cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));
        cliente.setActivo(true); // Asegurar que el cliente sea activo al crear
        return clienteRepository.save(cliente);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Cliente updateCliente(Long id, Cliente clienteDetalles) {
        Cliente existente = clienteRepository.findByIdAndActivo(id,true)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado o dado de Baja con ID: " + id));

        // Obtener el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Verificar que el usuario autenticado es el propietario de la cuenta
        if (!existente.getCorreo().equals(currentUsername)) {
            throw new AccessDeniedException("No tienes permiso para actualizar esta cuenta");
        }

        // Actualizar campos existentes
        existente.setNombres(clienteDetalles.getNombres());
        existente.setApellidos(clienteDetalles.getApellidos());
        existente.setCelular(clienteDetalles.getCelular());
        existente.setCorreo(clienteDetalles.getCorreo());
        existente.setActivo(clienteDetalles.getActivo());

        // Si se actualiza la contraseña, encriptarla
        if (clienteDetalles.getPassword() != null && !clienteDetalles.getPassword().isEmpty()) {
            existente.setPassword(passwordEncoder.encode(clienteDetalles.getPassword()));
        }

        return clienteRepository.save(existente);
    }

    /**
     * {@inheritDoc}
     * Realiza una eliminación lógica cambiando 'activo' a false.
     */
    @Override
    @Transactional
    public void deleteCliente(Long id) {
        Cliente existente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id));
        existente.setActivo(false);
        clienteRepository.save(existente);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Cliente getClienteByCorreo(String correo) {
        return clienteRepository.findByCorreoAndActivo(correo,true)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado o dado de Baja con correo: " + correo));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public boolean existsByCorreo(String correo) {
        return clienteRepository.existsByCorreoAndActivo(correo,true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findByNombresContainingIgnoreCase(String nombres) {
        return clienteRepository.findByNombresContainingIgnoreCaseAndActivo(nombres,true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findByActivo(Boolean activo) {
        return clienteRepository.findByActivo(activo);
    }
}
