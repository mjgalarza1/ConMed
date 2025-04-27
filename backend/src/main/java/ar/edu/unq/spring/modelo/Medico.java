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
@Table(name = "medico",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_dni", columnNames = "dni"),
                @UniqueConstraint(name = "unique_matricula", columnNames = "matricula")
        }
)
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMedico;
    @Column(nullable = false, unique = true)
    private String dni;
    @Column(nullable = false, length = 500)
    private String nombre;
    private String apellido;
    private String especialidad;
    @Column(nullable = false, length = 500, unique = true)
    private String matricula;
    @Size(min = 6, message = "La contraseña debe tener un mínimo de 6 caracteres")
    private String passwordMedico;

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<Turno> turnos;

    public Medico(String nombre, String apellido, String dni, String especialidad, String matricula) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.especialidad = especialidad;
        this.matricula = matricula;
    }

    public Medico(String nombre, String apellido, String dni, String especialidad, String matricula, String passwordMedico) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.especialidad = especialidad;
        this.matricula = matricula;
        this.passwordMedico = passwordMedico;
    }

    public String getNombreCompleto(){return this.nombre + " " + this.apellido;}

}