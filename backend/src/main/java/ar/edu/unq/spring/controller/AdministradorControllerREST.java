package ar.edu.unq.spring.controller;

import ar.edu.unq.spring.controller.dto.AdministradorDTO;
import ar.edu.unq.spring.controller.dto.MedicoDTO;
import ar.edu.unq.spring.controller.dto.MedicoDTOAdmin;
import ar.edu.unq.spring.controller.dto.PacienteDTO;
import ar.edu.unq.spring.jwt.service.AuthenticationService;
import ar.edu.unq.spring.modelo.Administrador;
import ar.edu.unq.spring.modelo.Medico;
import ar.edu.unq.spring.modelo.Paciente;
import ar.edu.unq.spring.service.interfaces.AdministradorService;
import ar.edu.unq.spring.service.interfaces.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/administrador")
public final class AdministradorControllerREST {
    @Autowired
    private AdministradorService administradorService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private MedicoService medicoService;


    @PostMapping("/agregarMedico")
    public void agregarMedico(@RequestBody MedicoDTOAdmin medico) {
        authenticationService.registrarMedicoComoUsuario(medico.aModelo());
        administradorService.agregarMedico(medico.aModelo());
    }
    @DeleteMapping("/quitarMedico/{id}")
    public void quitarMedico(@PathVariable Long id) {
        Medico medicoAQuitar = medicoService.recuperarMedicoPorId(id);
        authenticationService.eliminarUsuario(medicoAQuitar.getDni());
        administradorService.quitarMedico(id);
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<AdministradorDTO> obtenerPorDni(@PathVariable String dni) {
        Administrador administrador = this.administradorService.recuperarAdministrador(dni);
        return ResponseEntity.ok(AdministradorDTO.desdeModelo(administrador));
    }
    @GetMapping("/todosLosMedicos")
    public Set<MedicoDTOAdmin> obtenerTodos() {
        return administradorService.allMedicos().stream().map(MedicoDTOAdmin::desdeModelo).collect(Collectors.toSet());
    }

}