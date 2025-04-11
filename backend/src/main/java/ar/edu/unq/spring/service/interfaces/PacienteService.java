package ar.edu.unq.spring.service.interfaces;

import ar.edu.unq.spring.modelo.Medico;
import ar.edu.unq.spring.modelo.Paciente;

import java.util.List;

public interface PacienteService {
    List<Paciente> allPacientes();
    void guardarPaciente(Paciente paciente);
    Paciente recuperarPaciente(Long paceinteId);

    void clearAll();
}