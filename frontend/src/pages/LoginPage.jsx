import { useNavigate } from "react-router-dom"
import LoginForm from "../forms/LoginForm/LoginForm.jsx";
import {login} from "../services/AxiosService.js";

const LoginPage = () => {
    const navigate = useNavigate();

    const handleSubmitLogin = async (dni, password) => {
        try {
            const response = await login(dni, password);
            localStorage.setItem("token", response.data.token);
            navigate("/");
        } catch (error) {
            console.error("Error al iniciar sesión:", error);
            alert("Credenciales inválidas o error del servidor.");
        }
    };

    return (
        <div id="LoginPage" className="d-flex justify-content-center align-items-center" style={{ minHeight: '100vh' }}>
            <LoginForm handleSubmitLogin={handleSubmitLogin}/>
        </div>
    );
}

export default LoginPage;
