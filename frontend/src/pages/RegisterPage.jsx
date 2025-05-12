import { useNavigate } from "react-router-dom"
import {getPacienteByDni, register} from "../services/AxiosService.js";
import RegisterForm from "../components/forms/RegisterForm/RegisterForm.jsx";

const RegisterPage = () => {
    const navigate = useNavigate();

    const handleSubmitRegistration = async (nombre, dni, password) => {
        try {
            const nombreCompleto = nombre.trim().split(" ");
            const soloApellido = nombreCompleto.pop();
            const soloNombre = nombreCompleto.join(" ");
            const response = await register(soloNombre, dni, password, soloApellido);
            localStorage.setItem("token", response.data.token);
            await handleDatosDeUsuario(dni);
            navigate("/");
            window.location.reload();
        } catch (error) {
            console.error("Error al registrarse:", error);
            alert(error.response.data);
        }
    };

    const handleDatosDeUsuario = async (dni) => {
        try {
            const response = await getPacienteByDni(dni);
            const nombreCompleto = response.data.nombre.trim().split(" ");
            const soloApellido = nombreCompleto.pop();
            const soloNombre = nombreCompleto.join(" ");
            const usuario = {
                id: response.data.id,
                dni: response.data.dni,
                nombre: soloNombre,
                apellido: soloApellido,
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
        <div id="RegisterPage" className="d-flex justify-content-center align-items-center flex-grow-1 h-100">
            <RegisterForm handleSubmitRegistration={handleSubmitRegistration}/>
        </div>
    );
}

export default RegisterPage;
