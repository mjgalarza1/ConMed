package ar.edu.unq.spring.persistence;

import ar.edu.unq.spring.modelo.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoDAO extends JpaRepository<Medico, Long> {
}
