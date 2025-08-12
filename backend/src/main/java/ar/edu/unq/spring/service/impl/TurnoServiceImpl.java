package ar.edu.unq.spring.service.impl;

import ar.edu.unq.spring.controller.dto.TurnoReservadoDTO;
import ar.edu.unq.spring.controller.dto.TurnosMedicoDTO;
import ar.edu.unq.spring.modelo.*;
import ar.edu.unq.spring.persistence.MedicoDAO;
import ar.edu.unq.spring.persistence.PacienteDAO;
import ar.edu.unq.spring.persistence.TurnoDAO;
import ar.edu.unq.spring.persistence.TurnoReservadoDAO;
import ar.edu.unq.spring.service.interfaces.TurnoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SecurityRequirement(name = "bearerAuth")
@Service
@Transactional
public class TurnoServiceImpl implements TurnoService {

    private final TurnoDAO turnoDAO;
    private final TurnoReservadoDAO turnoReservadoDAO;
    private final PacienteDAO pacienteDAO;
    private final MedicoDAO medicoDAO;
    private final EmailService emailService;

    public TurnoServiceImpl(TurnoDAO turnoDAO, TurnoReservadoDAO turnoReservadoDAO, PacienteDAO pacienteDAO, MedicoDAO medicoDAO, EmailService emailService) {
        this.turnoDAO = turnoDAO;
        this.turnoReservadoDAO = turnoReservadoDAO;
        this.pacienteDAO = pacienteDAO;
        this.medicoDAO = medicoDAO;
        this.emailService = emailService;
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

        if (turnoBD.getDisponibilidad() == TurnoDisponibilidad.RESERVADO) {
            TurnoReservado turnoReservado = turnoReservadoDAO.findById(turno.getId())
                    .orElseThrow(() -> new RuntimeException("Turno reservado no encontrado"));
            Paciente paciente = turnoReservado.getPaciente();
            paciente.getTurnos().remove(turnoBD);
            turnoReservadoDAO.delete(turnoReservado);
        }

        Medico medico = turnoBD.getMedico();
        medico.getTurnos().remove(turnoBD);

        turnoDAO.delete(turnoBD);
    }

    @Override
    public Turno reservarTurno(Long pacienteId, Turno turno){
        Paciente pacienteActual = pacienteDAO.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        if (turno.getDisponibilidad() == TurnoDisponibilidad.RESERVADO) {
            throw new RuntimeException("No se pudo reservar el turno porque el mismo ya está reservado");
        }

        turno.setDisponibilidad(TurnoDisponibilidad.RESERVADO);
        TurnoReservado turnoReservado = new TurnoReservado(turno.getId(), pacienteActual);

        turnoDAO.save(turno);
        turnoReservadoDAO.save(turnoReservado);
        emailService.enviarEstadoTurnoPaciente(pacienteActual, turno, "RESERVADO");

        return turno;
    }

    @Override
    public Turno cancelarTurno(Long idTurno) {
        Turno turnoRecuperado = turnoDAO.findById(idTurno)
                .orElseThrow(() -> new IllegalArgumentException("No existe ningún turno con este ID: " + idTurno));

        if (turnoRecuperado.getDisponibilidad() != TurnoDisponibilidad.RESERVADO) {
            throw new IllegalArgumentException("No se puede cancelar un turno que no está reservado");
        }

        turnoRecuperado.setDisponibilidad(TurnoDisponibilidad.DISPONIBLE);

        //
        TurnoReservado turnoReservado = turnoReservadoDAO.findById(turnoRecuperado.getId())
                .orElseThrow(() -> new IllegalArgumentException("No existe ningún turno con este ID: " + turnoRecuperado.getId()));
        //
        turnoReservadoDAO.deleteTurnoReservadoById(idTurno);
        turnoDAO.save(turnoRecuperado);
        emailService.enviarEstadoTurnoPaciente(turnoReservado.getPaciente(), turnoRecuperado, "CANCELADO");

        return turnoRecuperado;
    }

    @Override
    public Set<Turno> obtenerTurnoByPaciente(Long pacienteId) {
        Paciente pacienteBD = pacienteDAO.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("No existe ningun Paciente con este ID"));

        Set<TurnoReservado> turnosDelPaciente = pacienteBD.getTurnos();

        List<Turno> turnos = turnoDAO.findAllById(
                turnosDelPaciente
                        .stream()
                        .map(TurnoReservado::getIdTurnoReservado).collect(Collectors.toSet())
        );

        return new HashSet<>(turnos);
    }

    @Override
    public Set<TurnosMedicoDTO> obtenerTurnosByDniMedico(String medicoDni) {
        return turnoDAO.obtenerTurnosDelMedicoPorDni(medicoDni);
    }
    
    @Override
    public Set<Turno> obtenerTurnosDisponiblesDeMedicoById(Long medicoId) {
        return turnoDAO.obtenerTurnosDisponiblesDelMedico(medicoId);
    }

    @Override
    public Set<TurnoReservadoDTO> obtenerTurnosReservadosDePacienteById(Long pacienteId) {
        return turnoDAO.obtenerTurnosReservadosDelPaciente(pacienteId);
    }

    @Override
    public void clearAll() {
        turnoDAO.deleteAllInBatch();
    }

    @Override
    public boolean existeTurnoConFechaYHora(LocalDate fecha, LocalTime hora) {
        return turnoDAO.existeTurnoConFechaYHora(fecha, hora);

    }

}
