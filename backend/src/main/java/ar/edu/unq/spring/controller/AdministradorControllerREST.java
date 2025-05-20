package ar.edu.unq.spring.controller;

import ar.edu.unq.spring.controller.dto.*;
import ar.edu.unq.spring.jwt.service.AuthenticationService;
import ar.edu.unq.spring.modelo.Administrador;
import ar.edu.unq.spring.modelo.Medico;
import ar.edu.unq.spring.modelo.Paciente;
import ar.edu.unq.spring.modelo.Role;
import ar.edu.unq.spring.service.interfaces.AdministradorService;
import ar.edu.unq.spring.service.interfaces.MedicoService;
import ar.edu.unq.spring.service.interfaces.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
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
    @Autowired
    private PacienteService pacienteService;



    @PostMapping("/agregarMedico")
    public void agregarMedico(@RequestBody MedicoDTOAdmin medico) {
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

    @GetMapping("/medicosPorEspecialidad/{especialidad}")
    public Set<MedicoDTO> obtenerMedicosPorEspecialidad(@PathVariable String especialidad) {
        return medicoService.allMedicos().stream().filter(medico -> medico.getEspecialidad().equalsIgnoreCase(especialidad)).map(MedicoDTO::desdeModelo).collect(Collectors.toSet());
    }

    @GetMapping("/todosLosUsuarios")
    public List<UsuarioDTOAdmin> obtenerTodosLosUsuarios() {
        List<UsuarioDTOAdmin> todos = new ArrayList<>();

        todos.addAll(
                administradorService.allAdministradores().stream()
                        .map(admin -> new UsuarioDTOAdmin(admin.getIdAdmin(), admin.getDni(), admin.getNombre() + " " + admin.getApellido(), Role.ADMIN))
                        .toList()
        );

        todos.addAll(
                medicoService.allMedicos().stream()
                        .map(medico -> new UsuarioDTOAdmin(medico.getIdMedico(), medico.getDni(), medico.getNombre() + " " + medico.getApellido(), Role.MEDICO))
                        .toList()
        );

        todos.addAll(
                pacienteService.allPacientes().stream()
                        .map(paciente -> new UsuarioDTOAdmin(paciente.getIdPaciente(), paciente.getDni(), paciente.getNombre() + " " + paciente.getApellido(), Role.PACIENTE))
                        .toList()
        );

        return todos;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody AdministradorDTO adminDTO) {
        if (adminDTO.nombre() == null || adminDTO.nombre().trim().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Faltan campos obligatorios");
        }
        Administrador administrador = adminDTO.aModeloUpdate(id);
        administradorService.actualizarAdministrador(id, administrador);
        return ResponseEntity.status(HttpStatus.OK).body(administrador);
    }

}