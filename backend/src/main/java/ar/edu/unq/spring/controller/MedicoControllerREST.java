package ar.edu.unq.spring.controller;

import ar.edu.unq.spring.controller.dto.MedicoDTO;
import ar.edu.unq.spring.controller.dto.TurnoDTO;
import ar.edu.unq.spring.modelo.Medico;
import ar.edu.unq.spring.modelo.Turno;
import ar.edu.unq.spring.service.interfaces.MedicoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/medicos")
public class MedicoControllerREST {

    private final MedicoService medicoService;

    @Autowired
    public MedicoControllerREST(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody MedicoDTO medicoDTO) {
        Medico nuevoMedico = medicoDTO.aModelo();
        medicoService.guardarMedico(nuevoMedico);
        return ResponseEntity.status(HttpStatus.CREATED).body(MedicoDTO.desdeModelo(nuevoMedico));
    }

    @GetMapping
    public Set<MedicoDTO> obtenerTodos() {
        return medicoService.allMedicos().stream().map(MedicoDTO::desdeModelo).collect(Collectors.toSet());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicoDTO> buscarPorId(@PathVariable Long id) {
        Medico medico = this.medicoService.recuperarMedicoPorId(id);
        return ResponseEntity.ok(MedicoDTO.desdeModelo(medico));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody MedicoDTO medicoDTO) {
        if (medicoDTO.nombre() == null || medicoDTO.nombre().trim().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Faltan campos obligatorios");
        }
        Medico medico = medicoDTO.aModeloUpdate(id);
        medicoService.actualizarMedico(id, medico);
        return ResponseEntity.status(HttpStatus.OK).body(medico);
    }

    @PostMapping("/agregarTurno")
    public ResponseEntity<?> agregarTurno(@RequestBody TurnoDTO turnoDTO) {
        Medico medico = medicoService.recuperarMedicoPorId(turnoDTO.medicoId());
        TurnoDTO nuevoTurno = medicoService.agregarTurno(turnoDTO.aModelo(medico));
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoTurno);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        medicoService.eliminarMedico(id);
    }
}
