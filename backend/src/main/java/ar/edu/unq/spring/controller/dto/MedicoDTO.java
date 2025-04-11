package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.Administrador;
import ar.edu.unq.spring.modelo.Medico;

public record MedicoDTO(Long id, String nombre, String apellido, String especialidad, String matricula) {

    public static MedicoDTO desdeModelo(Medico medico) {

        return new MedicoDTO(medico.getId(), medico.getNombre(),medico.getApellido(),
                                medico.getEspecialidad(),medico.getMatricula());
    }

    public Medico aModelo() {
        Medico medico = new Medico(this.nombre, this.apellido, this.especialidad, matricula);
        medico.setId(this.id);
        return null;
    }
}
