package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.Administrador;
import ar.edu.unq.spring.modelo.Medico;

public record MedicoDTO(Long id, String nombre, String apellido, String especialidad, String matricula) {

    public static MedicoDTO desdeModelo(Medico medico) {

        return new MedicoDTO(medico.getIdMedico(), medico.getNombre(),medico.getApellido(),
                medico.getEspecialidad(),medico.getMatricula());
    }

    public Medico aModelo() {
        Medico medico = new Medico();
        medico.setNombre(this.nombre);
        medico.setApellido(this.apellido);
        medico.setEspecialidad(this.especialidad);
        medico.setMatricula(this.matricula);

        return medico;
    }

    public Medico aModeloUpdate(Long id) {
        Medico medico = new Medico();
        medico.setIdMedico(id);
        medico.setNombre(this.nombre);
        medico.setApellido(this.apellido);
        medico.setEspecialidad(this.especialidad);
        medico.setMatricula(this.matricula);

        return medico;
    }
}
