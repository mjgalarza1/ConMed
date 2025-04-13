package ar.edu.unq.spring.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@ToString
@NoArgsConstructor

@Entity
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMedico;
    @Column(nullable = false, unique = true)
    private String dni;
    @Column(nullable = false, length = 500, unique = true)
    private String nombre;
    private String apellido;
    private String especialidad;
    private String matricula;

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


    public String getNombreCompleto(){return this.nombre + " " + this.apellido;}

}