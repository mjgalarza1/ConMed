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
    private String apellido;
    @Size(min = 6, message = "La contraseña debe tener un mínimo de 6 caracteres")
    private String passwordPaciente;
    @Column(unique = true)
    private String mail;

    public Paciente(String nombre,String dni, String apellido, String mail) {
        this.nombre = nombre;
        this.dni = dni;
        this.apellido = apellido;
        this.mail = mail;
    }

    public Paciente(String nombre, String dni, String passwordPaciente, String apellido, String mail) {
        this.nombre = nombre;
        this.dni = dni;
        this.passwordPaciente = passwordPaciente;
        this.apellido = apellido;
        this.mail = mail;
    }

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = false,fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<TurnoReservado> turnos;

    public void setPassword(String nuevaPassword) {
        this.passwordPaciente = nuevaPassword;
    }
}