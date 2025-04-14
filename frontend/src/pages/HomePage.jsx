import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import AuthPrompt from "../components/basic/AuthPrompt.jsx";
import LoggedInGreeting from "../components/basic/HomeLoggedContent.jsx";

function HomePage() {
    const [estaLogueado, setEstaLogueado] = useState(false);
    const [cargando, setCargando] = useState(true);

    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem("token");
        setEstaLogueado(!!token);
        setCargando(false);
    }, []);

    return (
        <div id="HomePage" className="d-flex justify-content-center align-items-center" style={{minHeight: '100vh'}}>
            {cargando ? (
                <div className="text-center">
                    <div className="spinner-border text-primary" role="status">
                        <span className="visually-hidden">Cargando...</span>
                    </div>
                    <p className="mt-2">Verificando sesión...</p>
                </div>
            ) : (
                estaLogueado ? <LoggedInGreeting/> : <AuthPrompt/>
            )}
        </div>
    );
}

export default HomePage;
