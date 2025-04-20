import { useNavigate } from "react-router-dom"
import {getPacienteByDni, register} from "../services/AxiosService.js";
import RegisterForm from "../components/forms/RegisterForm/RegisterForm.jsx";

const RegisterPage = () => {
    const navigate = useNavigate();

    const handleSubmitRegistration = async (nombre, dni, password) => {
        try {
            const response = await register(nombre, dni, password);
            localStorage.setItem("token", response.data.token);
            await handleDatosDeUsuario(dni);
            navigate("/");
        } catch (error) {
            console.error("Error al registrarse:", error);
            alert(error.response.data);
        }
    };

    const handleDatosDeUsuario = async (dni) => {
        try {
            const response = await getPacienteByDni(dni);
            const usuario = {
                id: response.data.id,
                dni: response.data.dni,
                nombre: response.data.nombre,
                role: "PACIENTE"
            };
            localStorage.setItem("usuario", JSON.stringify(usuario));
        } catch (error) {
            console.error("Error al iniciar sesión:", error);
            alert(error.response.data);
            localStorage.clear();
        }
    }

    return (
        <div id="RegisterPage" className="d-flex justify-content-center align-items-center" style={{ minHeight: '100vh' }}>
            <RegisterForm handleSubmitRegistration={handleSubmitRegistration}/>
        </div>
    );
}

export default RegisterPage;
