package ar.edu.unq.spring.controller;

import ar.edu.unq.spring.modelo.Paciente;
import ar.edu.unq.spring.service.impl.MailSenderService;
import ar.edu.unq.spring.service.interfaces.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ar.edu.unq.spring.service.impl.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/conmed/email")
public class EmailController {

    @Autowired
    private MailSenderService mailerSendService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private PasswordEncoder passwordEncoder;

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

@PostMapping("/enviarContrasenia")
public ResponseEntity<String> enviarPasswordTemporal(@RequestBody Map<String, String> body) {
    String email = body.get("email");

    if (email == null || email.isBlank()) {
        return ResponseEntity.badRequest().body("Formato de correo inválido");
    }

    Paciente paciente = pacienteService.recuperarPacientePorEmail(email);

    if (paciente == null) {
        return ResponseEntity.status(404).body("No se encontró una cuenta asociada a este correo");
    }

    String nuevaPassword = generarContraseniaTemporal();
    String hashedPassword = passwordEncoder.encode(nuevaPassword);
    paciente.setPassword(hashedPassword);
    pacienteService.actualizarPassword(paciente, hashedPassword);

    try {
        mailerSendService.enviarCorreo(email, "Contraseña temporal - ConMed", "Tu nueva contraseña temporal es: " + nuevaPassword + " Te recomendamos cambiarla al ingresar.");
        return ResponseEntity.ok("Se ha enviado una nueva contraseña a tu correo.");
    } catch (Exception e) {
        return ResponseEntity.status(500).body("Fallo: " + e.getMessage());
    }
}

private String generarContraseniaTemporal() {
    int length = 10;
    String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    String digits = "0123456789";
    StringBuilder sb = new StringBuilder();
    Random random = new Random();

    for (int i = 0; i < length - 1; i++) {
        sb.append(letters.charAt(random.nextInt(letters.length())));
    }

    char numero = digits.charAt(random.nextInt(digits.length()));
    int posicion = random.nextInt(sb.length() + 1);
    sb.insert(posicion, numero);

    return sb.toString();
}

}