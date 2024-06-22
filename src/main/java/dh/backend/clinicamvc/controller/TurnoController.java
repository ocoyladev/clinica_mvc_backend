package dh.backend.clinicamvc.controller;

import dh.backend.clinicamvc.Dto.request.TurnoRequestDto;
import dh.backend.clinicamvc.Dto.response.TurnoResponseDto;
import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.entity.Turno;
import dh.backend.clinicamvc.service.ITurnoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turno")
public class TurnoController {
    private static Logger LOGGER = LoggerFactory.getLogger(TurnoController.class);

    private ITurnoService turnoService;

    public TurnoController(ITurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @PostMapping
    public ResponseEntity<TurnoResponseDto> agregarTurno(@RequestBody TurnoRequestDto turno){
        TurnoResponseDto turnoADevolver = turnoService.registrarTurno(turno);
        if(turnoADevolver==null){
            LOGGER.info("Turno no se creó");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            LOGGER.info("Turno creado");
            return ResponseEntity.status(HttpStatus.CREATED).body(turnoADevolver);
        }
    }
    @GetMapping
    public ResponseEntity<List<TurnoResponseDto>> buscarTodosTurnos(){
        LOGGER.info("Se listaron los turnos");
        return ResponseEntity.ok(turnoService.buscarTodos());
    }
    @GetMapping("/{id}")
    public ResponseEntity<TurnoResponseDto> buscarPorId(@PathVariable Integer id){
        LOGGER.info("Se listaron los turnos");
        return ResponseEntity.ok(turnoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> modificarTurno(@PathVariable Integer id, @RequestBody TurnoRequestDto turno){
        turnoService.actualizarTurno(id, turno);
        LOGGER.info("Se actualizó el turno");
        return ResponseEntity.ok("Turno modificado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> borrarTurno(@PathVariable Integer id){
        turnoService.eliminarTurno(id);
        LOGGER.info("Se eliminó el turno");
        return ResponseEntity.ok("Turno eliminado");
    }


    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @GetMapping("/fechas")
    public ResponseEntity<List<TurnoResponseDto>> buscarEntreFechas(@RequestParam String inicio, @RequestParam String fin){
        LocalDate fechaInicio = LocalDate.parse(inicio, formatter);
        LocalDate fechaFinal = LocalDate.parse(fin, formatter);
        LOGGER.info("Se hizo la búsqueda de turnos entre " + inicio + "y" + fin);
        return ResponseEntity.ok(turnoService.buscarTurnoEntreFechas(fechaInicio, fechaFinal));
    }

}
