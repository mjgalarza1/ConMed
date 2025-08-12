package ar.edu.unq.spring.modelo;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor

@Entity
public class Administrador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAdmin;
    @Column(nullable = false, unique = true)
    private String dni;
    private String nombre;
    private String apellido;

    public Administrador(String nombre, String dni, String apellido){
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
    }
}
