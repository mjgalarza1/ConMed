import { useNavigate } from 'react-router-dom';
import { Button, Stack } from 'react-bootstrap';

function InicioDeAdministrador() {
    const navigate = useNavigate();

    const handleVerMedicos = () => {
        const token = localStorage.getItem("token");

        if (!token) {
            localStorage.clear();
            window.location.reload();
            alert("Debe iniciar sesión");
        } else {
            navigate("/todosLosMedicos");
        }
    };

    const handleVerUsuarios = () => {
        const token = localStorage.getItem("token");

        if (!token) {
            localStorage.clear();
            window.location.reload();
            alert("Debe iniciar sesión");
        } else {
            navigate("/todosLosUsuarios");
        }
    };

    return (
        <div className="d-flex flex-column justify-content-center align-items-center">
            <div className="d-flex flex-column justify-content-center align-items-center m-auto gap-4 text-center"
                 style={{
                     width: '50vw'
                 }}>
                <h1>¿Qué acción deseas realizar?</h1>
                <Stack gap={2} className="m-auto">
                    <Button variant="outline-primary" size="lg" onClick={handleVerMedicos}>
                        Gestionar médicos
                    </Button>
                    <Button variant="outline-primary" size="lg" onClick={handleVerUsuarios}>
                        Ver todos los usuarios
                    </Button>
                </Stack>
            </div>
        </div>
    );
}



export default InicioDeAdministrador;
