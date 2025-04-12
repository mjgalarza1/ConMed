package ar.edu.unq.spring.service.impl;

import ar.edu.unq.spring.modelo.Medico;
import ar.edu.unq.spring.persistence.MedicoDAO;
import ar.edu.unq.spring.persistence.PacienteDAO;
import ar.edu.unq.spring.service.interfaces.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MedicoServiceImpl implements MedicoService {

    @Autowired
    private MedicoDAO medicoDAO;

    @Override
    public List<Medico> allMedicos() {
        return medicoDAO.findAll();
    }
    @Override
    public Medico guardarMedico(Medico medico) {
        return medicoDAO.save(medico);
    }

    @Override
    public Medico recuperarMedico(Long medicoId) {
        return null;
    }

    @Override
    public void clearAll() {
    }
}