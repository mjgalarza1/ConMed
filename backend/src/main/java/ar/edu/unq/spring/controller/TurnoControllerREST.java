package ar.edu.unq.spring.controller;

import ar.edu.unq.spring.controller.dto.TurnoDTO;
import ar.edu.unq.spring.controller.dto.TurnoMedicoDTO;
import ar.edu.unq.spring.controller.dto.TurnoPacienteDTO;
import ar.edu.unq.spring.modelo.Medico;
import ar.edu.unq.spring.modelo.Paciente;
import ar.edu.unq.spring.modelo.Turno;
import ar.edu.unq.spring.service.interfaces.MedicoService;
import ar.edu.unq.spring.service.interfaces.PacienteService;
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
    private final MedicoService medicoService;
    private final PacienteService pacienteService;

    public TurnoControllerREST(TurnoService turnoService, MedicoService medicoService, PacienteService pacienteService){
        this.turnoService = turnoService;
        this.medicoService = medicoService;
        this.pacienteService = pacienteService;
    }

    @PostMapping("/{pacienteId}/reservar")
    public ResponseEntity<?> reservarTurno(@PathVariable Long pacienteId, @RequestBody TurnoDTO turnoDTO){
        Paciente pacienteBD = pacienteService.recuperarPacientePorId(pacienteId);
        Medico medicoBD = medicoService.recuperarMedicoPorId(turnoDTO.medicoId());

        Turno nuevoTurno = turnoDTO.aModelo(pacienteBD, medicoBD);
        turnoService.reservarTurno(pacienteId, nuevoTurno);
        return ResponseEntity.status(HttpStatus.CREATED).body(TurnoMedicoDTO.desdeModelo(nuevoTurno));
    }

    @GetMapping("/paciente/{id_paciente}")
    public Set<TurnoPacienteDTO> turnosDelPaciente(@PathVariable Long id_paciente){
        return turnoService.obtenerTurnoByPaciente(id_paciente).stream().map(TurnoPacienteDTO::desdeModelo).collect(Collectors.toSet());
    }

    @GetMapping("/medico/{id_medico}")
    public Set<TurnoMedicoDTO> turnosDelMedico(@PathVariable Long id_medico){
        return turnoService.obtenerTurnoByMedico(id_medico).stream().map(TurnoMedicoDTO::desdeModeloMedico).collect(Collectors.toSet());
    }

    @GetMapping("/all")
    public Set<TurnoMedicoDTO> todosLosTurnos(){
        return turnoService.obtenerTodosLosTurnos().stream().map(TurnoMedicoDTO::desdeModelo).collect(Collectors.toSet());
    }
}
