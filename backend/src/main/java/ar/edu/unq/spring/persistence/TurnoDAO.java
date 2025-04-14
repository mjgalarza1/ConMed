package ar.edu.unq.spring.persistence;

import ar.edu.unq.spring.modelo.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TurnoDAO extends JpaRepository<Turno, Long> {

    @Query("SELECT t FROM Turno t WHERE t.medico.idMedico = ?1 AND t.disponibilidad = 'DISPONIBLE'")
    Set<Turno> obtenerTurnosDisponiblesDelMedico(Long idMedico);

}
