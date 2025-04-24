package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.Medico;

public record MedicoDTOAdmin(
        Long idMedico,
        String nombre,
        String apellido,
        String dni,
        String especialidad,
        String matricula,
        String passwordMedico
) {
    public MedicoDTOAdmin {
        // Validar nombre
        if (!nombre.matches("^[A-Za-z]+( [A-Za-z]+)?$")) {
            throw new IllegalArgumentException("Nombre inválido. Solo se permite un espacio y letras.");
        }

        // Validar apellido
        if (!apellido.matches("^[A-Za-z]+( [A-Za-z]+)?$")) {
            throw new IllegalArgumentException("Apellido inválido. Solo se permite un espacio y letras.");
        }

        // Validar especialidad
        especialidad = especialidad.trim();
        if (!especialidad.matches("^[A-Za-zÑñ ]+$")) {
            throw new IllegalArgumentException("Especialidad inválida. Solo se permiten letras y espacios.");
        }

        // Validar DNI
        if (!dni.matches("^\\d{8}$")) {
            throw new IllegalArgumentException("DNI inválido. Debe tener exactamente 8 números.");
        }

        // Validar matrícula
        if (!matricula.matches("^MD-\\d{6}$")) {
            throw new IllegalArgumentException("Matrícula inválida. Debe tener formato MD-xxxxxx (6 dígitos).");
        }
    }

    public static MedicoDTOAdmin desdeModelo(Medico medico) {
        return new MedicoDTOAdmin(
                medico.getIdMedico(),
                medico.getNombre(),
                medico.getApellido(),
                medico.getDni(),
                medico.getEspecialidad(),
                medico.getMatricula(),
                medico.getPasswordMedico()
        );
    }

    public Medico aModelo() {
        Medico medico = new Medico();
        medico.setIdMedico(this.idMedico);
        medico.setNombre(this.nombre);
        medico.setApellido(this.apellido);
        medico.setDni(this.dni);
        medico.setEspecialidad(this.especialidad);
        medico.setMatricula(this.matricula);
        medico.setPasswordMedico(this.passwordMedico);

        return medico;
    }
}
