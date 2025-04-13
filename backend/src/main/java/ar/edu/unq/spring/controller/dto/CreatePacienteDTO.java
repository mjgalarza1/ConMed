package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.Administrador;
import ar.edu.unq.spring.modelo.Paciente;

public record CreatePacienteDTO(Long id, String nombre, String dni, String passwordPaciente) {

    public static CreatePacienteDTO desdeModelo(Paciente paciente) {
        return new CreatePacienteDTO(
                paciente.getIdPaciente(),
                paciente.getNombre(),
                paciente.getDni(),
                paciente.getPasswordPaciente()
        );
    }

    public Administrador aModelo() {
        Paciente paciente = new Paciente(this.nombre, this.dni, this.passwordPaciente);
        paciente.setIdPaciente(this.id);
        return null;
    }
}
