import { Button } from "react-bootstrap";
import { useNavigate } from "react-router-dom";

function InicioDeBienvenida() {
    const navigate = useNavigate();

    return (
        <div className="d-flex flex-column justify-content-center align-items-center text-center gap-4 m-auto h-100" style={{width: '400px'}}>
            <h1>
                    Bienvenidos a ConMed
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
    );
}

export default InicioDeBienvenida;
