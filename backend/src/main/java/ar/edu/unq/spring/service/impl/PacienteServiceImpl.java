package ar.edu.unq.spring.service.impl;

import ar.edu.unq.spring.controller.utils.Validator;
import ar.edu.unq.spring.modelo.Paciente;
import ar.edu.unq.spring.modelo.Usuario;
import ar.edu.unq.spring.persistence.PacienteDAO;
import ar.edu.unq.spring.persistence.UsuarioDAO;
import ar.edu.unq.spring.service.interfaces.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteDAO pacienteDAO;
    @Autowired
    private UsuarioDAO usuarioDAO;

    @Override
    public List<Paciente> allPacientes() {
        return pacienteDAO.findAll();
    }
    @Override
    public Paciente guardarPaciente(Paciente paciente) {
        if (pacienteDAO.existsByMail(paciente.getMail())) {
            throw new IllegalArgumentException("Ya existe un paciente registrado con ese mail.");
        }
        return pacienteDAO.save(paciente);
    }

    @Override
    public Paciente actualizarPassword(Paciente paciente, String nuevaPasswordHashed) {
        paciente.setPassword(nuevaPasswordHashed);
        Usuario usuario = usuarioDAO.findByDni(paciente.getDni());
        usuario.setPassword(nuevaPasswordHashed);
        usuarioDAO.save(usuario);
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
        pacienteNuevo.setApellido(pacienteActualizado.getApellido());
        pacienteNuevo.setTurnos(pacienteActualizado.getTurnos());
        pacienteNuevo.setMail(pacienteActualizado.getMail());

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

    @Override
    public List<String> getMails() {
        return pacienteDAO.getMails();
    }

    @Override
    public Paciente recuperarPacientePorEmail(String email) {
        if (email == null)
            throw new RuntimeException("Email invalido");

        Paciente paciente = this.pacienteDAO.findByEmail(email);
        if (paciente == null) {
            throw new RuntimeException("No existe ningun Paciente con este Email!");
        }
        return paciente;
    }
}