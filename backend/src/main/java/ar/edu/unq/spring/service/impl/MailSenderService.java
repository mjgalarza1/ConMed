package ar.edu.unq.spring.service.impl;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {

    @Value("${mailersend.api.token}")
    private String apiToken;

    @Value("${mailersend.from.email}")
    private String fromEmail;

    private final OkHttpClient client = new OkHttpClient();

    public void enviarCorreo(String to, String subject, String text) throws Exception {
        String json = """
            {
              "from": {
                "email": "%s"
              },
              "to": [
                {
                  "email": "%s"
                }
              ],
              "subject": "%s",
              "text": "%s"
            }
            """.formatted(fromEmail, to, subject, text);

        Request request = new Request.Builder()
                .url("https://api.mailersend.com/v1/email")
                .post(RequestBody.create(json, MediaType.parse("application/json")))
                .addHeader("Authorization", "Bearer " + apiToken)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Error al enviar email: " + response.code() + " - " + response.body().string());
            } else {
                System.out.println("✅ Correo enviado correctamente");
            }
        }
    }
}
