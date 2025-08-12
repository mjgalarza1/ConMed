package ar.edu.unq.spring.service.interfaces;

import ar.edu.unq.spring.modelo.Administrador;
import ar.edu.unq.spring.modelo.Medico;
import ar.edu.unq.spring.modelo.Paciente;

import java.util.List;

public interface AdministradorService {
    List<Administrador> allAdministradores();
    Administrador guardarAdministrador(Administrador administrador);
    Administrador recuperarAdministrador(String administradorId);
    void clearAll();
    Medico agregarMedico(Medico medico);
    void quitarMedico(Long medicoId);
    List<Medico> allMedicos();
    default void actualizarAdministrador(Long administradorId, Administrador administradorNuevo) {}
}