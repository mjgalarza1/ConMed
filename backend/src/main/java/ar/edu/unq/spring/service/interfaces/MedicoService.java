package ar.edu.unq.spring.service.interfaces;

import ar.edu.unq.spring.controller.dto.TurnoDTO;
import ar.edu.unq.spring.modelo.Medico;
import ar.edu.unq.spring.modelo.Turno;

import java.util.List;

public interface MedicoService {
    List<Medico> allMedicos();
    Medico guardarMedico(Medico medico);
    Medico recuperarMedicoPorId(Long medicoId);
    Medico recuperarMedicoPorDni(String dni);
    void actualizarMedico(Long medicoId, Medico medicoActualizado);
    void clearAll();
    void eliminarMedico(Long medicoId);
    TurnoDTO agregarTurno(Turno turno);
    List<String> getAllMatriculas();
    Boolean estaDisponible(Long medicoId);


}