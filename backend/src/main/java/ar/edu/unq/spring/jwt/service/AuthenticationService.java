package ar.edu.unq.spring.jwt.service;

import ar.edu.unq.spring.jwt.modelo.AuthenticationResponse;
import ar.edu.unq.spring.modelo.Usuario;
import ar.edu.unq.spring.persistence.UsuarioDAO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UsuarioDAO usuarioDAO;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UsuarioDAO usuarioDAO, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.usuarioDAO = usuarioDAO;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(Usuario request) {
        Usuario usuario = new Usuario();
        usuario.setDni(request.getDni());
        usuario.setRole(request.getRole());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));

        usuario = usuarioDAO.save(usuario);

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
