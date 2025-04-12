package ar.edu.unq.spring.modelo;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPaciente;
    @Column(nullable = false, unique = true)
    private String dni;
    @Column(nullable = false, length = 500, unique = true)
    private String nombre;

    public Paciente(String nombre, String dni) {
        this.nombre = nombre;
        this.dni = dni;
    }
}