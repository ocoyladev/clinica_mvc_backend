package dh.backend.clinicamvc.service;

import dh.backend.clinicamvc.Dto.request.TurnoRequestDto;
import dh.backend.clinicamvc.Dto.response.TurnoResponseDto;
import dh.backend.clinicamvc.entity.Domicilio;
import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.entity.Paciente;
import dh.backend.clinicamvc.entity.Turno;
import dh.backend.clinicamvc.service.impl.OdontologoService;
import dh.backend.clinicamvc.service.impl.PacienteService;
import dh.backend.clinicamvc.service.impl.TurnoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TurnoServiceTest {
    private static Logger LOGGER = LoggerFactory.getLogger(PacienteServiceTest.class);

    @Autowired
    private PacienteService pacienteService;
    private Paciente paciente = null;
    @Autowired
    private OdontologoService odontologoService;
    private Odontologo odontologo;
    @Autowired
    private TurnoService turnoService;
    private TurnoRequestDto turno;

    @BeforeEach
    void setUp(){
        paciente = new Paciente();
        paciente.setNombre("Juan");
        paciente.setApellido("Ramirez");
        paciente.setDni("48376056");
        paciente.setFechaIngreso(LocalDate.of(2024,02,01));
        Domicilio domicilio = new Domicilio();
        domicilio.setCalle("Venezuela");
        domicilio.setNumero(324);
        domicilio.setLocalidad("Arequipa");
        domicilio.setProvincia("Arequipa");
        paciente.setDomicilio(domicilio);

        odontologo = new Odontologo();
        odontologo.setNroMatricula("234567");
        odontologo.setNombre("Juan");
        odontologo.setApellido("Perez");


    }


    @Test
    @DisplayName("Testear que un turno fue guardado")
    void testTurnoGuardado(){
        Odontologo odontologo1 = odontologoService.registrarOdontologo(odontologo);
        LOGGER.info("Odontólogo agregado: "+ odontologo1);
        Paciente paciente1 = pacienteService.registrarPaciente(paciente);
        LOGGER.info("Paciente agregado: "+ paciente1);

        turno = new TurnoRequestDto();
        turno.setOdontologo_id(odontologo1.getId());
        turno.setPaciente_id(paciente1.getId());
        turno.setFecha("2024-05-23");

        TurnoResponseDto turno1 = turnoService.registrarTurno(turno);

        LOGGER.info("Turno agregado: "+ turno1);
        assertNotNull(turno1);
    }
}
