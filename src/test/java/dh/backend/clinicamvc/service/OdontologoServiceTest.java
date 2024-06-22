package dh.backend.clinicamvc.service;

import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.service.impl.OdontologoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OdontologoServiceTest {

    private static Logger LOGGER = LoggerFactory.getLogger(OdontologoServiceTest.class);
    @Autowired
    private OdontologoService odontologoService;
    private Odontologo odontologo;

    @BeforeEach
    void setUp(){
        odontologo = new Odontologo();
        odontologo.setNroMatricula("234567");
        odontologo.setNombre("Juan");
        odontologo.setApellido("Perez");
    }

    @Test
    @DisplayName("Testear guardar un odontologo")
    void testOdontologoGuardado() {
        Odontologo odontologo1 = odontologoService.registrarOdontologo(odontologo);
        LOGGER.info("Odontólogo agregado: "+ odontologo1);
        assertNotNull(odontologo1);
    }


    @Test
    @DisplayName("Testear búsqueda odontólogo por id")
    void testOdontologoPorId(){
        Integer id = 1;
        Odontologo odontologoBD = odontologoService.registrarOdontologo(odontologo);
        Optional<Odontologo> odontologoEncontrado = odontologoService.buscarPorId(id);
        Odontologo odontologo1 = odontologoEncontrado.get();
        assertEquals(id, odontologo1.getId());
    }

    @Test
    @DisplayName("Testear búsqueda de todos los odontólogos")
    void testOdontologoBuscarTodos() {

        List<Odontologo> odontologos = odontologoService.buscarTodos();
        assertTrue(odontologos.size()!=0);
    }
}

