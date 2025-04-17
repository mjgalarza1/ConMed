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

export const register = (nombre, dni, passwordPaciente) => {
    return axiosService
        .post('/registrarPaciente', { nombre, dni, passwordPaciente });
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

export const getTurnosByDniMedico = (dniMedico) => {
    return axiosService.get(`/turnos/medico/${dniMedico}`, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
        },
    });
};


