package com.professional.controller;

import com.professional.model.dto.Error;
import com.professional.model.entities.TrabajoIndependiente;
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
@RequestMapping("/trabajo-independiente")
public class TrabajoIndependienteController {
    private final TrabajoIndependienteService trabajoIndependienteService;

    @Autowired
    public TrabajoIndependienteController(TrabajoIndependienteService trabajoIndependienteService) {
        this.trabajoIndependienteService = trabajoIndependienteService;
    }

    @Transactional
    @PostMapping("/crear")
    public ResponseEntity<?> crearTrabajoIndependiente(@Valid @RequestBody TrabajoIndependiente trabajoIndependiente,
                                                        BindingResult result){
        if (result.hasErrors()){
            List<Error> errores = new ArrayList<>();
            result.getFieldErrors().forEach(err ->{
                errores.add(new Error(err.getDefaultMessage()));
            });
            return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
        }

        trabajoIndependiente.setActivo(true);
        TrabajoIndependiente creado= trabajoIndependienteService.createTrabajoIndependiente(trabajoIndependiente);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @Transactional(readOnly = true)
    @GetMapping("/listar")
    public ResponseEntity<List<TrabajoIndependiente>> listarTrabajosIndependientes(){
        List<TrabajoIndependiente> lista= trabajoIndependienteService.getAllTrabajosIndependientes()
                .stream()
                .filter(TrabajoIndependiente::getActivo)
                .toList();
        return new ResponseEntity<>(lista,HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    @GetMapping("/{id}")
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
        TrabajoIndependiente actualizado= trabajoIndependienteService.updateTrabajoIndependiente(id, trabajoIndependiente);
        return new ResponseEntity<>(actualizado,HttpStatus.OK);

    }

    @Transactional
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarTrabajoIndependiente(@PathVariable Long id){
        trabajoIndependienteService.deleteTrabajoIndependiente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional(readOnly = true)
    @GetMapping("/listar")
    public ResponseEntity<List<TrabajoIndependiente>> listarTrabajosEmpresa() {
        List<TrabajoIndependiente> listaActivos = trabajoIndependienteService.getAllTrabajosIndependientesActivos();
        return new ResponseEntity<>(listaActivos, HttpStatus.OK);
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
