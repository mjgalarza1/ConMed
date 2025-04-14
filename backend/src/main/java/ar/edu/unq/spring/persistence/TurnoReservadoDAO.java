package ar.edu.unq.spring.persistence;

import ar.edu.unq.spring.modelo.TurnoReservado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnoReservadoDAO extends JpaRepository<TurnoReservado, Long>  {
}
