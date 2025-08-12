package ar.edu.unq.spring.persistence;

import ar.edu.unq.spring.modelo.Administrador;
import ar.edu.unq.spring.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdministradorDAO extends JpaRepository<Administrador, Long> {

    Administrador findByDni(String dni);
}
