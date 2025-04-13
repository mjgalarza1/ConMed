package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.Medico;
import ar.edu.unq.spring.modelo.Paciente;
import ar.edu.unq.spring.modelo.Turno;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalTime;

public record TurnoPacienteDTO(Long medicoId, LocalDate fecha,
                               @Schema(type = "string", example = "10:00")
                       @JsonFormat(pattern = "HH:mm") LocalTime hora,
                               String especialidad,
                               String nombreMedico) {

    public static TurnoPacienteDTO desdeModelo(Turno turno) {
        return new TurnoPacienteDTO(
                turno.getMedico().getIdMedico(),
                turno.getFecha(),
                turno.getHora(),
                turno.getMedico().getEspecialidad(),
                turno.getMedico().getNombreCompleto()

        );
    }

    public static TurnoPacienteDTO desdeModeloMedico(Turno turno) {
        return new TurnoPacienteDTO(
                turno.getMedico().getIdMedico(),
                turno.getFecha(),
                turno.getHora(),
                turno.getMedico().getEspecialidad(),
                turno.getMedico().getNombreCompleto()
        );
    }

    public Turno aModelo(Paciente paciente, Medico medico) {
        Turno turno = new Turno();
        turno.setPaciente(paciente);
        turno.setMedico(medico);
        turno.setHora(this.hora);
        turno.setFecha(this.fecha);

        return turno;
    }
}
