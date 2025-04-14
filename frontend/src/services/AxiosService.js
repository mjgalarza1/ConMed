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
