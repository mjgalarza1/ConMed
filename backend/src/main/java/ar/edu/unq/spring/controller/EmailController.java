package ar.edu.unq.spring.controller;

import ar.edu.unq.spring.service.impl.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/conmed/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/enviar")
    public ResponseEntity<String> enviarCorreo() {
        emailService.enviarCorreo(
                "tresesodola-1497@yopmail.com",
                "Turno Confirmado",
                "<h1>Tu turno ha sido confirmado</h1><p>Gracias por usar ConMed</p>"
        );
        return ResponseEntity.ok("Correo enviado");
    }
}


