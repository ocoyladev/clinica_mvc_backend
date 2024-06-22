package dh.backend.clinicamvc.controller;


import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.exception.ResourceNotFoundException;
import dh.backend.clinicamvc.service.IOdontologoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologo")
public class OdontologoController {
    private static Logger LOGGER = LoggerFactory.getLogger(OdontologoController.class);
    public IOdontologoService odontologoService;

    public OdontologoController(IOdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    @PostMapping
    public ResponseEntity<Odontologo> registrarOdontologo(@RequestBody Odontologo odontologo){
        Odontologo odontologoARegistrar = odontologoService.registrarOdontologo(odontologo);
        if (odontologoARegistrar == null){
            LOGGER.info("Odontólogo no se agregó");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            LOGGER.info("Odontólogo agregado: "+ odontologo);
            return ResponseEntity.status(HttpStatus.CREATED).body(odontologoARegistrar);
        }
    }

    @GetMapping
    public ResponseEntity<List<Odontologo>> buscarTodos(){
        LOGGER.info("Se listaron los odontólogos");
        return ResponseEntity.ok(odontologoService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> buscarOdontologoPorID (@PathVariable Integer id){
        Optional<Odontologo> odontologo = odontologoService.buscarPorId(id);
        if (odontologo.isPresent()){
            Odontologo odontologoARetornar = odontologo.get();
            LOGGER.info("Odontólogo encontrado: "+odontologo.get());
            return ResponseEntity.ok(odontologoARetornar);
        } else {
            LOGGER.info("Odontólogo no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping
    public ResponseEntity<String> actualizarOdontologo(@RequestBody Odontologo odontologo){
        Optional<Odontologo> odontologoOptional = odontologoService.buscarPorId(odontologo.getId());
        if(odontologoOptional.isPresent()){
            odontologoService.actualizarOdontologo(odontologo);
            LOGGER.info("Odontólogo modificado");
            return ResponseEntity.ok("{\"message\": \"odontologo modificado\"}");
        }else {
            LOGGER.info("Odontólogo no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> borrarOdontologo(@PathVariable Integer id) throws ResourceNotFoundException {
        odontologoService.eliminarOdontologo(id);
        LOGGER.info("Odontólogo eliminado");
        return ResponseEntity.ok("{\"message\": \"odontologo eliminado\"}");
    }

    @GetMapping("/apellido/{apellido}")
    public ResponseEntity<List<Odontologo>> buscarPorApellido(@PathVariable String apellido){
        List<Odontologo> listaOdontologos =odontologoService.buscarPorApellido(apellido);
        if(listaOdontologos.size()>0){
            LOGGER.info("Se listaron los odontólogos con apellido "+apellido);
            return ResponseEntity.ok(listaOdontologos);
        } else
            LOGGER.info("No se encontraron odontólogos con apellido "+apellido);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Odontologo>> buscarTodos(@PathVariable String nombre){
        LOGGER.info("Se listaron los odontólogos con nombres que contienen: "+nombre);
        return ResponseEntity.ok(odontologoService.buscarPorNombre(nombre));

    }

}
