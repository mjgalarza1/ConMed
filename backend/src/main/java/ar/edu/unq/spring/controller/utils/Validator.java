package ar.edu.unq.spring.controller.utils;

import ar.edu.unq.spring.controller.dto.CreatePacienteDTO;
import ar.edu.unq.spring.modelo.Usuario;

public class Validator {

    private static volatile Validator instance;
    private static final String NOMBRE_VACIO_O_NULO = "El nombre no puede estar vacio o nulo.";
    private static final String PATRON_DNI_ALFABETICO = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$";
    private static final String CONTRASENIA_VACIA_O_NULA = "La contraseña no puede estar vacia o nula.";
    private static final String CONTRASENIA_CANT_CARACTERES_MIN = "La contraseña debe tener un mínimo de 6 caracteres.";
    private static final String CONTRASENIA_NO_ALFANUMERICA = "La contraseña debe ser alfanumérica.";
    private static final String DNI_VACIO_O_NULO = "El DNI no puede estar vacio o nulo.";
    private static final String DNI_CANT_NUMEROS = "El DNI debe tener solo 8 números.";
    private static final String DNI_SOLO_ADMITE_NUMEROS = "El DNI debe contener solo números.";
    private static final String PATRON_CONTRASENIA_ALFANUMERICA = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$";
    private static final String PATRON_DNI_NUMERICO = "^\\d+$";

    private Validator() {}

    public static Validator getInstance() {
        if (instance == null) {
            synchronized (Validator.class) {
                if (instance == null) {
                    instance = new Validator();
                }
            }
        }
        return instance;
    }

    public void validarCreatePacienteDTO(CreatePacienteDTO createPacienteDTO) {
        if (createPacienteDTO.nombre() == null || createPacienteDTO.nombre().isBlank()) {
            throw new IllegalArgumentException(NOMBRE_VACIO_O_NULO);
        }
        if (!createPacienteDTO.nombre().matches(PATRON_DNI_ALFABETICO)) {
            throw new IllegalArgumentException("El nombre solo puede contener letras y espacios.");
        }
        if (createPacienteDTO.dni() == null || createPacienteDTO.dni().isBlank()) {
            throw new IllegalArgumentException(DNI_VACIO_O_NULO);
        }
        if (!createPacienteDTO.dni().matches(PATRON_DNI_NUMERICO)) {
            throw new IllegalArgumentException(DNI_SOLO_ADMITE_NUMEROS);
        }
        if (createPacienteDTO.dni().length() < 8) {
            throw new IllegalArgumentException(DNI_CANT_NUMEROS);
        }
        if (createPacienteDTO.dni().length() > 8) {
            throw new IllegalArgumentException(DNI_CANT_NUMEROS);
        }
        if (createPacienteDTO.passwordPaciente() == null || createPacienteDTO.passwordPaciente().isBlank()) {
            throw new IllegalArgumentException(CONTRASENIA_VACIA_O_NULA);
        }
        if (!createPacienteDTO.passwordPaciente().matches(PATRON_CONTRASENIA_ALFANUMERICA)) {
            throw new IllegalArgumentException(CONTRASENIA_NO_ALFANUMERICA);
        }
        if (createPacienteDTO.passwordPaciente().length() < 6) {
            throw new IllegalArgumentException(CONTRASENIA_CANT_CARACTERES_MIN);
        }
    }

    public void validarUsuarioLogin(Usuario usuario) {
        if (usuario.getDni() == null || usuario.getDni().isBlank()) {
            throw new IllegalArgumentException(DNI_VACIO_O_NULO);
        }
        if (!usuario.getDni().matches(PATRON_DNI_NUMERICO)) {
            throw new IllegalArgumentException(DNI_SOLO_ADMITE_NUMEROS);
        }
        if (usuario.getDni().length() < 8) {
            throw new IllegalArgumentException(DNI_CANT_NUMEROS);
        }
        if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
            throw new IllegalArgumentException(CONTRASENIA_VACIA_O_NULA);
        }
        if (!usuario.getPassword().matches(PATRON_CONTRASENIA_ALFANUMERICA)) {
            throw new IllegalArgumentException(CONTRASENIA_NO_ALFANUMERICA);
        }
        if (usuario.getPassword().length() < 6) {
            throw new IllegalArgumentException(CONTRASENIA_CANT_CARACTERES_MIN);
        }
    }
}
