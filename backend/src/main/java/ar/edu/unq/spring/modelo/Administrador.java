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

    public Administrador(String nombre, String dni){
        this.nombre = nombre;
        this.dni = dni;
    }
}
