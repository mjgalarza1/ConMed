package ar.edu.unq.spring.service.impl;

import ar.edu.unq.spring.modelo.Paciente;
import ar.edu.unq.spring.persistence.PacienteDAO;
import ar.edu.unq.spring.service.interfaces.PacienteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class PacienteServiceImpl implements PacienteService {

    private PacienteDAO pacienteDAO = null;
    public PacienteServiceImpl(PacienteDAO personajeDAO) {
        this.pacienteDAO = pacienteDAO;
    }

    @Override
    public Set<Paciente> allPersonajes() {
        return Set.of();
    }
    @Override
    public void guardarPersonaje(Paciente paciente) {
    }

    @Override
    public Paciente recuperarPersonaje(Long personajeId) {
        return null;
    }

    @Override
    public void clearAll() {
    }
}