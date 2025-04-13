package ar.edu.unq.spring.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@ToString
@NoArgsConstructor

@Entity
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPaciente;
    @Column(nullable = false, unique = true)
    private String dni;
    @Column(nullable = false, length = 500)
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

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = false,fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<Turno> turnos;
}