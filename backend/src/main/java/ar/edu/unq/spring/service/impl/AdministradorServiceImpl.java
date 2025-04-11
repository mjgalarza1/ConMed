package ar.edu.unq.spring.service.impl;
import ar.edu.unq.spring.modelo.Administrador;
import ar.edu.unq.spring.modelo.Medico;
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

    @Override
    public List<Administrador> allAdministradores() {
        return administradorDAO.findAll();
    }

    @Override
    public void guardarAdministrador(Administrador administrador) {
        this.administradorDAO.save(administrador);
    }

    @Override
    public Administrador recuperarAdministrador(Long administradorId) {
        return this.administradorDAO.findById(administradorId)
                .orElseThrow(() -> new NoSuchElementException("Administrador not found with id: " + administradorId));
    }

    @Override
    public void clearAll() {

    }

    @Override
    public Medico agregarMedico(Medico medico) {
        return medicoService.guardarMedico(medico);
    }
}
