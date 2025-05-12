package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.Role;
import ar.edu.unq.spring.modelo.Usuario;

public record UsuarioDTOAdmin(
    Long idUsuario,
    String dni,
    String nombreCompleto,
    Role role
){

    public Usuario aModelo() {
        Usuario usuario = new Usuario(this.dni, this.role);
        usuario.setIdUsuario(this.idUsuario);
        return usuario;
    }

}
