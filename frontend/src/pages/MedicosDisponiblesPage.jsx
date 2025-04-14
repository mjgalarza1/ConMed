import {useEffect, useState} from "react";
import {Table, Container, Spinner, Alert, Button} from "react-bootstrap";
import {verMedicosDisponibles} from "../services/AxiosService.js";
import {useNavigate} from "react-router-dom";
import ConfirmarTurnoModal from "../components/modals/ConfirmarTurnoModal.jsx";
import ReservaExitosaToast from "../components/basic/ReservaExitosaToast.jsx";

const MedicosDisponiblesPage = () => {
    const [medicos, setMedicos] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [confirmarTurnoModalShow, setConfirmarTurnoModalShow] = useState(false);

    const [medicoSeleccionado, setMedicoSeleccionado] = useState(null);
    const [mostrarToast, setMostrarToast] = useState(false);

    const navigate = useNavigate();

    const handleVolver = () => {
        navigate("/");
    }

    const handleReservar = (id, nombre, apellido, matricula, especialidad) => {
        const medicoSeleccionado = {
            "id": id,
            "nombre": nombre,
            "apellido": apellido,
            "matricula": matricula,
            "especialidad": especialidad,
        }
        setMedicoSeleccionado(medicoSeleccionado)
        setConfirmarTurnoModalShow(true);
    }

    useEffect(() => {
        const fetchMedicos = async () => {
            try {
                const response = await verMedicosDisponibles();
                setMedicos(response.data);
            } catch (err) {
                setError("Error al obtener los médicos disponibles");
            } finally {
                setLoading(false);
            }
        };

        fetchMedicos();
    }, []);

    return (
        <>
        <ConfirmarTurnoModal
            medico={medicoSeleccionado}
            show={confirmarTurnoModalShow}
            onHide={() => setConfirmarTurnoModalShow(false)}
            onReservaExitosa={() => setMostrarToast(true)}
        />

        <ReservaExitosaToast
            mostrarToast={mostrarToast}
            setMostrarToast={setMostrarToast}
        />

        <Container className="mt-5">
            <h2 className="mb-4 text-center">Médicos Disponibles</h2>

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
                        <th>Nombre</th>
                        <th>N° de Matrícula</th>
                        <th>Especialidad</th>
                        <th>Turnos</th>
                    </tr>
                    </thead>
                    <tbody>
                    {medicos.map((medico, index) => (
                        <tr className="text-center" key={index}>
                            <td>{medico.nombre + " " + medico.apellido}</td>
                            <td>{medico.matricula}</td>
                            <td>{medico.especialidad}</td>
                            <td>
                                <Button variant="success" onClick={
                                    e => handleReservar(
                                        medico.id,
                                        medico.nombre,
                                        medico.apellido,
                                        medico.matricula,
                                        medico.especialidad
                                    )}
                                >
                                    Reservar
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
    );
};

export default MedicosDisponiblesPage;
