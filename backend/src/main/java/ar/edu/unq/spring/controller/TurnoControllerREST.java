package ar.edu.unq.spring.controller;

import ar.edu.unq.spring.controller.dto.ReservaDeTurnoDTO;
import ar.edu.unq.spring.controller.dto.TurnoDTO;
import ar.edu.unq.spring.controller.dto.TurnoReservadoDTO;
import ar.edu.unq.spring.controller.dto.TurnosMedicoDTO;
import ar.edu.unq.spring.modelo.Turno;
import ar.edu.unq.spring.service.interfaces.TurnoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/turnos")
public class TurnoControllerREST {

    private final TurnoService turnoService;

    public TurnoControllerREST(TurnoService turnoService){
        this.turnoService = turnoService;
    }

    @PostMapping("/reservar")
    public ResponseEntity<?> reservarTurno(@RequestBody ReservaDeTurnoDTO reservaDTO){
        Turno nuevoTurno = turnoService.obtenerTurnoById(reservaDTO.idTurno());
        turnoService.reservarTurno(reservaDTO.idPaciente(), nuevoTurno);
        return ResponseEntity.status(HttpStatus.CREATED).body(TurnoDTO.desdeModelo(nuevoTurno));
    }

    @PutMapping("/paciente/cancelar/{id_turno}")
    public ResponseEntity<TurnoDTO> cancelarTurno(@PathVariable Long id_turno){
        return ResponseEntity.ok(TurnoDTO.desdeModelo(turnoService.cancelarTurno(id_turno)));
    }

    @GetMapping("/paciente/{id_paciente}")
    public Set<TurnoDTO> turnosDelPaciente(@PathVariable Long id_paciente){
        return turnoService.obtenerTurnoByPaciente(id_paciente).stream().map(TurnoDTO::desdeModelo).collect(Collectors.toSet());
    }

    @GetMapping("/paciente/{id_paciente}/turnosReservados")
    public Set<TurnoReservadoDTO> turnosReservadosDelPaciente(@PathVariable Long id_paciente){
        return turnoService.obtenerTurnosReservadosDePacienteById(id_paciente);
    }

    @GetMapping("/medico/{dni_medico}")
    public Set<TurnosMedicoDTO> turnosDelMedico(@PathVariable String dni_medico){
        return turnoService.obtenerTurnosByDniMedico(dni_medico);
    }

    @GetMapping("/medico/{id_medico}/turnosDisponibles")
    public Set<TurnoDTO> turnosDisponiblesDelMedico(@PathVariable Long id_medico){
        return turnoService.obtenerTurnosDisponiblesDeMedicoById(id_medico).stream().map(TurnoDTO::desdeModelo).collect(Collectors.toSet());
    }

    @GetMapping("/all")
    public Set<TurnoDTO> todosLosTurnos(){
        return turnoService.obtenerTodosLosTurnos().stream().map(TurnoDTO::desdeModelo).collect(Collectors.toSet());
    }
}
