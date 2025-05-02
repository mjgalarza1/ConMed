package ar.edu.unq.spring.controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record TurnoReservadoDTO(
        Long id,
        LocalDate fecha,
        LocalTime hora,
        String nombreMedico,
        String especialidad
) {}
