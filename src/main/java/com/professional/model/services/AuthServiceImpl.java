package com.professional.model.services;

import com.professional.model.auth.GeneradorJwt;
import com.professional.model.dto.RegistroDTO;
import com.professional.model.dto.LoginDTO;
import com.professional.model.entities.Cliente;
import com.professional.model.entities.Empresa;
import com.professional.model.entities.Independiente;
import com.professional.model.entities.VerificationToken;
import com.professional.model.repositories.ClienteRepository;
import com.professional.model.repositories.EmpresaRepository;
import com.professional.model.repositories.IndependienteRepository;
import com.professional.model.repositories.VerificationTokenRepository;
import com.professional.model.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.time.LocalDateTime;
// todo: al registrar un nuevo usuario se debe controlar las fechas de pago USD de inicio y fin
@Service
public class AuthServiceImpl implements AuthService {

    private final ClienteRepository clienteRepository;
    private final EmpresaRepository empresaRepository;
    private final IndependienteRepository independienteRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(ClienteRepository clienteRepository,
                           EmpresaRepository empresaRepository,
                           IndependienteRepository independienteRepository,
                           VerificationTokenRepository verificationTokenRepository,
                           EmailService emailService,
                           PasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;
        this.empresaRepository = empresaRepository;
        this.independienteRepository = independienteRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void registrarUsuario(RegistroDTO registroDTO) {
        String tipoUsuario = registroDTO.getTipoUsuario();

        switch (tipoUsuario.toLowerCase()) {
            case "cliente" -> registrarCliente(registroDTO);
            case "empresa" -> registrarEmpresa(registroDTO);
            case "independiente" -> registrarIndependiente(registroDTO);
            default -> throw new IllegalArgumentException("Tipo de usuario no válido");
        }
    }

    private void registrarCliente(RegistroDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNombres(dto.getNombres());
        cliente.setApellidos(dto.getApellidos());
        cliente.setCelular(dto.getCelular());
        cliente.setCorreo(dto.getCorreo());
        cliente.setPassword(passwordEncoder.encode(dto.getPassword()));
        cliente.setActivo(false); // Se activará tras confirmar el correo

        Cliente guardado = clienteRepository.save(cliente);

        enviarCorreoConfirmacion(guardado.getCorreo(), generarToken(guardado.getCorreo(),LocalDateTime.now().plusHours(72),guardado.getTipoUsuario()));
    }

    private void registrarEmpresa(RegistroDTO dto) {
        Empresa empresa = new Empresa();
        empresa.setNombres(dto.getNombres());
        empresa.setApellidos(dto.getApellidos());
        empresa.setCelular(dto.getCelular());
        empresa.setCorreo(dto.getCorreo());
        empresa.setPassword(passwordEncoder.encode(dto.getPassword()));
        empresa.setNombreEmpresa(dto.getNombreEmpresa());
        empresa.setRegistroDeEmpresa(dto.getRegistroDeEmpresa());
        empresa.setLicenciaComercial(dto.getLicenciaComercial());
        empresa.setAreaTrabajo(dto.getAreaTrabajo());
        empresa.setActivo(false); // Se activará tras confirmar el correo

        Empresa guardada = empresaRepository.save(empresa);

        enviarCorreoConfirmacion(guardada.getCorreo(), generarToken(guardada.getCorreo(),LocalDateTime.now().plusHours(72),guardada.getTipoUsuario()));
    }

    private void registrarIndependiente(RegistroDTO dto) {
        Independiente independiente = new Independiente();
        independiente.setNombres(dto.getNombres());
        independiente.setApellidos(dto.getApellidos());
        independiente.setCelular(dto.getCelular());
        independiente.setCorreo(dto.getCorreo());
        independiente.setPassword(passwordEncoder.encode(dto.getPassword()));
        independiente.setProfesion(dto.getProfesion());
        independiente.setFotoTitulo(dto.getFotoTitulo());
        independiente.setActivo(false); // Se activará tras confirmar el correo

        Independiente guardado = independienteRepository.save(independiente);

        enviarCorreoConfirmacion(guardado.getCorreo(), generarToken(guardado.getCorreo(),LocalDateTime.now().plusHours(72),guardado.getTipoUsuario()));
    }

    private String generarToken(String correo, LocalDateTime fechaExpiracion, String tipoUsuario) {
        String token = GeneradorJwt.generarToken(correo,fechaExpiracion,tipoUsuario);
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setCorreo(correo);
        verificationToken.setFechaExpiracion(fechaExpiracion);
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    private void enviarCorreoConfirmacion(String correo, String token) {
        String enlaceConfirmacion = "http://localhost:8080/auth/confirmar-cuenta?token=" + token;
        String asunto = "Confirmación de Cuenta";
        String mensaje = "Por favor, haz clic en el siguiente enlace para confirmar tu cuenta: " + enlaceConfirmacion;

        emailService.enviarEmail(correo, asunto, mensaje);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void confirmarCuenta(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Token de confirmación no válido"));

        if (verificationToken.getFechaExpiracion().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("El token ha expirado");
        }

        String correo = verificationToken.getCorreo();

        // Activar el usuario
        if (activarUsuario(correo)) {
            // Eliminar el token después de la confirmación
            verificationTokenRepository.delete(verificationToken);
        } else {
            throw new ResourceNotFoundException("Usuario no encontrado para activar");
        }
    }

    private boolean activarUsuario(String correo) {
        boolean activado = false;

        Cliente cliente = clienteRepository.findByCorreo(correo)
                .orElseThrow(() -> new ResourceNotFoundException("Ese correo ya esta registrado."));
        if (cliente != null) {
            cliente.setActivo(true);
            clienteRepository.save(cliente);
            activado = true;
        }

        Empresa empresa = empresaRepository.findByCorreo(correo)
                .orElseThrow(() -> new ResourceNotFoundException("Ese correo ya esta registrado."));
        if (empresa != null) {
            empresa.setActivo(true);
            empresaRepository.save(empresa);
            activado = true;
        }

        Independiente independiente = independienteRepository.findByCorreo(correo)
                .orElseThrow(() -> new ResourceNotFoundException("Ese correo ya esta registrado."));
        if (independiente != null) {
            independiente.setActivo(true);
            independienteRepository.save(independiente);
            activado = true;
        }

        return activado;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public String autenticarUsuario(LoginDTO loginDTO) {
        Optional<VerificationToken> verificationToken;
        String correo = loginDTO.getCorreo();
        String password = loginDTO.getPassword();

        // Buscar el usuario en las tres tablas
        Cliente cliente = clienteRepository.findByCorreo(correo)
                .orElseThrow(() -> new ResourceNotFoundException("Ese correo ya esta registrado."));
        Empresa empresa = empresaRepository.findByCorreo(correo)
                .orElseThrow(() -> new ResourceNotFoundException("Ese correo ya esta registrado."));
        Independiente independiente = independienteRepository.findByCorreo(correo)
                .orElseThrow(() -> new ResourceNotFoundException("Ese correo ya esta registrado."));

        if (cliente != null && cliente.getActivo()) {
            if (passwordEncoder.matches(password, cliente.getPassword())) {
                verificationToken = verificationTokenRepository.findByCorreo(cliente.getCorreo());
                verificationToken.ifPresent(verificationTokenRepository::delete);
                return generarToken(cliente.getCorreo(),LocalDateTime.now().plusHours(72),cliente.getTipoUsuario());
            }
        } else if (empresa != null && empresa.getActivo()) {
            if (passwordEncoder.matches(password, empresa.getPassword())) {
                verificationToken = verificationTokenRepository.findByCorreo(empresa.getCorreo());
                verificationToken.ifPresent(verificationTokenRepository::delete);
                return generarToken(empresa.getCorreo(),LocalDateTime.now().plusHours(72),empresa.getTipoUsuario());
            }
        } else if (independiente != null && independiente.getActivo()) {
            if (passwordEncoder.matches(password, independiente.getPassword())) {
                verificationToken = verificationTokenRepository.findByCorreo(independiente.getCorreo());
                verificationToken.ifPresent(verificationTokenRepository::delete);
                return generarToken(independiente.getCorreo(),LocalDateTime.now().plusHours(72),independiente.getTipoUsuario());
            }
        }

        throw new IllegalArgumentException("Credenciales inválidas o cuenta no confirmada");
    }
}

