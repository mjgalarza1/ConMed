package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.Medico;

public record CreateMedicoDTO(Long id, String nombre, String apellido, String especialidad, String matricula, String passwordMedico){

    public static CreateMedicoDTO desdeModelo(Medico medico) {

        return new CreateMedicoDTO(medico.getIdMedico(), medico.getNombre(),medico.getApellido(),
                medico.getEspecialidad(),medico.getMatricula(), medico.getPasswordMedico());
    }

    public Medico aModelo() {
        Medico medico = new Medico();
        medico.setNombre(this.nombre);
        medico.setApellido(this.apellido);
        medico.setEspecialidad(this.especialidad);
        medico.setMatricula(this.matricula);
        medico.setPasswordMedico(this.passwordMedico);

        return medico;
    }

}
