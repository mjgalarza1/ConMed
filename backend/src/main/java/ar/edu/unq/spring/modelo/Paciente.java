package ar.edu.unq.spring.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
    @Size(min = 6, message = "La contraseña debe tener un mínimo de 6 caracteres")
    private String passwordPaciente;

    public Paciente(String nombre, String dni) {
        this.nombre = nombre;
        this.dni = dni;
    }

    public Paciente(String nombre, String dni, String passwordPaciente) {
        this.nombre = nombre;
        this.dni = dni;
        this.passwordPaciente = passwordPaciente;
    }
}