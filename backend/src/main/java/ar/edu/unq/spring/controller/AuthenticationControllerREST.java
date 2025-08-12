package ar.edu.unq.spring.controller;

import ar.edu.unq.spring.controller.dto.CambiarContraseñaDTO;
import ar.edu.unq.spring.controller.dto.CreatePacienteDTO;
import ar.edu.unq.spring.controller.dto.UsuarioDTO;
import ar.edu.unq.spring.jwt.modelo.AuthenticationResponse;
import ar.edu.unq.spring.jwt.service.AuthenticationService;
import ar.edu.unq.spring.modelo.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.edu.unq.spring.controller.utils.Validator;

@RestController
public class AuthenticationControllerREST {

    private final AuthenticationService authenticationService;

    public AuthenticationControllerREST(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/registrarPaciente")
    public ResponseEntity<AuthenticationResponse> registrarPaciente(@RequestBody CreatePacienteDTO request) {
        Validator.getInstance().validarCreatePacienteDTO(request);
        return ResponseEntity.ok(authenticationService.registrarPaciente(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody Usuario request) {
        Validator.getInstance().validarUsuarioLogin(request);
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @GetMapping("/usuario/{dni}")
    public ResponseEntity<UsuarioDTO> obtenerPorDni(@PathVariable String dni) {
        Usuario usuario = this.authenticationService.recuperarUsuarioPorDni(dni);
        return ResponseEntity.ok(UsuarioDTO.desdeModelo(usuario));
    }

    @PostMapping("/usuario/cambiar-contraseña")
    public ResponseEntity<?> cambiarContraseña(@RequestBody CambiarContraseñaDTO dto) {
        authenticationService.cambiarContraseña(dto.dni(), dto.contraseñaActual(), dto.nuevaContraseña());
        return ResponseEntity.ok("Contraseña actualizada con éxito.");
    }

}
