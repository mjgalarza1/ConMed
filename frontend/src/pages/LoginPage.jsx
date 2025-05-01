import { useNavigate } from "react-router-dom"
import LoginForm from "../components/forms/LoginForm/LoginForm.jsx";
import {
    login,
    getPacienteByDni,
    getUsuarioByDni,
    getMedicoByDni,
    getAdministradorByDni
} from "../services/AxiosService.js";

const LoginPage = () => {
    const navigate = useNavigate();

    const handleSubmitLogin = async (dni, password) => {
        try {
            const response = await login(dni, password);
            localStorage.setItem("token", response.data.token);
            await handleDatosDeUsuario(dni);
            navigate("/");
        } catch (error) {
            console.error("Error al iniciar sesión:", error);
            alert(error.response.data);
        }
    };

    const handleDatosDeUsuario = async (dni) => {
        try {
            const response = await getUsuarioByDni(dni);

            let responseRole
            switch (response.data.role) {
                case "PACIENTE":
                    responseRole = await getPacienteByDni(dni)
                    break;
                case "MEDICO":
                    responseRole = await getMedicoByDni(dni)
                    break;
                case "ADMIN":
                responseRole = await getAdministradorByDni(dni)
                break;
            }

            const usuario = {
                id: responseRole.data.id,
                dni: response.data.dni,
                nombre: responseRole.data.nombre,
                role: response.data.role,
            };
            localStorage.setItem("usuario", JSON.stringify(usuario));
        } catch (error) {
            console.error("Error al iniciar sesión:", error);
            alert(error.response.data);
            localStorage.clear();
        }
    }

    return (
        <div id="LoginPage" className="d-flex justify-content-center align-items-center flex-grow-1 h-100">
            <LoginForm handleSubmitLogin={handleSubmitLogin}/>
        </div>
    );
}

export default LoginPage;
