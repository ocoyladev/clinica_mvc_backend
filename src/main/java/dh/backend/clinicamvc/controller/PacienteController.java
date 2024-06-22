package dh.backend.clinicamvc.controller;

import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.entity.Paciente;
import dh.backend.clinicamvc.service.IPacienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/paciente")
public class PacienteController {
    private static Logger LOGGER = LoggerFactory.getLogger(PacienteController.class);
    public IPacienteService pacienteService;

    public PacienteController(IPacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    public ResponseEntity<Paciente> registrarPaciente(@RequestBody Paciente paciente){
        Paciente pacienteARetornar = pacienteService.registrarPaciente(paciente);
        if(pacienteARetornar == null){
            LOGGER.info("Paciente no se agregó");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            LOGGER.info("Paciente agregado: "+ paciente);
            return ResponseEntity.status(HttpStatus.CREATED).body(pacienteARetornar);
        }
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> buscarTodos(){

        LOGGER.info("Se listaron los pacientes");
        return ResponseEntity.ok(pacienteService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPacientePorID (@PathVariable Integer id){
        Optional<Paciente> paciente = pacienteService.buscarPorId(id);
        if(paciente.isPresent()){
            Paciente pacienteARetornar = paciente.get();
            LOGGER.info("Paciente encontrado: "+paciente.get());
            return ResponseEntity.ok(pacienteARetornar);
        } else {
            LOGGER.info("Odontólogo no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping
    public ResponseEntity<String> actualizarPaciente(@RequestBody Paciente paciente){
        Optional<Paciente> pacienteOptional = pacienteService.buscarPorId(paciente.getId());
        if(pacienteOptional.isPresent()){
            pacienteService.actualizarPaciente(paciente);
            LOGGER.info("Paciente modificado");
            return ResponseEntity.ok("{\"message\": \"paciente modificado\"}");
        }else {
            LOGGER.info("Paciente no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> borrarPaciente(@PathVariable Integer id) {
        Optional<Paciente> pacienteOptional = pacienteService.buscarPorId(id);
        if (pacienteOptional.isPresent()) {
            pacienteService.eliminarPaciente(id);
            LOGGER.info("Paciente eliminado");
            return ResponseEntity.ok("{\"message\": \"paciente eliminado\"}");
        } else {
            LOGGER.info("Paciente no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
