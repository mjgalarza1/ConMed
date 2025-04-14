package ar.edu.unq.spring.persistence;

import ar.edu.unq.spring.controller.dto.TurnoReservadoDTO;
import ar.edu.unq.spring.modelo.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TurnoDAO extends JpaRepository<Turno, Long> {

    @Query("SELECT t FROM Turno t WHERE t.medico.idMedico = ?1 AND t.disponibilidad = 'DISPONIBLE'")
    Set<Turno> obtenerTurnosDisponiblesDelMedico(Long idMedico);

    @Query("""
        SELECT new ar.edu.unq.spring.controller.dto.TurnoReservadoDTO(
            t.fecha,
            t.hora,
            CONCAT(m.nombre, ' ', m.apellido),
            m.especialidad
        )
        FROM Turno t
        JOIN TurnoReservado tr ON tr.idTurnoReservado = t.id
        JOIN Paciente p ON tr.paciente.idPaciente = p.idPaciente
        JOIN Medico m ON t.medico.idMedico = m.idMedico
        WHERE p.idPaciente = :pacienteId
    """)
    Set<TurnoReservadoDTO> obtenerTurnosReservadosDelPaciente(@Param("pacienteId") Long pacienteId);
}
