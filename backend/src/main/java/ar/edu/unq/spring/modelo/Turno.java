package ar.edu.unq.spring.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@ToString
@NoArgsConstructor

@Entity
public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;
    private LocalTime hora;

    @ManyToOne
    @JoinColumn(name = "medico_id")
    private Medico medico;

    @Enumerated(EnumType.STRING)
    private TurnoDisponibilidad disponibilidad;

}
