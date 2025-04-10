import axios from "axios";

const axiosService = axios.create({
    baseURL: 'http://localhost:8080',
    headers: {
        'Content-Type': 'application/json',
    },
});

export const greetings = () => {

    return axios.get('/', {})

}
