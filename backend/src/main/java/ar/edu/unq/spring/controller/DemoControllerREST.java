package ar.edu.unq.spring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoControllerREST {

    // ESTO SOLO ESTÁ PARA DEMOSTRAR QUE FUNCIONA LA AUTORIZACIÓN A LOS ROLES
    // ¡NO OLVIDAR BORRAR!

    @GetMapping("/pacienteDemo")
    public ResponseEntity<String> pacienteDemo() {
        return ResponseEntity.ok("Esta página es visible porque ERES UN PACIENTE");
    }

    @GetMapping("/medicoDemo")
    public ResponseEntity<String> medicoDemo() {
        return ResponseEntity.ok("Esta página es visible porque ERES UN MEDICO");
    }

    @GetMapping("/adminDemo")
    public ResponseEntity<String> adminDemo() {
        return ResponseEntity.ok("Esta página es visible porque ERES UN ADMIN");
    }
}
