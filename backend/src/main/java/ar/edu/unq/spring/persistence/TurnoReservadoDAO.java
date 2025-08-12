package ar.edu.unq.spring.persistence;

import ar.edu.unq.spring.modelo.TurnoReservado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnoReservadoDAO extends JpaRepository<TurnoReservado, Long>  {

    @Modifying
    @Query("DELETE FROM TurnoReservado t WHERE t.idTurnoReservado = ?1")
    void deleteTurnoReservadoById(Long idTurnoReservado);

}
