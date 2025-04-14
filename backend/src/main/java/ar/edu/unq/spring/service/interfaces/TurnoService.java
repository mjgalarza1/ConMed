package ar.edu.unq.spring.service.interfaces;

import ar.edu.unq.spring.controller.dto.TurnoReservadoDTO;
import ar.edu.unq.spring.modelo.Turno;

import java.util.List;
import java.util.Set;


public interface TurnoService {

    Turno crearTurno(Turno turno);
    List<Turno> obtenerTodosLosTurnos();
    Turno obtenerTurnoById(Long id);
    void actualizarTurno(Long id, Turno turnoNuevo);
    void eliminarTurno(Turno turno);
    Turno reservarTurno(Long pacienteId, Turno turno);
    Set<Turno> obtenerTurnoByPaciente(Long pacienteId);
    Set<Turno> obtenerTurnoByMedico(Long medicoId);
    Set<Turno> obtenerTurnosDisponiblesDeMedicoById(Long medicoId);
    Set<TurnoReservadoDTO> obtenerTurnosReservadosDePacienteById(Long pacienteId);

    void clearAll();
}
