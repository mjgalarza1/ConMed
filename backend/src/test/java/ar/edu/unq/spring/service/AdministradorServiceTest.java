package ar.edu.unq.spring.service;

import ar.edu.unq.spring.modelo.Administrador;
import ar.edu.unq.spring.modelo.Medico;
import ar.edu.unq.spring.service.interfaces.AdministradorService;
import ar.edu.unq.spring.service.interfaces.MedicoService;
import ar.edu.unq.spring.service.interfaces.PacienteService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AdministradorServiceTest {

    @Autowired
    private AdministradorService administradorService;

    @Autowired
    private MedicoService medicoService;

    private Administrador admin1;
    private Administrador admin2;

    @BeforeEach
    public void setUp() {
        admin1 = new Administrador("admin1", "11111111");
        admin2 = new Administrador("admin2", "22222222");
    }

    @Test
    public void testAllAdministradores(){
        administradorService.guardarAdministrador(admin1);
        administradorService.guardarAdministrador(admin2);
        Assertions.assertEquals(2, administradorService.allAdministradores().size());
    }

    @Test
    public void testGuardarAdministrador(){
        Long id = administradorService.guardarAdministrador(admin1).getIdAdmin();
        Assertions.assertEquals(id, administradorService.recuperarAdministrador(id).getIdAdmin());
    }

    @Test
    public void testRecuperarAdministrador(){
        Long id = administradorService.guardarAdministrador(admin1).getIdAdmin();
        String nombre = administradorService.recuperarAdministrador(id).getNombre();
        Assertions.assertEquals("admin1", nombre);
    }

    @Test
    public void testAgregarMedico(){
        Medico medico = new Medico("Juan", "Se", "33333333", "Odontologo", "ABC123");
        Long id = administradorService.agregarMedico(medico).getIdMedico();
        Assertions.assertEquals(id, medicoService.recuperarMedico(id).getIdMedico());
    }

    @Test
    public void testQuitarMedico(){
        Medico medico = new Medico("Juan", "Se", "33333333", "Odontologo", "ABC123");
        Long id = administradorService.agregarMedico(medico).getIdMedico();
        Assertions.assertEquals(1, medicoService.allMedicos().size());
        administradorService.quitarMedico(id);
        Assertions.assertEquals(0, medicoService.allMedicos().size());
    }

    @AfterEach
    public void tearDown() {
        administradorService.clearAll();
        medicoService.clearAll();
    }

}