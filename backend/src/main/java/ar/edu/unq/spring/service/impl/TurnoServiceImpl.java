package ar.edu.unq.spring.service.impl;

import ar.edu.unq.spring.modelo.Medico;
import ar.edu.unq.spring.modelo.Paciente;
import ar.edu.unq.spring.modelo.Turno;
import ar.edu.unq.spring.persistence.MedicoDAO;
import ar.edu.unq.spring.persistence.PacienteDAO;
import ar.edu.unq.spring.persistence.TurnoDAO;
import ar.edu.unq.spring.service.interfaces.TurnoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@SecurityRequirement(name = "bearerAuth")
@Service
@Transactional
public class TurnoServiceImpl implements TurnoService {

    private final TurnoDAO turnoDAO;
    private final PacienteDAO pacienteDAO;
    private final MedicoDAO medicoDAO;

    public TurnoServiceImpl(TurnoDAO turnoDAO, PacienteDAO pacienteDAO, MedicoDAO medicoDAO) {
        this.turnoDAO = turnoDAO;
        this.pacienteDAO = pacienteDAO;
        this.medicoDAO = medicoDAO;
    }

    @Override
    public Turno crearTurno(Turno turno) {
        return turnoDAO.save(turno);
    }

    @Override
    public List<Turno> obtenerTodosLosTurnos() {
        return turnoDAO.findAll();
    }

    @Override
    public Turno obtenerTurnoById(Long id) {
        if(id == null)
            throw new RuntimeException("No existe ningun Turno con este ID");

        Turno turnoActual = this.turnoDAO.findById(id).orElseThrow(() -> new RuntimeException("No existe un Turno para este ID"));
        return turnoActual;
    }

    @Override
    public void actualizarTurno(Long id, Turno turnoNuevo) {
        if(id == null)
            throw new RuntimeException("No existe ningun turno con este ID");
        Turno turnoActual = this.turnoDAO.findById(id).orElseThrow(() -> new RuntimeException("No existe un Turno para este ID"));

        turnoNuevo.setId(turnoActual.getId());
        turnoDAO.save(turnoNuevo);
    }

    @Override
    @Transactional
    public void eliminarTurno(Turno turno) {
        Turno turnoBD = turnoDAO.findById(turno.getId())
                .orElseThrow(() -> new RuntimeException("Turno no encontrado"));

        Paciente paciente = turnoBD.getPaciente();
        Medico medico = turnoBD.getMedico();

        if (paciente != null) {
            paciente.getTurnos().remove(turnoBD);
        }
        if (medico != null) {
            medico.getTurnos().remove(turnoBD);
        }

        turnoDAO.delete(turnoBD);
    }

    @Override
    public Turno reservarTurno(Long pacienteId, Turno turno){
        Paciente pacienteActual = pacienteDAO.findById(pacienteId).orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        turno.setPaciente(pacienteActual);
        turnoDAO.save(turno);
        return turno;
    }

    @Override
    public Set<Turno> obtenerTurnoByPaciente(Long pacienteId) {
        Paciente pacienteBD = pacienteDAO.findById(pacienteId).orElseThrow(() -> new RuntimeException("No existe ningun Paciente con este ID"));
        return pacienteBD.getTurnos();
    }

    @Override
    public Set<Turno> obtenerTurnoByMedico(Long medicoId) {
        Medico medicoBD = medicoDAO.findById(medicoId).orElseThrow(() -> new RuntimeException("No existe ningun Medico con este ID"));
        return medicoBD.getTurnos();
    }

    @Override
    public void clearAll() {
        turnoDAO.deleteAllInBatch();
    }


}
