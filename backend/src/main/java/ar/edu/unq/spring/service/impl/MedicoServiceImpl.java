package ar.edu.unq.spring.service.impl;

import ar.edu.unq.spring.controller.dto.TurnoDTO;
import ar.edu.unq.spring.modelo.Medico;
import ar.edu.unq.spring.modelo.Turno;
import ar.edu.unq.spring.persistence.MedicoDAO;
import ar.edu.unq.spring.persistence.TurnoDAO;
import ar.edu.unq.spring.service.interfaces.MedicoService;
import ar.edu.unq.spring.service.interfaces.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
public class MedicoServiceImpl implements MedicoService {

    @Autowired
    private MedicoDAO medicoDAO;

    @Autowired
    private TurnoService turnoService;
    @Autowired
    private TurnoDAO turnoDAO;

    @Override
    public List<Medico> allMedicos() {
        return medicoDAO.findAll();
    }
    @Override
    public Medico guardarMedico(Medico medico) {
        return medicoDAO.save(medico);
    }

    @Override
    public Medico recuperarMedicoPorId(Long medicoId) {
        if (medicoId == null)
            throw new IllegalArgumentException("ID invalido");
        return this.medicoDAO.findById(medicoId).orElseThrow(() -> new IllegalArgumentException("No existe ningun medico con este ID!"));
    }

    @Override
    public Medico recuperarMedicoPorDni(String dni) {
        if (dni == null || dni.isBlank())
            throw new IllegalArgumentException("DNI invalido");
        Medico medico = medicoDAO.findByDni(dni);
        return medico;
    }

    public void actualizarMedico(Long medicoId, Medico medicoActualizado) {
        if(medicoId == null)
            throw new IllegalArgumentException("ID invalido");

        Medico medicoNuevo = this.medicoDAO.findById(medicoId)
                .orElseThrow(() -> new IllegalArgumentException("No existe ningun Medico con este ID"));

        medicoNuevo.setNombre(medicoActualizado.getNombre());
        medicoNuevo.setApellido(medicoActualizado.getApellido());
        medicoNuevo.setEspecialidad(medicoActualizado.getEspecialidad());
        medicoNuevo.setMatricula(medicoActualizado.getMatricula());

        this.medicoDAO.save(medicoNuevo);
    }

    public TurnoDTO agregarTurno(Turno turno) {
        if (turno == null) {
            throw new IllegalArgumentException("Turno invalido");
        }
        if (turnoService.existeTurnoConFechaYHora(turno.getFecha(), turno.getHora())) {
            throw new IllegalArgumentException("No se puede agregar el turno porque ya existe un turno con la misma fecha y hora que las dadas");
        }
        LocalDate fechaActual = LocalDate.now();
        LocalTime horaActual = LocalTime.now();
        if (
                turno.getFecha().isBefore(fechaActual)
                && turno.getHora().isBefore(horaActual)
        ) {
            throw new IllegalArgumentException("No se puede agregar un turno en una fecha y hora anterior a la fecha y hora actual");
        }

        Turno nuevoTurno = turnoService.crearTurno(turno);
        TurnoDTO turnoDTO = new TurnoDTO(nuevoTurno.getId(), nuevoTurno.getMedico().getIdMedico() ,nuevoTurno.getFecha(), nuevoTurno.getHora(), nuevoTurno.getDisponibilidad());

        return turnoDTO;
    }

    public Boolean estaDisponible(Long medicoId) {
        return turnoService.obtenerTurnosDisponiblesDeMedicoById(medicoId).size() > 0;
    }

    @Override
    public void clearAll() {
        medicoDAO.deleteAll();
    }

    @Override
    public void eliminarMedico(Long medicoId) {
        medicoDAO.deleteById(medicoId);
    }

    @Override
    public List<String> getAllMatriculas() {
        return medicoDAO.getAllMatriculas();
    }
}