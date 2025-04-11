package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.Administrador;
import ar.edu.unq.spring.modelo.Paciente;

public record AdministradorDTO(Long id, String nombre) {

    public static AdministradorDTO desdeModelo(Administrador administrador) {

        return new AdministradorDTO(administrador.getId(), administrador.getNombre());
    }

    public Administrador aModelo() {
        Administrador administrador = new Administrador(this.nombre);
        administrador.setId(this.id);
        return null;
    }
}
