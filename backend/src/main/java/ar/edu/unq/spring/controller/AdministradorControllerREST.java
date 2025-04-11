package ar.edu.unq.spring.controller;

import ar.edu.unq.spring.controller.dto.MedicoDTO;
import ar.edu.unq.spring.service.interfaces.AdministradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/administrador")
public final class AdministradorControllerREST {
    @Autowired
    private AdministradorService administradorService;

    @PostMapping("/agregarMedico")
    public void agregarMedico(@RequestBody MedicoDTO medico) {
        administradorService.agregarMedico(medico.aModelo());
    }
}