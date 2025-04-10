package ar.edu.unq.spring.controller;

import ar.edu.unq.spring.controller.dto.PacienteDTO;
import ar.edu.unq.spring.service.interfaces.PacienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/paciente")
public final class PacienteControllerREST { }