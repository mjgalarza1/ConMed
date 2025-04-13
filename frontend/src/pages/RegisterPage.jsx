import { useNavigate } from "react-router-dom"
import { register } from "../services/AxiosService.js";
import RegisterForm from "../forms/RegisterForm/RegisterForm.jsx";

const RegisterPage = () => {
    const navigate = useNavigate();

    const handleSubmitRegistration = async (nombre, dni, password) => {
        try {
            const response = await register(nombre, dni, password);
            localStorage.setItem("token", response.data.token);
            navigate("/");
        } catch (error) {
            console.error("Error al registrarse:", error);
            alert(error.response.data);
        }
    };

    return (
        <div id="RegisterPage" className="d-flex justify-content-center align-items-center" style={{ minHeight: '100vh' }}>
            <RegisterForm handleSubmitRegistration={handleSubmitRegistration}/>
        </div>
    );
}

export default RegisterPage;
