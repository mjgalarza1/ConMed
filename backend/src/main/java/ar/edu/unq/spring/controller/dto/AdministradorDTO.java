package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.Administrador;
import ar.edu.unq.spring.modelo.Paciente;

public record AdministradorDTO(Long id, String nombre, String dni) {

    public static AdministradorDTO desdeModelo(Administrador administrador) {

        return new AdministradorDTO(administrador.getIdAdmin(), administrador.getNombre(), administrador.getDni());
    }

    public Administrador aModelo() {
        Administrador administrador = new Administrador(this.nombre, this.dni);
        administrador.setIdAdmin(this.id);
        return null;
    }
}
