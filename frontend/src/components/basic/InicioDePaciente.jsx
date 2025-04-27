import {Button, Stack} from "react-bootstrap";
import InicioDeBienvenida from "./InicioDeBienvenida.jsx";
import {useNavigate} from "react-router-dom";

function InicioDePaciente({setEstaLogueado}) {
    const navigate = useNavigate();
    const estaLogueado = () => !!localStorage.getItem("token")

    return (
        <div className="d-flex flex-column justify-content-center align-items-center">
            <div className="d-flex flex-column justify-content-center align-items-center m-auto gap-4 text-center"
                 style={{
                     width: '50vw'
            }}>
                <h1>¿Qué acción deseas realizar?</h1>
                <Stack gap={2} className="m-auto">
                    <Button variant="outline-primary" size="lg" onClick={() =>
                        {if(estaLogueado()){
                            navigate("/medicosDisponibles")
                        }
                        else{
                            setEstaLogueado(false)
                        }}}>
                        Ver médicos disponibles
                    </Button>
                    <Button variant="outline-primary" size="lg" onClick={() =>
                    {if(estaLogueado()){
                        navigate("/reservasDeTurnos")
                    }
                    else{
                        setEstaLogueado(false)
                    }}}>
                        Ver turnos reservados
                    </Button>
                </Stack>
            </div>
        </div>
    );
}

export default InicioDePaciente;
