import { useEffect, useState} from "react";
import {Table, Container, Spinner, Alert, Button, Form} from "react-bootstrap";
import {
    verMedicosDisponibles,
    getMedicosPorEspecialidad, getEstaDisponibleElMedico
} from "../services/AxiosService.js";
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
    const [nombreSeleccionado , setNombreSeleccionado] = useState("");
    const [mostrarSoloDisponibles , setMostrarSoloDisponibles] = useState(false)

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
                //setLoading(true);
                let response;

                if (especialidadSeleccionada === "Todas") {
                    response = await verMedicosDisponibles();
                } else {
                    response = await getMedicosPorEspecialidad(especialidadSeleccionada);
                }

                const medicosConDisponibilidad = await Promise.all(
                    response.data.map(async (medico) => {
                        try {
                            const res = await getEstaDisponibleElMedico(medico.id);
                            return { ...medico, estaDisponible: res.data };
                        } catch (err) {
                            console.log("Error al obtener disponibilidad de", medico.nombre, err);
                            return { ...medico, estaDisponible: false };
                        }
                    })
                );

                setMedicos(medicosConDisponibilidad);
                setError(null);
            } catch (err) {
                setError("Error al obtener los médicos");
            } finally {
                //setLoading(false);
            }
        };

        fetchMedicos();
    }, [especialidadSeleccionada, mostrarSoloDisponibles]);

    useEffect(() => {
    }, [nombreSeleccionado]);

    const medicosFiltrados = medicos.filter((medico) => {
        const coincideEspecialidad =
            especialidadSeleccionada === "Todas" || medico.especialidad === especialidadSeleccionada;

        const coincideNombre =
            nombreSeleccionado.trim() === "" ||
            medico.nombre.toLowerCase().includes(nombreSeleccionado.toLowerCase());

        const coincideDisponibilidad = mostrarSoloDisponibles ? medico.estaDisponible : true;

        return coincideEspecialidad && coincideNombre && coincideDisponibilidad;
    });



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
                            <div className="d-flex align-items-center ">
                                <label className="me-2">Buscar por nombre:</label>
                                <Form.Control
                                    type="text"
                                    className="d-inline w-auto"
                                    id="inputPassword5"
                                    onChange={(e) => setNombreSeleccionado(e.target.value)}
                                />
                            </div>
                            <div>
                                <Form.Check
                                    type="checkbox"
                                    id="disponiblesCheckbox"
                                    label="Mostrar solo médicos disponibles"
                                    checked={mostrarSoloDisponibles}
                                    onChange={(e) => setMostrarSoloDisponibles(e.target.checked)}
                                />
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
