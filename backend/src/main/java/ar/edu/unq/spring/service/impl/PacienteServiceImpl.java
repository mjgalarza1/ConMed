package ar.edu.unq.spring.service.impl;

import ar.edu.unq.spring.modelo.Medico;
import ar.edu.unq.spring.modelo.Paciente;
import ar.edu.unq.spring.persistence.PacienteDAO;
import ar.edu.unq.spring.service.interfaces.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteDAO pacienteDAO;

    @Override
    public List<Paciente> allPacientes() {
        return pacienteDAO.findAll();
    }
    @Override
    public void guardarPaciente(Paciente paciente) {
    }

    @Override
    public Paciente recuperarPaciente(Long pacienteId) {
        return null;
    }

    @Override
    public void clearAll() {
    }
}