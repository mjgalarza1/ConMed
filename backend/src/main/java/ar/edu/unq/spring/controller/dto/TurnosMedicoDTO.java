package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.TurnoDisponibilidad;

import java.time.LocalDate;
import java.time.LocalTime;

public record TurnosMedicoDTO(LocalDate fecha,
                              LocalTime hora,
                              TurnoDisponibilidad disponibilidad,
                              String nombrePaciente) {
}
