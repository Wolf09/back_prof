package com.professional.controller;

import com.professional.model.dto.Error;
import com.professional.model.entities.Independiente;
import com.professional.model.entities.TrabajoIndependiente;
import com.professional.model.services.IndependienteService;
import com.professional.model.services.TrabajoIndependienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/trabajo-independiente")
public class TrabajoIndependienteController {
    private final TrabajoIndependienteService trabajoIndependienteService;
    private final IndependienteService independienteService;

    @Autowired
    public TrabajoIndependienteController(TrabajoIndependienteService trabajoIndependienteService,
                                          IndependienteService independienteService) {
        this.trabajoIndependienteService = trabajoIndependienteService;
        this.independienteService = independienteService;
    }

    @Transactional
    @PostMapping("/crear/{id}")
    public ResponseEntity<?> crearTrabajoIndependiente(@PathVariable Long id,@Valid @RequestBody TrabajoIndependiente trabajoIndependiente,
                                                        BindingResult result){
        if (result.hasErrors()){
            List<Error> errores = new ArrayList<>();
            result.getFieldErrors().forEach(err ->{
                errores.add(new Error(err.getDefaultMessage()));
            });
            return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
        }

        TrabajoIndependiente creado= trabajoIndependienteService.createTrabajoIndependiente(id,trabajoIndependiente);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @Transactional(readOnly = true)
    @GetMapping("/listar/independientes")
    public ResponseEntity<List<TrabajoIndependiente>> listarTrabajosIndependientes(){
        List<TrabajoIndependiente> lista= trabajoIndependienteService.getAllTrabajosIndependientes()
                .stream()
                .filter(TrabajoIndependiente::getActivo)
                .toList();
        return new ResponseEntity<>(lista,HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    //toda la logica esta en el servico y las interfaces que implementa, siempre va a validar que activo=true antes de devolver la lista
    public ResponseEntity<TrabajoIndependiente> obtenerTrabajoIndependientePorId(@PathVariable Long id){
        TrabajoIndependiente trabajoIndependiente= trabajoIndependienteService.getTrabajoIndependienteById(id);
        return new ResponseEntity<>(trabajoIndependiente,HttpStatus.OK);
    }

    @Transactional
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarTrabajoIndependiente(@PathVariable Long id,
                                                            @Valid @RequestBody TrabajoIndependiente trabajoIndependiente,
                                                            BindingResult result){
        if (result.hasErrors()){
            List<Error> errores = new ArrayList<>();
            result.getFieldErrors().forEach(err ->{
                errores.add(new Error(err.getDefaultMessage()));
            });
            return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
        }

        // Si se está actualizando el Independiente, asegurarse de que existe
        if (trabajoIndependiente.getIndependiente() != null) {
            Independiente independiente = independienteService.getIndependienteById(trabajoIndependiente.getIndependiente().getId());
            trabajoIndependiente.setIndependiente(independiente);
        }
        TrabajoIndependiente actualizado= trabajoIndependienteService.updateTrabajoIndependiente(id, trabajoIndependiente);
        return new ResponseEntity<>(actualizado,HttpStatus.OK);

    }

    @Transactional
    @PostMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarTrabajoIndependiente(@PathVariable Long id){
        trabajoIndependienteService.deleteTrabajoIndependiente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional(readOnly = true)
    @GetMapping("/listar/empresas")
    public ResponseEntity<List<TrabajoIndependiente>> listarTrabajosEmpresa() {
        List<TrabajoIndependiente> listaActivos = trabajoIndependienteService.getAllTrabajosIndependientesActivos();
        return new ResponseEntity<>(listaActivos, HttpStatus.OK);
    }

    /**
     * Obtener todos los TrabajosIndependientes de un Independiente específico, incluyendo las calificaciones y los Clientes que las realizaron.
     *
     * @param independiente
     * @return ResponseEntity con la lista de TrabajosIndependientes y sus calificaciones.
     */
    @Transactional(readOnly = true)
    @GetMapping("/independiente/{independienteId}")
    public ResponseEntity<List<TrabajoIndependiente>> obtenerTrabajosPorIndependiente(@RequestBody Independiente independiente) {
        List<TrabajoIndependiente> trabajos = trabajoIndependienteService.getTrabajosIndependientesByIndependiente(independiente);
        return new ResponseEntity<>(trabajos, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/save-crear")
    public ResponseEntity<?> saveTrabajoIndependiente(@Valid @RequestBody TrabajoIndependiente trabajoIndependiente,
                                                       BindingResult result){
        if (result.hasErrors()){
            List<Error> errores = new ArrayList<>();
            result.getFieldErrors().forEach(err ->{
                errores.add(new Error(err.getDefaultMessage()));
            });
            return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
        }
        TrabajoIndependiente creado= trabajoIndependienteService.saveTrabajoIndependiente(trabajoIndependiente);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }


}
