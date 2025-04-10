package ar.edu.unq.spring.service.interfaces;

import ar.edu.unq.spring.modelo.Paciente;

import java.util.Set;

public interface PacienteService {
    Set<Paciente> allPersonajes();
    void guardarPersonaje(Paciente paciente);
    Paciente recuperarPersonaje(Long personajeId);
    void clearAll();
}