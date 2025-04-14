package ar.edu.unq.spring.service.impl;

import ar.edu.unq.spring.controller.dto.PacienteDTO;
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
    public Paciente guardarPaciente(Paciente paciente) {
        return pacienteDAO.save(paciente);
    }

    @Override
    public Paciente recuperarPacientePorId(Long pacienteId) {
        if (pacienteId == null)
            throw new RuntimeException("ID invalido");
        return this.pacienteDAO.findById(pacienteId).orElseThrow(() -> new RuntimeException("No existe ningun Paciente con este ID!"));
    }

    @Override
    public Paciente recuperarPacientePorDni(String dni) {
        if (dni == null || dni.isBlank())
            throw new RuntimeException("DNI invalido");
        Paciente paciente = pacienteDAO.findByDni(dni);
        return paciente;
    }

    @Override
    public void actualizarPaciente(Long pacienteId, Paciente pacienteActualizado) {
        if(pacienteId == null)
            throw new RuntimeException("ID invalido");

        Paciente pacienteNuevo = this.pacienteDAO.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("No existe ningun Paciente con este ID"));

        pacienteNuevo.setNombre(pacienteActualizado.getNombre());
        pacienteNuevo.setTurnos(pacienteActualizado.getTurnos());

        this.pacienteDAO.save(pacienteNuevo);
    }

    @Override
    public void eliminarPaciente(Long pacienteId) {
        pacienteDAO.deleteById(pacienteId);
    }

    @Override
    public void clearAll() {
        pacienteDAO.deleteAll();
    }
}