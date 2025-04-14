package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.Medico;
import ar.edu.unq.spring.modelo.Turno;
import ar.edu.unq.spring.modelo.TurnoDisponibilidad;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalTime;

public record TurnoDTO(Long idTurno, Long medicoId, LocalDate fecha,
                       @Schema(type = "string", example = "10:00")
                       @JsonFormat(pattern = "HH:mm") LocalTime hora,
                       TurnoDisponibilidad disponibilidad) {

    public static TurnoDTO desdeModelo(Turno turno) {
        return new TurnoDTO(
                turno.getId(),
                turno.getMedico().getIdMedico(),
                turno.getFecha(),
                turno.getHora(),
                turno.getDisponibilidad()
        );
    }

    public Turno aModelo(Medico medico) {
        Turno turno = new Turno();
        turno.setMedico(medico);
        turno.setHora(this.hora);
        turno.setFecha(this.fecha);
        turno.setDisponibilidad(this.disponibilidad);
        turno.setId(this.idTurno);
        return turno;
    }
}
