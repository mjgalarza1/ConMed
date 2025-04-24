package ar.edu.unq.spring.service.impl;
import ar.edu.unq.spring.jwt.service.AuthenticationService;
import ar.edu.unq.spring.modelo.Administrador;
import ar.edu.unq.spring.modelo.Medico;
import ar.edu.unq.spring.modelo.Paciente;
import ar.edu.unq.spring.persistence.AdministradorDAO;
import ar.edu.unq.spring.service.interfaces.AdministradorService;
import ar.edu.unq.spring.service.interfaces.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class AdministradorServiceImpl implements AdministradorService {
    @Autowired
    private AdministradorDAO administradorDAO;
    @Autowired
    private MedicoService medicoService;
    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public List<Administrador> allAdministradores() {
        return administradorDAO.findAll();
    }

    @Override
    public Administrador guardarAdministrador(Administrador administrador) {
        return this.administradorDAO.save(administrador);
    }

    @Override
    public Administrador recuperarAdministrador(String dni) {
        if (dni == null || dni.isBlank())
            throw new RuntimeException("DNI invalido");
        return administradorDAO.findByDni(dni);
    }

    @Override
    public void clearAll() {
        administradorDAO.deleteAll();
    }

    @Override
    public Medico agregarMedico(Medico medico) {
        return medicoService.guardarMedico(medico);
    }

    @Override
    public void quitarMedico(Long medicoId) {
        medicoService.eliminarMedico(medicoId);
    }

    @Override
    public List<Medico> allMedicos() {
        return medicoService.allMedicos();
    }
}
