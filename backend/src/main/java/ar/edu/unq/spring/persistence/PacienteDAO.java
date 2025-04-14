package ar.edu.unq.spring.persistence;

import ar.edu.unq.spring.controller.dto.PacienteDTO;
import ar.edu.unq.spring.modelo.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteDAO extends JpaRepository<Paciente, Long> {

    @Query("SELECT p FROM Paciente p WHERE p.dni = ?1")
    Paciente findByDni(String dni);
}