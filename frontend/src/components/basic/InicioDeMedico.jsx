import {Button, Stack} from "react-bootstrap";
import {useNavigate} from "react-router-dom";

function InicioDeMedico() {
    const navigate = useNavigate();

    const handleVerTurnos = () => {
        navigate("/turnosDelMedico");
    };

    return (
        <div className="d-flex flex-column justify-content-center align-items-center">
            <div className="d-flex flex-column justify-content-center align-items-center m-auto gap-4 text-center"
                 style={{
                     width: '50vw'
            }}>
                <h1>¿Qué acción deseas realizar?</h1>
                <Stack gap={2} className="m-auto w-25">
                    <Button variant="outline-primary" size="lg" onClick={handleVerTurnos}>
                        Ver turnos
                    </Button>
                </Stack>
            </div>
        </div>
    );
}

export default InicioDeMedico;
