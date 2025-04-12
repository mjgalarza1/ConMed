package ar.edu.unq.spring.controller;

import ar.edu.unq.spring.controller.dto.CreatePacienteDTO;
import ar.edu.unq.spring.jwt.modelo.AuthenticationResponse;
import ar.edu.unq.spring.jwt.service.AuthenticationService;
import ar.edu.unq.spring.modelo.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationControllerREST {

    private final AuthenticationService authenticationService;

    public AuthenticationControllerREST(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/registrarPaciente")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody CreatePacienteDTO request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody Usuario request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
