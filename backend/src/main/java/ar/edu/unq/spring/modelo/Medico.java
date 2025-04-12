package ar.edu.unq.spring.modelo;

import jakarta.persistence.*;
import lombok.*;

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

    public Medico(String nombre, String apellido, String dni, String especialidad, String matricula) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.especialidad = especialidad;
        this.matricula = matricula;
    }

}