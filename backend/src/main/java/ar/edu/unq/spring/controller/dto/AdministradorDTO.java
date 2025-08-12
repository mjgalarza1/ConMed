package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.Administrador;
import ar.edu.unq.spring.modelo.Paciente;

public record AdministradorDTO(Long id, String nombre, String apellido,String dni) {

    public static AdministradorDTO desdeModelo(Administrador administrador) {

        return new AdministradorDTO(administrador.getIdAdmin(), administrador.getNombre(), administrador.getApellido(),administrador.getDni());
    }

    public Administrador aModelo() {
        Administrador administrador = new Administrador(this.nombre, this.apellido,this.dni);
        administrador.setIdAdmin(this.id);
        return null;
    }

    public Administrador aModeloUpdate(Long id) {
        Administrador administrador = new Administrador();
        administrador.setIdAdmin(id);
        administrador.setNombre(this.nombre);
        administrador.setApellido(this.apellido);

        return administrador;
    }
}
