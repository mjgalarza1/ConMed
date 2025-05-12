package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.Paciente;

public record CreatePacienteDTO(Long id, String nombre, String dni, String passwordPaciente, String apellido) {

    public static CreatePacienteDTO desdeModelo(Paciente paciente) {
        return new CreatePacienteDTO(
                paciente.getIdPaciente(),
                paciente.getNombre(),
                paciente.getDni(),
                paciente.getPasswordPaciente(),
                paciente.getApellido()
        );
    }

    public Paciente aModelo() {
        Paciente paciente = new Paciente(this.nombre, this.dni, this.passwordPaciente, this.apellido);
        paciente.setIdPaciente(this.id);
        return paciente;
    }
}
