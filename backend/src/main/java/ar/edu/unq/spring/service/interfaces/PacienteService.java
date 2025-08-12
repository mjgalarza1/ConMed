package ar.edu.unq.spring.service.interfaces;

import ar.edu.unq.spring.modelo.Paciente;

import java.util.List;

public interface PacienteService {
    List<Paciente> allPacientes();
    Paciente guardarPaciente(Paciente paciente);
    Paciente recuperarPacientePorId(Long pacienteId);
    Paciente recuperarPacientePorDni(String dni);

    default void actualizarPaciente(Long pacienteId, Paciente pacienteNuevo){}
    void eliminarPaciente(Long pacienteId);
    void clearAll();
    List<String> getMails();

    Paciente recuperarPacientePorEmail(String email);

    Paciente actualizarPassword(Paciente paciente, String hashedPassword);
}