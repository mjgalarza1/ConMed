import {useEffect, useState} from "react";
import {Button} from "react-bootstrap";
import {useNavigate} from "react-router-dom";

function HomePage() {

    const [estaLogueado, setEstaLogueado] = useState(false);

    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem("token");
        setEstaLogueado(!!token);
    }, []);

    return (
        <>
            {estaLogueado
                ?
                <div className="d-flex justify-content-center align-items-center text-center" style={{height: '100vh'}}>
                    <h1>
                        ¡Hola usuario logueado!
                    </h1>
                </div>
                :
                <div className="d-flex flex-column justify-content-center align-items-center text-center gap-4 m-auto" style={{height: '100vh', width: '400px'}}>
                    <h1>
                        Iniciar sesión o registrarse
                    </h1>
                    <div className="d-flex flex-column justify-content-center align-items-center gap-2" style={{width: '100%'}}>
                        <Button variant="outline-primary" size="lg" className="w-100" onClick={() => navigate("/login")}>
                            Iniciar sesión
                        </Button>
                        <Button variant="primary" size="lg" className="w-100" onClick={() => navigate("/register")}>
                            Registrarse
                        </Button>
                    </div>
                </div>
            }
        </>
    )
}

export default HomePage;
