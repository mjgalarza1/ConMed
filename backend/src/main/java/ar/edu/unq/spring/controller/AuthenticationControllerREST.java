package ar.edu.unq.spring.controller;

import ar.edu.unq.spring.controller.dto.CreatePacienteDTO;
import ar.edu.unq.spring.jwt.modelo.AuthenticationResponse;
import ar.edu.unq.spring.jwt.service.AuthenticationService;
import ar.edu.unq.spring.modelo.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
