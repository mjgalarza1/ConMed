package ar.edu.unq.spring.service;

import ar.edu.unq.spring.modelo.Paciente;
import ar.edu.unq.spring.service.interfaces.PacienteService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PacienteServiceTest {

    @Autowired
    private PacienteService pacienteService;

    @Test
    void testCrearPaciente() {
        Paciente paciente = new Paciente();
        paciente.setNombre("Gregory House");
        paciente.setDni("12345678");

        Long id = pacienteService.guardarPaciente(paciente).getIdPaciente();
        Paciente pacienteBd = pacienteService.recuperarPacientePorId(paciente.getIdPaciente());

        Assertions.assertNotNull(pacienteBd.getIdPaciente());
        Assertions.assertEquals("Gregory House", pacienteBd.getNombre());
    }

    @Test
    void testRecuperarPacientePorId() {
        Paciente paciente = new Paciente();
        paciente.setDni("12345678");
        paciente.setNombre("Lisa Cuddy");
        pacienteService.guardarPaciente(paciente);
        Paciente pacienteBD = pacienteService.recuperarPacientePorId(paciente.getIdPaciente());

        Assertions.assertEquals("Lisa Cuddy", pacienteBD.getNombre());
    }

    @Test
    void testRecuperarTodosLosPacientes() {
        Paciente pacienteA = new Paciente();
        pacienteA.setDni("12345678");
        pacienteA.setNombre("Allison Cameron");
        Paciente pacienteB = new Paciente();
        pacienteB.setDni("12345679");
        pacienteB.setNombre("Robert Chase");

        pacienteService.guardarPaciente(pacienteA);
        pacienteService.guardarPaciente(pacienteB);

        List<Paciente> pacientes = pacienteService.allPacientes();

        Assertions.assertTrue(pacientes.size() >= 2);
    }

    @Test
    void testActualizarPaciente() {
        Paciente paciente = new Paciente();
        paciente.setNombre("James Wilson");
        paciente.setDni("12345678");
        pacienteService.guardarPaciente(paciente);

        paciente.setNombre("Paul W.");
        pacienteService.actualizarPaciente(paciente.getIdPaciente(), paciente);

        Paciente actualizado = pacienteService.recuperarPacientePorId(paciente.getIdPaciente());
        Assertions.assertEquals("Paul W.", actualizado.getNombre());
    }

    @Test
    void testEliminarPaciente() {
        Paciente paciente = new Paciente();
        paciente.setNombre("Thirteen");
        paciente.setDni("12345678");
        pacienteService.guardarPaciente(paciente);

        pacienteService.eliminarPaciente(paciente.getIdPaciente());

        Assertions.assertThrows(RuntimeException.class, () -> {
            pacienteService.recuperarPacientePorId(paciente.getIdPaciente());
        });
    }

    @AfterEach
    public void tearDown() {
        pacienteService.clearAll();
    }

}