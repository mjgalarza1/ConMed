import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import {verTurnosReservadosPorPaciente} from "../services/AxiosService.js";
import {Alert, Button, Container, Spinner, Table} from "react-bootstrap";

const ReservasDeTurnosPage = () => {
    const [turnos, setTurnos] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const navigate = useNavigate();

    const handleVolver = () => {
        navigate("/");
    }

    useEffect(() => {
        const fetchTurnosReservados = async () => {
            try {
                const paciente_id = JSON.parse(localStorage.getItem("usuario"))?.id;
                const response = await verTurnosReservadosPorPaciente(paciente_id);
                setTurnos(response.data);
            } catch (err) {
                setError("Error al obtener los médicos disponibles");
            } finally {
                setLoading(false);
            }
        };

        fetchTurnosReservados();
    }, []);

    return (
        <>
            <Container className="mt-5">
                <h2 className="mb-4 text-center">Turnos reservados</h2>

                {loading && (
                    <div className="d-flex justify-content-center">
                        <Spinner animation="border" variant="primary"/>
                    </div>
                )}

                {error && <Alert variant="danger">{error}</Alert>}

                {!loading && !error && (
                    <Table striped bordered hover responsive>
                        <thead>
                        <tr className="text-center">
                            <th>Fecha</th>
                            <th>Hora</th>
                            <th>Nombre del médico</th>
                            <th>Especialidad</th>
                        </tr>
                        </thead>
                        <tbody>
                        {turnos.map((turnos, index) => (
                            <tr className="text-center" key={index}>
                                <td>{turnos.fecha}</td>
                                <td>{turnos.hora}</td>
                                <td>{turnos.nombreMedico}</td>
                                <td>{turnos.especialidad}</td>
                            </tr>
                        ))}
                        </tbody>
                    </Table>
                )}
                <Button variant="primary" className="w-100" onClick={handleVolver}>
                    Volver
                </Button>
            </Container>
        </>
    )
}

export default ReservasDeTurnosPage;
