package ar.edu.unq.spring.service.interfaces;

import ar.edu.unq.spring.modelo.Medico;

import java.util.List;

public interface MedicoService {
    List<Medico> allMedicos();
    Medico guardarMedico(Medico medico);
    Medico recuperarMedicoPorId(Long medicoId);
    void actualizarMedico(Long medicoId, Medico medicoActualizado);
    void clearAll();
    void eliminarMedico(Long medicoId);

}