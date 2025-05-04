import {useEffect, useState} from "react";
import {Table, Container, Spinner, Alert, Button, Form} from "react-bootstrap";
import {verMedicosDisponibles, getMedicosPorEspecialidad} from "../services/AxiosService.js";
import {useNavigate} from "react-router-dom";
import ConfirmarTurnoModal from "../components/modals/ConfirmarTurnoModal.jsx";
import ConMedToast from "../components/basic/ConMedToast.jsx";
import { especialidadesLista } from "../data/especialidades"; // ⬅️ Asegurate de tener esta importación

const MedicosDisponiblesPage = () => {
    const [medicos, setMedicos] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [confirmarTurnoModalShow, setConfirmarTurnoModalShow] = useState(false);
    const [medicoSeleccionado, setMedicoSeleccionado] = useState(null);
    const [mostrarToast, setMostrarToast] = useState(false);
    const [especialidadSeleccionada, setEspecialidadSeleccionada] = useState("Todas"); // ⬅️ NUEVO

    const navigate = useNavigate();

    const handleVolver = () => {
        navigate("/");
    };

    const handleReservar = (id, nombre, apellido, matricula, especialidad) => {
        setMedicoSeleccionado({ id, nombre, apellido, matricula, especialidad });
        setConfirmarTurnoModalShow(true);
    };

    useEffect(() => {
        const fetchMedicos = async () => {
            try {
                setLoading(true);
                let response;

                if (especialidadSeleccionada === "Todas") {
                    response = await verMedicosDisponibles();
                } else {
                    response = await getMedicosPorEspecialidad(especialidadSeleccionada);
                }

                setMedicos(response.data);
                setError(null);
            } catch (err) {
                setError("Error al obtener los médicos");
            } finally {
                setLoading(false);
            }
        };

        fetchMedicos();
    }, [especialidadSeleccionada]);

    const medicosFiltrados =
        especialidadSeleccionada === "Todas"
            ? medicos
            : medicos.filter((medico) => medico.especialidad === especialidadSeleccionada);

    return (
        <>
            <ConfirmarTurnoModal
                medico={medicoSeleccionado}
                show={confirmarTurnoModalShow}
                onHide={() => setConfirmarTurnoModalShow(false)}
                onReservaExitosa={() => setMostrarToast(true)}
            />

            <ConMedToast
                mostrarToast={mostrarToast}
                setMostrarToast={setMostrarToast}
                titulo="Reserva exitosa"
                descripcion="Has reservado un turno"
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
                    <>
                        <div className="d-flex justify-content-between align-items-center mb-3">
                            <div>
                                <label htmlFor="especialidadSelect" className="me-2">Filtrar por especialidad:</label>
                                <Form.Select
                                    id="especialidadSelect"
                                    className="d-inline w-auto"
                                    value={especialidadSeleccionada}
                                    onChange={(e) => setEspecialidadSeleccionada(e.target.value)}
                                >
                                    <option value="Todas">Todas</option>
                                    {especialidadesLista.sort((a, b) => a.localeCompare(b)).map((esp, idx) => (
                                        <option key={idx} value={esp}>
                                            {esp}
                                        </option>
                                    ))}
                                </Form.Select>
                            </div>
                        </div>

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
                            {medicosFiltrados.map((medico, index) => (
                                <tr className="text-center" key={index}>
                                    <td>{medico.nombre + " " + medico.apellido}</td>
                                    <td>{medico.matricula}</td>
                                    <td>{medico.especialidad}</td>
                                    <td>
                                        <Button variant="success" onClick={() =>
                                            handleReservar(
                                                medico.id,
                                                medico.nombre,
                                                medico.apellido,
                                                medico.matricula,
                                                medico.especialidad
                                            )}>
                                            Reservar
                                        </Button>
                                    </td>
                                </tr>
                            ))}
                            </tbody>
                        </Table>
                    </>
                )}
                <Button variant="primary" className="w-100" onClick={handleVolver}>
                    Volver
                </Button>
            </Container>
        </>
    );
};

export default MedicosDisponiblesPage;
