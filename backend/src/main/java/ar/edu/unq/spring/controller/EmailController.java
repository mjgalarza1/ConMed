package ar.edu.unq.spring.controller;

import ar.edu.unq.spring.modelo.EmailRequest;
import ar.edu.unq.spring.service.impl.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/conmed/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/enviar")
    public String enviarCorreoPost(@RequestBody EmailRequest request) {
        emailService.enviarCorreo(request.getDestinatario(), request.getAsunto(), request.getMensaje());
        return "Correo enviado a " + request.getDestinatario();
    }

}

