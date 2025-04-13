package ar.edu.unq.spring.controller;

import ar.edu.unq.spring.controller.dto.PacienteDTO;
import ar.edu.unq.spring.modelo.Paciente;
import ar.edu.unq.spring.service.interfaces.PacienteService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/pacientes")
public class PacienteControllerREST {

    private final PacienteService pacienteService;

    public PacienteControllerREST(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody PacienteDTO pacienteDTO) {
        Paciente nuevoPaciente = pacienteDTO.aModelo();
        pacienteService.guardarPaciente(nuevoPaciente);
        Paciente pacienteBD = pacienteService.recuperarPacientePorId(nuevoPaciente.getIdPaciente());
        return ResponseEntity.status(HttpStatus.CREATED).body(PacienteDTO.desdeModelo(pacienteBD));
    }

    @GetMapping
    public Set<PacienteDTO> obtenerTodos() {
        return pacienteService.allPacientes().stream().map(PacienteDTO::desdeModelo).collect(Collectors.toSet());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> buscarPorId(@PathVariable Long id) {
        Paciente paciente = this.pacienteService.recuperarPacientePorId(id);
        return ResponseEntity.ok(PacienteDTO.desdeModelo(paciente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody PacienteDTO pacienteDTO) {
        if (pacienteDTO.nombre() == null || pacienteDTO.nombre().trim().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Faltan campos obligatorios");
        }
        Paciente paciente = pacienteDTO.aModeloUpdate(id);
        pacienteService.actualizarPaciente(id, paciente);
        return ResponseEntity.status(HttpStatus.OK).body(paciente);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        pacienteService.eliminarPaciente(id);
    }
}
