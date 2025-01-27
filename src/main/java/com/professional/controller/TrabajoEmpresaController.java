package com.professional.controller;

import com.professional.model.dto.Error;
import com.professional.model.entities.Cliente;
import com.professional.model.entities.Empresa;
import com.professional.model.entities.TrabajoEmpresa;
import com.professional.model.services.ClienteService;
import com.professional.model.services.EmpresaService;
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

/**
 * Controlador para gestionar las operaciones CRUD de la entidad TrabajoEmpresa.
 */
@RestController
@RequestMapping("/trabajo-empresa")
public class TrabajoEmpresaController {

    private final TrabajoEmpresaService trabajoEmpresaService;
    private final EmpresaService empresaService;
    private final ClienteService clienteService;

    @Autowired
    public TrabajoEmpresaController(TrabajoEmpresaService trabajoEmpresaService, EmpresaService empresaService, ClienteService clienteService) {
        this.trabajoEmpresaService = trabajoEmpresaService;
        this.empresaService = empresaService;
        this.clienteService = clienteService;
    }


    /**
     * Crear un nuevo TrabajoEmpresa.
     *
     * @param trabajoEmpresa Datos del TrabajoEmpresa a crear.
     * @param result         Resultado de la validación.
     * @return ResponseEntity con el TrabajoEmpresa creado o errores de validación.
     */
    @Transactional
    @PostMapping("/crear")
    public ResponseEntity<?> crearTrabajoEmpresa(@Valid @RequestBody TrabajoEmpresa trabajoEmpresa, BindingResult result) {
        if (result.hasErrors()) {
            List<Error> errores = new ArrayList<>();
            result.getFieldErrors().forEach(err -> {
                errores.add(new Error(err.getDefaultMessage()));
            });
            return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
        }
        // Asegurar que 'activo' esté establecido en true
        trabajoEmpresa.setActivo(true);
        TrabajoEmpresa creado = trabajoEmpresaService.createTrabajoEmpresa(trabajoEmpresa);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    /**
     * Listar todos los TrabajosEmpresa.
     *
     * @return ResponseEntity con la lista de TrabajosEmpresa.
     */
    @Transactional(readOnly = true)
    @GetMapping("/listar")
    public ResponseEntity<List<TrabajoEmpresa>> listarTrabajosEmpresa() {
        List<TrabajoEmpresa> listaActivos = trabajoEmpresaService.getAllTrabajosEmpresaActivos();
        return new ResponseEntity<>(listaActivos, HttpStatus.OK);
    }


    /**
     * Listar todos los TrabajosEmpresa, tanto activos como inactivos.
     *
     * @return ResponseEntity con la lista completa de TrabajosEmpresa.
     */
    @Transactional(readOnly = true)
    @GetMapping("/listar-todos")
    public ResponseEntity<List<TrabajoEmpresa>> listarTrabajosEmpresaTodos() {
        List<TrabajoEmpresa> lista = trabajoEmpresaService.getAllTrabajosEmpresa();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }
    /**
     * Obtener un TrabajoEmpresa por su ID.
     *
     * @param id ID del TrabajoEmpresa.
     * @return ResponseEntity con el TrabajoEmpresa encontrado.
     */
    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public ResponseEntity<TrabajoEmpresa> obtenerTrabajoEmpresaPorId(@PathVariable Long id) {
        TrabajoEmpresa trabajoEmpresa = trabajoEmpresaService.getTrabajoEmpresaById(id);
        return new ResponseEntity<>(trabajoEmpresa, HttpStatus.OK);
    }

    /**
     * Actualizar un TrabajoEmpresa existente.
     *
     * @param id                  ID del TrabajoEmpresa a actualizar.
     * @param trabajoEmpresaDetalles Datos actualizados del TrabajoEmpresa.
     * @param result              Resultado de la validación.
     * @return ResponseEntity con el TrabajoEmpresa actualizado o errores de validación.
     */
    @Transactional
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarTrabajoEmpresa(@PathVariable Long id,
                                                      @Valid @RequestBody TrabajoEmpresa trabajoEmpresaDetalles,
                                                      BindingResult result) {
        if (result.hasErrors()) {
            List<Error> errores = new ArrayList<>();
            result.getFieldErrors().forEach(err -> {
                errores.add(new Error(err.getDefaultMessage()));
            });
            return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
        }

        TrabajoEmpresa actualizado = trabajoEmpresaService.updateTrabajoEmpresa(id, trabajoEmpresaDetalles);
        return new ResponseEntity<>(actualizado, HttpStatus.OK);
    }

    /**
     * Eliminar lógicamente un TrabajoEmpresa por su ID.
     *
     * @param id ID del TrabajoEmpresa a eliminar.
     * @return ResponseEntity con el estado de la operación.
     */
    @Transactional
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarTrabajoEmpresa(@PathVariable Long id) {
        trabajoEmpresaService.deleteTrabajoEmpresa(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Buscar TrabajosEmpresa por nombre de trabajo.
     *
     * @param trabajo Nombre del trabajo.
     * @return ResponseEntity con la lista de TrabajosEmpresa que coinciden.
     */


    /**
     * Buscar TrabajosEmpresa por ID de Empresa.
     *
     * @param empresaId ID de la Empresa.
     * @return ResponseEntity con la lista de TrabajosEmpresa asociados.
     */
    @Transactional(readOnly = true)
    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<TrabajoEmpresa>> buscarPorEmpresa(@PathVariable Long empresaId) {
        // Obtener la Empresa por ID
        Empresa empresa = empresaService.getEmpresaById(empresaId);

        // Obtener los TrabajosEmpresa asociados a la Empresa
        List<TrabajoEmpresa> trabajos = trabajoEmpresaService.getTrabajosEmpresaByEmpresa(empresa);

        // Filtrar solo los activos
        List<TrabajoEmpresa> trabajosActivos = trabajos.stream()
                .filter(TrabajoEmpresa::getActivo)
                .toList();

        return new ResponseEntity<>(trabajosActivos, HttpStatus.OK);
    }

    /**
     * Buscar TrabajosEmpresa por ID de Cliente.
     *
     * @param clienteId ID del Cliente.
     * @return ResponseEntity con la lista de TrabajosEmpresa asociados.
     */
    @Transactional(readOnly = true)
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<?> buscarPorCliente(@PathVariable Long clienteId) {
        // Obtener el Cliente por ID
        Cliente cliente = clienteService.getClienteById(clienteId);

        // Obtener los TrabajosEmpresa asociados al Cliente
        List<TrabajoEmpresa> trabajos = trabajoEmpresaService.getTrabajosEmpresaByCliente(cliente);


        // Filtrar solo los activos
        List<TrabajoEmpresa> trabajosActivos = trabajos.stream()
                .filter(TrabajoEmpresa::getActivo)
                .toList();

        return new ResponseEntity<>(trabajosActivos, HttpStatus.OK);
    }

}
