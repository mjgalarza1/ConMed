package ar.edu.unq.spring.jwt.service;

import ar.edu.unq.spring.controller.dto.CreatePacienteDTO;
import ar.edu.unq.spring.controller.dto.UsuarioDTO;
import ar.edu.unq.spring.jwt.modelo.AuthenticationResponse;
import ar.edu.unq.spring.modelo.Medico;
import ar.edu.unq.spring.modelo.Paciente;
import ar.edu.unq.spring.modelo.Role;
import ar.edu.unq.spring.modelo.Usuario;
import ar.edu.unq.spring.persistence.PacienteDAO;
import ar.edu.unq.spring.persistence.UsuarioDAO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
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

    public AuthenticationResponse registrarPaciente(CreatePacienteDTO request) {
        if (!pacienteDAO.existsByMail(request.mail())){

        // CREAR Y GUARDAR USUARIO EN BASE DE DATOS
        Usuario usuario = new Usuario(request.dni(), Role.PACIENTE);
        String encodedPassword = passwordEncoder.encode(request.passwordPaciente());
        usuario.setPassword(encodedPassword);

        try {
            usuario = usuarioDAO.save(usuario);
        }
        catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("No se pudo registrar porque ya existe el DNI dado en sistema.");
        }

        // CREAR Y GUARDAR PACIENTE EN BASE DE DATOS
        Paciente paciente = new Paciente(request.nombre(), request.dni(), encodedPassword, request.apellido(), request.mail());
        pacienteDAO.save(paciente);

        String token = jwtService.generateToken(usuario);
        return new AuthenticationResponse(token);
        } else {
            throw new IllegalArgumentException("El mail ingresado ya existe. Por favor ingrese otro mail.");
        }
    }

    public AuthenticationResponse authenticate(Usuario request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        }
        catch (BadCredentialsException | InternalAuthenticationServiceException e) {
            throw new IllegalArgumentException("DNI o contraseña incorrectos.");
        }


        Usuario usuario = usuarioDAO.findByDni(request.getDni());
        String token = jwtService.generateToken(usuario);

        return new AuthenticationResponse(token);
    }

    public Usuario recuperarUsuarioPorDni(String dni) {
        if (dni == null || dni.isBlank())
            throw new RuntimeException("DNI invalido");
        Usuario usuario = usuarioDAO.findByDni(dni);
        return usuario;
    }

    public void registrarMedicoComoUsuario(Medico medico) {
        Usuario usuario = new Usuario(medico.getDni(), Role.MEDICO);
        String encodedPassword = passwordEncoder.encode(medico.getPasswordMedico());
        usuario.setPassword(encodedPassword);

        try {
            usuarioDAO.save(usuario);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("El DNI ya existe en el sistema.");
        }

    }

    public void eliminarUsuario(String dni) {
        if (dni == null) {
            throw new IllegalArgumentException("El DNI no puede ser nulo.");
        }

        Usuario usuario = usuarioDAO.findByDni(dni);

        if (usuario == null) {
            throw new IllegalArgumentException("No se encontró un usuario con el DNI especificado.");
        }

        usuarioDAO.delete(usuario);
    }

    public void cambiarContraseña(String dni, String contraseñaActual, String nuevaContraseña) {
        Usuario usuario = usuarioDAO.findByDni(dni);
        Paciente paciente = pacienteDAO.findByDni(dni);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado.");
        }

        if (!passwordEncoder.matches(contraseñaActual, usuario.getPassword())) {
            throw new IllegalArgumentException("La contraseña actual es incorrecta.");
        }

        if (passwordEncoder.matches(nuevaContraseña, usuario.getPassword())) {
            throw new IllegalArgumentException("La nueva contraseña no puede ser igual a la actual.");
        }

        usuario.setPassword(passwordEncoder.encode(nuevaContraseña));
        paciente.setPassword(passwordEncoder.encode(nuevaContraseña));
        usuarioDAO.save(usuario);
        pacienteDAO.save(paciente);
    }


}
