package ar.edu.unq.spring.controller;

import ar.edu.unq.spring.service.impl.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/conmed/email")
public class EmailController {

    @Autowired
    private MailSenderService mailerSendService;

    @PostMapping("/enviar")
    public ResponseEntity<String> testMail() {
        try {
            mailerSendService.enviarCorreo("yaxotok443@frisbook.com", "Prueba de correo API", "Se envia correo desde backend");
            return ResponseEntity.ok("Correo enviado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Fallo: " + e.getMessage());
        }
    }

}

