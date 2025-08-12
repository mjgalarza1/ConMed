import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import {cancelarTurno, verTurnosReservadosPorPaciente} from "../services/AxiosService.js";
import {Alert, Button, Container, Spinner, Table} from "react-bootstrap";
import ConMedToast from "../components/basic/ConMedToast.jsx";

const ReservasDeTurnosPage = () => {
    const [turnos, setTurnos] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [mostrarCancelarTurnoToast, setMostrarCancelarTurnoToast] = useState(false);

    const navigate = useNavigate();

    const handleVolver = () => {
        navigate("/");
    }

    const handleCancelarTurno = (idTurno) => {
        const confirmacion = window.confirm("¿Estás seguro de cancelar el turno?");
        if (confirmacion) {
            handleConfirmarCancelarTurno(idTurno);
        }
    }

    const handleConfirmarCancelarTurno = async (idTurno) => {
        try {
            await cancelarTurno(idTurno);
            setMostrarCancelarTurnoToast(true);
        } catch (e) {
            if (e.response && (e.response.status === 401 || e.response.status === 403)) {
                alert("Debe iniciar sesión")
                localStorage.clear();
                navigate("/");
            } else {
                setError("Error al cancelar el turno:" + e);
            }
        } finally {
            setLoading(false);
        }
    }

    useEffect(() => {
        const fetchTurnosReservados = async () => {
            try {
                const paciente_id = JSON.parse(localStorage.getItem("usuario"))?.id;
                const response = await verTurnosReservadosPorPaciente(paciente_id);
                setTurnos(response.data);
            } catch (err) {
                setError("Error al obtener los turnos reservados");
            } finally {
                setLoading(false);
            }
        };

        fetchTurnosReservados();
    }, [mostrarCancelarTurnoToast]);

    return (
        <>
            <ConMedToast
                mostrarToast={mostrarCancelarTurnoToast}
                setMostrarToast={setMostrarCancelarTurnoToast}
                titulo= "Turno cancelado exitosamente"
                descripcion= "Has cancelado un turno"
            />

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
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        {turnos.map((turnos, index) => (
                            <tr className="text-center" key={index}>
                                <td>{turnos.fecha}</td>
                                <td>{turnos.hora}</td>
                                <td>{turnos.nombreMedico}</td>
                                <td>{turnos.especialidad}</td>
                                <td>
                                    <Button variant="danger" onClick={() => handleCancelarTurno(turnos.id)}>
                                        Cancelar turno
                                    </Button>
                                </td>
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
