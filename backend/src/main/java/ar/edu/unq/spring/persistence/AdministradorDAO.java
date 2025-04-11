package ar.edu.unq.spring.persistence;

import ar.edu.unq.spring.modelo.Administrador;
import ar.edu.unq.spring.modelo.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministradorDAO extends JpaRepository<Administrador, Long> {
}
