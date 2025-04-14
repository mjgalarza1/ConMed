import {Button, Stack} from "react-bootstrap";
import {useNavigate} from "react-router-dom";

function LoggedInGreeting() {
    const navigate = useNavigate();

    return (
        <div className="d-flex flex-column justify-content-center align-items-center">
            <div className="d-flex flex-column justify-content-center align-items-center m-auto gap-4 text-center"
                 style={{
                     width: '50vw'
            }}>
                <h1>¿Qué acción deseas realizar?</h1>
                <Stack gap={2} className="m-auto">
                    <Button variant="outline-primary" size="lg" onClick={() => navigate("/medicosDisponibles")}>
                        Ver médicos disponibles
                    </Button>
                    <Button variant="outline-primary" size="lg" disabled>
                        Ver turnos reservados
                    </Button>
                </Stack>
            </div>
        </div>
    );
}

export default LoggedInGreeting;
