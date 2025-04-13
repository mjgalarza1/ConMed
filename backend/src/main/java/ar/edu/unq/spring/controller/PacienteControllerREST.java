package ar.edu.unq.spring.controller;

import ar.edu.unq.spring.controller.dto.MedicoDTO;
import ar.edu.unq.spring.modelo.Medico;
import ar.edu.unq.spring.service.impl.MedicoServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paciente")
public final class PacienteControllerREST {

    private final MedicoServiceImpl medicoService;

    public PacienteControllerREST(MedicoServiceImpl medicoService) {
        this.medicoService = medicoService;
    }

    @GetMapping("/verMedicos")
    public ResponseEntity<List<MedicoDTO>> verMedicos() {
        List<Medico> medicos = medicoService.allMedicos();
        List<MedicoDTO> medicosDTO = medicos.stream().map(MedicoDTO::desdeModelo).toList();
        return ResponseEntity.ok(medicosDTO);
    }
}
