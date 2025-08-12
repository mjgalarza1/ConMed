package ar.edu.unq.spring.service.impl;
import ar.edu.unq.spring.modelo.Paciente;
import ar.edu.unq.spring.modelo.Turno;
import org.springframework.http.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
public class EmailService {

    private final String API_URL = "https://api.brevo.com/v3/smtp/email";
    private final String API_KEY = "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";

    public void enviarCorreo(String destinatario, String asunto, String contenidoHtml) {
        RestTemplate restTemplate = new RestTemplate();

        // Construcción del cuerpo del request
        Map<String, Object> body = new HashMap<>();

        Map<String, String> sender = new HashMap<>();
        sender.put("name", "ConMed");
        sender.put("email", "consultoriodevconmed@gmail.com");  // debe ser un email verificado en Brevo

        Map<String, String> to = new HashMap<>();
        to.put("email", destinatario);
        to.put("name", "Usuario");

        body.put("sender", sender);
        body.put("to", Collections.singletonList(to));
        body.put("subject", asunto);
        body.put("htmlContent", contenidoHtml);

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", API_KEY);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(API_URL, request, String.class);
            System.out.println("Respuesta de Brevo: " + response.getBody());
        } catch (Exception e) {
            System.err.println("Error al enviar el correo: " + e.getMessage());
        }
    }

    public void enviarEstadoTurnoPaciente(Paciente pacienteTurno, Turno turnoReservado, String estadoTurno) {

        String nombrePaciente = pacienteTurno.getNombre() + " " + pacienteTurno.getApellido();
        String nombreMedico = turnoReservado.getMedico().getNombre() + " " + turnoReservado.getMedico().getApellido();
        String especialidad = turnoReservado.getMedico().getEspecialidad();
        String fecha = turnoReservado.getFecha().toString();
        String hora = turnoReservado.getHora().toString();
        String estadoColor;
        String estadoTexto;
        String asunto;

        if(estadoTurno.equals("RESERVADO")){
            estadoColor = "green";
            estadoTexto = "RESERVADO";
            asunto = "Turno reservado exitosamente";
        }
        else{
            estadoColor = "red";
            estadoTexto = "CANCELADO";
            asunto = "Aviso turno cancelado";
        }

        String bodyEmail = String.format(
                "<html><body>" +
                        "<p><strong>Nombre del Paciente:</strong> %s</p>" +
                        "<p><strong>Nombre del Médico:</strong> %s</p>" +
                        "<p><strong>Especialidad:</strong> %s</p>" +
                        "<p><strong>Fecha:</strong> %s</p>" +
                        "<p><strong>Hora:</strong> %s</p>" +
                        "<p><strong>Estado:</strong> <span style='color: %s; font-weight: bold;'>%s</span></p>" +
                        "</body></html>",
                nombrePaciente,
                nombreMedico,
                especialidad,
                fecha,
                hora,
                estadoColor,
                estadoTexto
        );
        enviarCorreo(pacienteTurno.getMail(), asunto, bodyEmail);
    }
}
