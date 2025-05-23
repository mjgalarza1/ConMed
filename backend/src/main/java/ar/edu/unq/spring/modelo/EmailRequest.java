package ar.edu.unq.spring.modelo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class EmailRequest {
        private String destinatario;
        private String asunto;
        private String mensaje;
}
