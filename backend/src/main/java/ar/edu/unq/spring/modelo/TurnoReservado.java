package ar.edu.unq.spring.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor

@Entity
@Table(name = "turnosReservados")
public class TurnoReservado {
    @Id
    private Long idTurnoReservado;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    public TurnoReservado(Long idTurnoReservado, Paciente paciente) {
        this.idTurnoReservado = idTurnoReservado;
        this.paciente = paciente;
    }

}
