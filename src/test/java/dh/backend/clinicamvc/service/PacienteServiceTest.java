package dh.backend.clinicamvc.service;

import dh.backend.clinicamvc.entity.Domicilio;
import dh.backend.clinicamvc.entity.Paciente;
import dh.backend.clinicamvc.service.impl.PacienteService;
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
class PacienteServiceTest {
    private static Logger LOGGER = LoggerFactory.getLogger(PacienteServiceTest.class);

    @Autowired
    private PacienteService pacienteService;
    private Paciente paciente;

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
    }


    @Test
    @DisplayName("Testear que un paciente fue guardado")
    void testPacienteGuardado(){
        Paciente paciente1 = pacienteService.registrarPaciente(paciente);
        LOGGER.info("Paciente agregado: "+ paciente1);
        assertNotNull(paciente1);
    }

    @Test
    @DisplayName("Testear búsqueda paciente por id")
    void testPacientePorId(){
        Integer id = 1;
        Optional<Paciente> pacienteEncontrado = pacienteService.buscarPorId(id);
        Paciente paciente1 = pacienteEncontrado.get();

        assertEquals(id, paciente1.getId());
    }

    @Test
    @DisplayName("Testear búsqueda todos los pacientes")
    void testBusquedaTodos(){

        List<Paciente> pacientes = pacienteService.buscarTodos();

        assertTrue(pacientes.size()!=0);

    }

}