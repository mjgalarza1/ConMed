package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.Role;
import ar.edu.unq.spring.modelo.Usuario;

public record UsuarioDTO(
        Long id,
        String dni,
        Role role
) {

public static UsuarioDTO desdeModelo(Usuario u) {
    return new UsuarioDTO(
            u.getIdUsuario(),
            u.getDni(),
            u.getRole()
    );
}

public Usuario aModelo() {
    Usuario usuario = new Usuario(this.dni, this.role);
    usuario.setIdUsuario(this.id);
    return usuario;
}
}
