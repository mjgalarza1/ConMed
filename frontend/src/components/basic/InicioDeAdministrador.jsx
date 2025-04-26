import { useNavigate } from 'react-router-dom';
import { Button, Stack } from 'react-bootstrap';

function InicioDeAdministrador() {
    const navigate = useNavigate();

    // Función para manejar la navegación y verificar el token
    const handleVerMedicos = () => {
        const token = localStorage.getItem("token");

        if (!token) {
            localStorage.clear();  // Limpiar el localStorage
            window.location.reload();  // Recargar la página
            alert("Debe iniciar sesión");  // O cualquier otro mensaje de alerta
        } else {
            navigate("/todosLosMedicos");  // Si el token está presente, navegar a la página
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
                        Ver médicos
                    </Button>
                </Stack>
            </div>
        </div>
    );
}

export default InicioDeAdministrador;
