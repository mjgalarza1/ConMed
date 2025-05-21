package ar.edu.unq.spring.service.impl;
import ar.edu.unq.spring.service.interfaces.PacienteService;
import ar.edu.unq.spring.service.interfaces.TurnoService;
import okhttp3.*;
import org.springframework.http.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
public class EmailService {

    private final String API_URL = "https://api.brevo.com/v3/smtp/email";
    private final String API_KEY = "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";

    private final OkHttpClient client = new OkHttpClient();
    private final PacienteService pacienteService;
    private final TurnoService turnoService;

    public EmailService(PacienteService pacienteService, TurnoService turnoService) {
        this.pacienteService = pacienteService;
        this.turnoService = turnoService;
    }

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

//    public void enviarReservaTurnoPaciente(Long idPaciente, Long idTurno) {
//        Paciente pacienteTurno = pacienteService.recuperarPacientePorId(idPaciente);
//        Turno turnoReservado = turnoService.obtenerTurnoById(idTurno);
//
//        enviarCorreo(pacienteTurno.getMail(), "Turno reservado exitosamente");
//
//
//    }
}
