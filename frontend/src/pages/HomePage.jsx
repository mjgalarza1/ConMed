import {useEffect, useState} from "react";
import InicioDeBienvenida from "../components/basic/InicioDeBienvenida.jsx";
import InicioDePaciente from "../components/basic/InicioDePaciente.jsx";
import InicioDeMedico from "../components/basic/InicioDeMedico.jsx";
import InicioDeAdministrador from "../components/basic/InicioDeAdministrador.jsx";

function HomePage() {
    const [estaLogueado, setEstaLogueado] = useState(false);
    const [cargando, setCargando] = useState(true);
    const [role, setRole] = useState("");

    useEffect(() => {
        const token = localStorage.getItem("token");

        const usuarioRole = JSON.parse(localStorage.getItem("usuario"))?.role;
        setRole(usuarioRole);
        setEstaLogueado(!!token);
        setCargando(false);
    }, [estaLogueado]);

    const inicioDeRol = () => {
        if (estaLogueado) {
            if (role === "PACIENTE") {
                return <InicioDePaciente setEstaLogueado={setEstaLogueado}/>;
            } else if (role === "MEDICO") {
                return <InicioDeMedico/>;
            } else if (role === "ADMIN") {
                return <InicioDeAdministrador/>;
            }
        } else {
            return <InicioDeBienvenida/>;
        }
    }

    return (
        <div id="HomePage" className="d-flex justify-content-center align-items-center flex-grow-1 h-100">
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
