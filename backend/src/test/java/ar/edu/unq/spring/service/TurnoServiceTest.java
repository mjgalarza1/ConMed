package ar.edu.unq.spring.service;

import ar.edu.unq.spring.modelo.Medico;
import ar.edu.unq.spring.modelo.Paciente;
import ar.edu.unq.spring.modelo.Turno;
import ar.edu.unq.spring.persistence.MedicoDAO;
import ar.edu.unq.spring.persistence.PacienteDAO;
import ar.edu.unq.spring.persistence.TurnoDAO;
import ar.edu.unq.spring.service.impl.MedicoServiceImpl;
import ar.edu.unq.spring.service.impl.PacienteServiceImpl;
import ar.edu.unq.spring.service.interfaces.TurnoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TurnoServiceTest {

    @Autowired
    private TurnoService turnoService;

    @Autowired
    private PacienteDAO pacienteDAO;
    @Autowired
    private MedicoDAO medicoDAO;
    @Autowired
    private TurnoDAO turnoDAO;

    private Paciente paciente;
    private Medico medico,medicoOdontologo;

    @Autowired
    private MedicoServiceImpl medicoServiceImpl;
    @Autowired
    private PacienteServiceImpl pacienteServiceImpl;

    @BeforeEach
    void setUp() {
        paciente = new Paciente();
        paciente.setNombre("Juan Perez");
        paciente.setDni("12345678");
        pacienteServiceImpl.guardarPaciente(paciente);

        medico = new Medico();
        medico.setDni("12345679");
        medico.setNombre("Dra. Juana");
        medico.setApellido("Gomez");
        medico.setEspecialidad("Oftalmologo");
        medico.setMatricula("MD123456");
        medicoServiceImpl.guardarMedico(medico);

        medicoOdontologo = new Medico();
        medicoOdontologo.setNombre("Dr. Pablo");
        medicoOdontologo.setApellido("Lopez");
        medicoOdontologo.setEspecialidad("Odontologo");
        medicoOdontologo.setMatricula("MD987654");
        medicoOdontologo.setDni("12345670");
        medicoServiceImpl.guardarMedico(medicoOdontologo);
    }

    @Test
    void testReservarTurno() {
        Turno turno = new Turno();
        Medico medicoBD = medicoServiceImpl.recuperarMedicoPorId(medico.getIdMedico());
        Paciente pacienteBD = pacienteServiceImpl.recuperarPacientePorId(paciente.getIdPaciente());

        turno.setFecha(LocalDate.of(2025, 4,22));
        turno.setHora(LocalTime.of(9, 0));
        turno.setMedico(medicoBD);


        Turno turnoCreado = turnoService.reservarTurno(pacienteBD.getIdPaciente(), turno);

        assertNotNull(turnoCreado.getId());
        assertEquals("Juan Perez", turnoCreado.getPaciente().getNombre());
        assertEquals("Dra. Juana Gomez", turnoCreado.getMedico().getNombreCompleto());

        Medico medicoConTurno = medicoServiceImpl.recuperarMedicoPorId(medico.getIdMedico());
        Paciente pacienteConTurno = pacienteServiceImpl.recuperarPacientePorId(paciente.getIdPaciente());

        assertFalse(medicoConTurno.getTurnos().isEmpty());
        assertFalse(pacienteConTurno.getTurnos().isEmpty());
    }

    @Test
    void testElPacienteReservaDosTurnosDistintos() {
        Turno turnoOftalmologo = new Turno();
        Turno turnoOdontologo = new Turno();

        Medico medicoBD = medicoServiceImpl.recuperarMedicoPorId(medico.getIdMedico());
        Medico medicoOdontologoBD = medicoServiceImpl.recuperarMedicoPorId(medicoOdontologo.getIdMedico());

        Paciente pacienteBD = pacienteServiceImpl.recuperarPacientePorId(paciente.getIdPaciente());

        turnoOftalmologo.setFecha(LocalDate.of(2025, 4,22));
        turnoOftalmologo.setHora(LocalTime.of(9, 0));
        turnoOftalmologo.setMedico(medicoBD);

        Turno turnoCreado = turnoService.reservarTurno(pacienteBD.getIdPaciente(), turnoOftalmologo);

        assertNotNull(turnoCreado.getId());
        assertEquals("Juan Perez", turnoCreado.getPaciente().getNombre());
        assertEquals("Dra. Juana Gomez", turnoCreado.getMedico().getNombreCompleto());

        Medico medicoConTurno = medicoServiceImpl.recuperarMedicoPorId(medico.getIdMedico());
        Paciente pacienteConTurno = pacienteServiceImpl.recuperarPacientePorId(paciente.getIdPaciente());

        assertFalse(medicoConTurno.getTurnos().isEmpty());
        assertFalse(pacienteConTurno.getTurnos().isEmpty());

        turnoOdontologo.setFecha(LocalDate.of(2025, 7,13));
        turnoOdontologo.setHora(LocalTime.of(14, 30));
        turnoOdontologo.setMedico(medicoOdontologoBD);

        Turno otroTurnoCreado = turnoService.reservarTurno(pacienteBD.getIdPaciente(), turnoOdontologo);

        assertNotNull(otroTurnoCreado.getId());
        assertEquals("Juan Perez", otroTurnoCreado.getPaciente().getNombre());
        assertEquals("Dr. Pablo Lopez", otroTurnoCreado.getMedico().getNombreCompleto());

        Medico otroMedicoConTurno = medicoServiceImpl.recuperarMedicoPorId(medico.getIdMedico());
        Paciente mismoPacienteConTurno = pacienteServiceImpl.recuperarPacientePorId(paciente.getIdPaciente());

        assertFalse(otroMedicoConTurno.getTurnos().isEmpty());
        assertTrue(mismoPacienteConTurno.getTurnos().size() == 2);
    }

    @Test
    void testObtenerTodosLosTurnos() {
        Turno turno = new Turno();
        turno.setFecha(LocalDate.of(2025, 4, 20));
        turno.setHora(LocalTime.of(10, 0));
        Medico medicoBD = medicoServiceImpl.recuperarMedicoPorId(medico.getIdMedico());
        Paciente pacienteBD = pacienteServiceImpl.recuperarPacientePorId(paciente.getIdPaciente());
        turno.setMedico(medicoBD);
        turnoService.reservarTurno(pacienteBD.getIdPaciente(), turno);

        List<Turno> listaTurnos = turnoService.obtenerTodosLosTurnos();
        assertFalse(listaTurnos.isEmpty());
    }

    @Test
    void testEliminarTurno() {
        Turno turno = new Turno();
        turno.setFecha(LocalDate.of(2025, 4, 20));
        turno.setHora(LocalTime.of(10, 0));
        Medico medicoBD = medicoServiceImpl.recuperarMedicoPorId(medico.getIdMedico());
        Paciente pacienteBD = pacienteServiceImpl.recuperarPacientePorId(paciente.getIdPaciente());
        turno.setMedico(medicoBD);
        turnoService.reservarTurno(pacienteBD.getIdPaciente(), turno);

        turnoService.eliminarTurno(turno);

        Assertions.assertThrows(RuntimeException.class, () -> {
            turnoService.obtenerTurnoById(turno.getId());
        });
    }

    @AfterEach
    public void tearDown() {
        turnoService.clearAll();
        medicoServiceImpl.clearAll();
        pacienteServiceImpl.clearAll();
    }
}

