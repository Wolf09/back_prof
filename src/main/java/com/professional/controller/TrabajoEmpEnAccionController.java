package com.professional.controller;

import com.professional.model.dto.Error;
import com.professional.model.entities.Cliente;
import com.professional.model.enums.EstadoTrabajo;
import com.professional.model.entities.TrabajoEmpEnAccion;
import com.professional.model.entities.TrabajoEmpresa;
import com.professional.model.services.ClienteService;
import com.professional.model.services.EmpresaService;
import com.professional.model.services.TrabajoEmpEnAccionService;
import com.professional.model.services.TrabajoEmpresaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//TODO me falta implementar los Controllers de las siguientes clases: Cliente, CalificacionEmpresas, CalificacionIndependientes, HistorialEmpresas, HistorialIndependientes
/**
 * Controlador para gestionar las operaciones CRUD de la entidad TrabajoEmpEnAccion.
 */
@RestController
@RequestMapping("/trabajo-emp-en-accion")
public class TrabajoEmpEnAccionController {

    private final TrabajoEmpEnAccionService trabajoEmpEnAccionService;
    private final TrabajoEmpresaService trabajoEmpresaService;
    private final ClienteService clienteService;
    private final EmpresaService empresaService;

    @Autowired
    public TrabajoEmpEnAccionController(TrabajoEmpEnAccionService trabajoEmpEnAccionService,
                                        TrabajoEmpresaService trabajoEmpresaService,
                                        ClienteService clienteService,
                                        EmpresaService empresaService) {
        this.trabajoEmpEnAccionService = trabajoEmpEnAccionService;
        this.trabajoEmpresaService = trabajoEmpresaService;
        this.clienteService = clienteService;
        this.empresaService = empresaService;
    }

