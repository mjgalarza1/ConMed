package ar.edu.unq.spring.modelo;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor

@Entity
public class Medico {

    public Medico(String nombre, String apellido, String especialidad, String matricula) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
        this.matricula = matricula;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500, unique = true)
    private String nombre;

    private String apellido;
    private String especialidad;
    private String matricula;


}