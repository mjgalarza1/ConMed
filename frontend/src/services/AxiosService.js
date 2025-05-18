import axios from "axios";

const axiosService = axios.create({
    baseURL: 'http://localhost:8080',
    headers: {
        'Content-Type': 'application/json',
    },
});

// USUARIOS

export const login = (dni, password) => {
    return axiosService
        .post('/login', { dni, password });
};

export const register = (nombre, dni, passwordPaciente, apellido, mail) => {
    return axiosService
        .post('/registrarPaciente', { nombre, dni, passwordPaciente, apellido, mail });
};

export const verMedicosDisponibles = () => {
    return axiosService.get('/pacientes/verMedicos', {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
        },
    });
};

export const verTurnosReservadosPorPaciente = (id_paciente) => {
    return axiosService.get(`/turnos/paciente/${id_paciente}/turnosReservados`, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
        },
    });
};

export const verTurnosDisponiblesDelMedico = (idMedico) => {
    return axiosService.get(`/turnos/medico/${idMedico}/turnosDisponibles`, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
        },
    });
};

export const getPacienteByDni = (dniPaciente) => {
    return axiosService.get(`/pacientes/dni/${dniPaciente}`, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
        },
    });
};

export const getMedicoByDni = (dniMedico) => {
    return axiosService.get(`/medicos/dni/${dniMedico}`, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
        },
    });
};

//Para futura implementación de admin
export const getAdministradorByDni = (dniAdministrador) => {
    return axiosService.get(`/administrador/dni/${dniAdministrador}`, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
        },
    });
};

export const getUsuarioByDni = (dniUsuario) => {
    return axiosService.get(`/usuario/${dniUsuario}`, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
        },
    });
};


export const reservarTurno = (idPaciente, idTurno) => {
    return axiosService.post(
        '/turnos/reservar',
        { idPaciente, idTurno },
        {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`,
            },
        }
    );
};

export const cancelarTurno = (idTurno) => {
    return axiosService.put(
        `/turnos/paciente/cancelar/${idTurno}`,
        {},
        {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`,
            },
        }
    );
};

export const agregarTurno = (medicoId, fecha, hora, disponibilidad) => {
    return axiosService.post(
        '/medicos/agregarTurno',
        { medicoId, fecha, hora, disponibilidad },
        {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`,
            },
        }
    );
};

export const getTurnosByDniMedico = (dniMedico) => {
    return axiosService.get(`/turnos/medico/${dniMedico}`, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
        },
    });
};

export const getAllMedicos = () => {
    return axiosService.get(`/administrador/todosLosMedicos`, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
        },
    });
};

export const agregarMedico = (nombre, apellido, dni, especialidad, matricula, passwordMedico) => {
    return axiosService.post(
        '/administrador/agregarMedico',
        { nombre, apellido, dni, especialidad, matricula, passwordMedico},
        {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`,
            },
        }
    );
};

export const deleteMedico = (id) => {
    return axiosService.delete(`/administrador/quitarMedico/${id}`,{
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
        },
    });
};

export const getMedicosPorEspecialidad = (especialidad) => {
    return axiosService.get(`/pacientes/medicosPorEspecialidad/${especialidad}`, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
        },
    });
};

export const getAllUsuarios = () => {
    return axiosService.get(`/administrador/todosLosUsuarios`, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
        },
    });
};

export const getAllMails = () => {
    return axiosService.get(`/pacientes/todosLosMails`, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
        },
    });
};

export const actualizarPerfil = (usuario) => {
    const { id, role } = usuario;

    let endpoint = "";
    switch (role) {
        case "PACIENTE":
            endpoint = `/pacientes/${id}`;
            break;
        case "MEDICO":
            endpoint = `/medicos/${id}`;
            break;
        case "ADMIN":
            endpoint = `/administrador/${id}`;
            break;
        default:
            throw new Error("Rol no soportado");
    }

    return axiosService.put(endpoint, usuario, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
        },
    });
};

