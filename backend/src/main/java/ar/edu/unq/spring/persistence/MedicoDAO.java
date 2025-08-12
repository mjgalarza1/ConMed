package ar.edu.unq.spring.persistence;

import ar.edu.unq.spring.modelo.Medico;
import ar.edu.unq.spring.modelo.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicoDAO extends JpaRepository<Medico, Long> {

    @Query("SELECT m FROM Medico m WHERE m.dni = ?1")
    Medico findByDni(String dni);

    @Query("SELECT m.matricula FROM Medico m")
    List<String> getAllMatriculas();
}
