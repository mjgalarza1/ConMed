import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import {getTurnosByDniMedico} from "../services/AxiosService.js";
import {Alert, Button, Container, Spinner, Table} from "react-bootstrap";

const TurnosDelMedicoPage = () => {
    const [turnos, setTurnos] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const navigate = useNavigate();

    const handleVolver = () => {
        navigate("/");
    }

    useEffect(() => {
        const fetchTurnosMedico = async () => {
            try {
                const user_dni = JSON.parse(localStorage.getItem("usuario"))?.dni;
                const response = await getTurnosByDniMedico(user_dni);
                setTurnos(response.data);
            } catch (err) {
                setError("Error al obtener los turnos del médico");
            } finally {
                setLoading(false);
            }
        };

        fetchTurnosMedico();
    }, []);

    return (
        <>
            <Container className="mt-5">
                <h2 className="mb-4 text-center">Turnos del Médico</h2>

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
                            <th>Nombre del paciente</th>
                            <th>Disponibilidad</th>
                        </tr>
                        </thead>
                        <tbody>
                        {turnos.map((turnos, index) => (
                            <tr className="text-center" key={index}>
                                <td>{turnos.fecha}</td>
                                <td>{turnos.hora}</td>
                                <td>{turnos.nombrePaciente}</td>
                                <td>{turnos.disponibilidad}</td>
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

export default TurnosDelMedicoPage;
