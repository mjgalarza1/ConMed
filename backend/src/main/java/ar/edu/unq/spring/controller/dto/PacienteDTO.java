package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.Paciente;

public record PacienteDTO(Long id, String nombre, String dni) {

    public static PacienteDTO desdeModelo(Paciente p) {
        return new PacienteDTO(
                p.getIdPaciente(),
                p.getNombre(),
                p.getDni()
        );
    }

    public Paciente aModelo() {
        Paciente paciente = new Paciente(this.nombre, this.dni);
        paciente.setIdPaciente(this.id);
        return paciente;
    }

    public Paciente aModeloUpdate(Long id) {
        Paciente paciente = new Paciente();
        paciente.setIdPaciente(id);
        paciente.setNombre(this.nombre);

        return paciente;
    }
}
