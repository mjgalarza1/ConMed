import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import InicioDeBienvenida from "../components/basic/InicioDeBienvenida.jsx";
import InicioDePaciente from "../components/basic/InicioDePaciente.jsx";
import InicioDeMedico from "../components/basic/InicioDeMedico.jsx";

function HomePage() {
    const [estaLogueado, setEstaLogueado] = useState(false);
    const [cargando, setCargando] = useState(true);
    const [role, setRole] = useState("");

    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem("token");

        const usuarioRole = JSON.parse(localStorage.getItem("usuario"))?.role;
        setRole(usuarioRole);
        setEstaLogueado(!!token);
        setCargando(false);
    }, []);

    const inicioDeRol = () => {
        if (estaLogueado) {
            console.log(role)
            if (role === "PACIENTE") {
                return <InicioDePaciente/>;
            } else if (role === "MEDICO") {
                return <InicioDeMedico/>;
            }
        }else{
            return <InicioDeBienvenida/>;
        }
    }

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
                inicioDeRol()
            )}
        </div>
    );
}

export default HomePage;