    /**
     * Crear un nuevo TrabajoEmpEnAccion.
     *
     * @param trabajoEmpEnAccion Datos del TrabajoEmpEnAccion a crear.
     * @param result              Resultado de la validación.
     * @return ResponseEntity con el TrabajoEmpEnAccion creado o errores de validación.
     */
    @Transactional
    @PostMapping("/crear")
    public ResponseEntity<?> crearTrabajoEmpEnAccion(@Valid @RequestBody TrabajoEmpEnAccion trabajoEmpEnAccion, BindingResult result) {
        if (result.hasErrors()) {
            List<Error> errores = new ArrayList<>();
            result.getFieldErrors().forEach(err -> {
                errores.add(new Error(err.getDefaultMessage()));
            });
            return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
        }

        // Asegurar que 'activo' esté establecido en true
        trabajoEmpEnAccion.setActivo(true);

        TrabajoEmpEnAccion creado = trabajoEmpEnAccionService.createTrabajoEmpEnAccion(trabajoEmpEnAccion);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    /**
     * Listar todos los TrabajosEmpEnAccion activos.
     *
     * @return ResponseEntity con la lista de TrabajosEmpEnAccion activos.
     */
    @Transactional(readOnly = true)
    @GetMapping("/listar")
    public ResponseEntity<List<TrabajoEmpEnAccion>> listarTrabajosEmpEnAccionActivos() {
        List<TrabajoEmpEnAccion> listaActivos = trabajoEmpEnAccionService.findByActivo(true);
        return new ResponseEntity<>(listaActivos, HttpStatus.OK);
    }

    /**
     * Listar todos los TrabajosEmpEnAccion, tanto activos como inactivos.
     *
     * @return ResponseEntity con la lista completa de TrabajosEmpEnAccion.
     */
    @Transactional(readOnly = true)
    @GetMapping("/listar-todos")
    public ResponseEntity<List<TrabajoEmpEnAccion>> listarTrabajosEmpEnAccionTodos() {
        List<TrabajoEmpEnAccion> lista = trabajoEmpEnAccionService.getAllTrabajosEmpEnAccion();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    /**
     * Obtener un TrabajoEmpEnAccion por su ID.
     *
     * @param id ID del TrabajoEmpEnAccion.
     * @return ResponseEntity con el TrabajoEmpEnAccion encontrado.
     */
    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public ResponseEntity<TrabajoEmpEnAccion> obtenerTrabajoEmpEnAccionPorId(@PathVariable Long id) {
        TrabajoEmpEnAccion trabajoEmpEnAccion = trabajoEmpEnAccionService.getTrabajoEmpEnAccionById(id);
        return new ResponseEntity<>(trabajoEmpEnAccion, HttpStatus.OK);
    }

    /**
     * Actualizar un TrabajoEmpEnAccion existente.
     *
     * @param id                     ID del TrabajoEmpEnAccion a actualizar.
     * @param trabajoEmpEnAccionDetalles Datos actualizados del TrabajoEmpEnAccion.
     * @param result                 Resultado de la validación.
     * @return ResponseEntity con el TrabajoEmpEnAccion actualizado o errores de validación.
     */
    @Transactional
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarTrabajoEmpEnAccion(@PathVariable Long id,
                                                          @Valid @RequestBody TrabajoEmpEnAccion trabajoEmpEnAccionDetalles,
                                                          BindingResult result) {
        if (result.hasErrors()) {
            List<Error> errores = new ArrayList<>();
            result.getFieldErrors().forEach(err -> {
                errores.add(new Error(err.getDefaultMessage()));
            });
            return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
        }

        TrabajoEmpEnAccion actualizado = trabajoEmpEnAccionService.updateTrabajoEmpEnAccion(id, trabajoEmpEnAccionDetalles);
        return new ResponseEntity<>(actualizado, HttpStatus.OK);
    }

    /**
     * Eliminar lógicamente un TrabajoEmpEnAccion por su ID.
     *
     * @param id ID del TrabajoEmpEnAccion a eliminar.
     * @return ResponseEntity con el estado de la operación.
     */
    @Transactional
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarTrabajoEmpEnAccion(@PathVariable Long id) {
        trabajoEmpEnAccionService.deleteTrabajoEmpEnAccion(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Buscar TrabajosEmpEnAccion por estado de trabajo.
     *
     * @param estado Estado del trabajo.
     * @return ResponseEntity con la lista de TrabajosEmpEnAccion que coinciden.
     */
    @Transactional(readOnly = true)
    @GetMapping("/buscar-por-estado")
    public ResponseEntity<List<TrabajoEmpEnAccion>> buscarPorEstadoTrabajo(@RequestParam EstadoTrabajo estado) {
        List<TrabajoEmpEnAccion> trabajos = trabajoEmpEnAccionService.findByEstadoTrabajo(estado);
        return new ResponseEntity<>(trabajos, HttpStatus.OK);
    }

    /**
     * Buscar TrabajosEmpEnAccion por TrabajoEmpresa.
     *
     * @param trabajoEmpresaId ID del TrabajoEmpresa.
     * @return ResponseEntity con la lista de TrabajosEmpEnAccion asociados.
     */
    @Transactional(readOnly = true)
    @GetMapping("/trabajo-empresa/{trabajoEmpresaId}")
    public ResponseEntity<List<TrabajoEmpEnAccion>> buscarPorTrabajoEmpresa(@PathVariable Long trabajoEmpresaId) {
        TrabajoEmpresa trabajoEmpresa = trabajoEmpresaService.getTrabajoEmpresaById(trabajoEmpresaId);
        List<TrabajoEmpEnAccion> trabajos = trabajoEmpEnAccionService.getTrabajosEmpEnAccionByTrabajoEmpresa(trabajoEmpresa);

        // Filtrar solo los activos
        List<TrabajoEmpEnAccion> trabajosActivos = trabajos.stream()
                .filter(TrabajoEmpEnAccion::getActivo)
                .toList();

        return new ResponseEntity<>(trabajosActivos, HttpStatus.OK);
    }

    /**
     * Buscar TrabajosEmpEnAccion por Cliente.
     *
     * @param clienteId ID del Cliente.
     * @return ResponseEntity con la lista de TrabajosEmpEnAccion asociados.
     */
    @Transactional(readOnly = true)
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<TrabajoEmpEnAccion>> buscarPorCliente(@PathVariable Long clienteId) {
        Cliente cliente = clienteService.getClienteById(clienteId);
        List<TrabajoEmpEnAccion> trabajos = trabajoEmpEnAccionService.getTrabajosEmpEnAccionByCliente(cliente);

        // Filtrar solo los activos
        List<TrabajoEmpEnAccion> trabajosActivos = trabajos.stream()
                .filter(TrabajoEmpEnAccion::getActivo)
                .toList();

        return new ResponseEntity<>(trabajosActivos, HttpStatus.OK);
    }

    /**
     * Buscar todos los TrabajosEmpEnAccion, independientemente de su estado activo.
     *
     * @return ResponseEntity con la lista completa de TrabajosEmpEnAccion.
     */
    @Transactional(readOnly = true)
    @GetMapping("/buscar-todos")
    public ResponseEntity<List<TrabajoEmpEnAccion>> buscarTodosTrabajosEmpEnAccion() {
        List<TrabajoEmpEnAccion> trabajos = trabajoEmpEnAccionService.getAllTrabajosEmpEnAccion();
        return new ResponseEntity<>(trabajos, HttpStatus.OK);
    }
}

