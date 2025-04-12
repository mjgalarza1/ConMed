package ar.edu.unq.spring.jwt.service;

import ar.edu.unq.spring.controller.dto.CreatePacienteDTO;
import ar.edu.unq.spring.jwt.modelo.AuthenticationResponse;
import ar.edu.unq.spring.modelo.Paciente;
import ar.edu.unq.spring.modelo.Role;
import ar.edu.unq.spring.modelo.Usuario;
import ar.edu.unq.spring.persistence.PacienteDAO;
import ar.edu.unq.spring.persistence.UsuarioDAO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UsuarioDAO usuarioDAO;
    private final PacienteDAO pacienteDAO;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UsuarioDAO usuarioDAO, PacienteDAO pacienteDAO, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.usuarioDAO = usuarioDAO;
        this.pacienteDAO = pacienteDAO;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(CreatePacienteDTO request) {
        // CREAR Y GUARDAR USUARIO EN BASE DE DATOS
        Usuario usuario = new Usuario();
        usuario.setDni(request.dni());
        usuario.setRole(Role.PACIENTE);
        String encodedPassword = passwordEncoder.encode(request.passwordPaciente());
        usuario.setPassword(encodedPassword);
        usuario = usuarioDAO.save(usuario);

        // CREAR Y GUARDAR PACIENTE EN BASE DE DATOS
        Paciente paciente = new Paciente(request.nombre(), request.dni(), encodedPassword);
        pacienteDAO.save(paciente);

        String token = jwtService.generateToken(usuario);
        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse authenticate(Usuario request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        Usuario usuario = usuarioDAO.findByDni(request.getDni());
        String token = jwtService.generateToken(usuario);

        return new AuthenticationResponse(token);
    }
}
