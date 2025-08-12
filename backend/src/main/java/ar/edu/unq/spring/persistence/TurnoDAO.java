package ar.edu.unq.spring.persistence;

import ar.edu.unq.spring.controller.dto.TurnoReservadoDTO;
import ar.edu.unq.spring.controller.dto.TurnosMedicoDTO;
import ar.edu.unq.spring.modelo.Medico;
import ar.edu.unq.spring.modelo.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Repository
public interface TurnoDAO extends JpaRepository<Turno, Long> {

    @Query("SELECT t FROM Turno t WHERE t.medico.idMedico = ?1 AND t.disponibilidad = 'DISPONIBLE'")
    Set<Turno> obtenerTurnosDisponiblesDelMedico(Long idMedico);

    @Query("""
        SELECT new ar.edu.unq.spring.controller.dto.TurnoReservadoDTO(
            t.id,
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

    @Query("""
        SELECT new ar.edu.unq.spring.controller.dto.TurnosMedicoDTO(
                t.fecha,
                t.hora,
                t.disponibilidad,
                p.nombre
            )
            FROM Turno t
            LEFT JOIN TurnoReservado tr ON tr.idTurnoReservado = t.id
            LEFT JOIN Paciente p ON p.idPaciente = tr.paciente.idPaciente
            LEFT JOIN Medico m ON t.medico.idMedico = m.idMedico
            WHERE t.fecha >= CURRENT_DATE
              AND m.dni = ?1
    """)
    Set<TurnosMedicoDTO> obtenerTurnosDelMedicoPorDni(String dniMedico);

    Long medico(Medico medico);

    @Query("""
    SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END
    FROM Turno t
    WHERE t.fecha = :fecha AND t.hora = :hora
""")
    boolean existeTurnoConFechaYHora(@Param("fecha") LocalDate fecha, @Param("hora") LocalTime hora);

}
