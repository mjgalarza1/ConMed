package ar.edu.unq.spring.persistence;

import ar.edu.unq.spring.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioDAO extends JpaRepository<Usuario, Long> {

    Usuario findByDni(String dni);
}
