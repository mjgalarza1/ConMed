import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { getAllMedicos, deleteMedico } from "../services/AxiosService.js";
import {Alert,Button,Container,Spinner,Table,Form} from "react-bootstrap";
import ConMedToast from "../components/basic/ConMedToast.jsx";
import AgregarMedicoModal from "../components/modals/AgregarMedicoModal.jsx";
import { especialidadesLista } from "../data/especialidades";

const TodosLosMedicosPage = () => {
    const [medicos, setMedicos] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [agregarMedicoModalShow, setAgregarMedicoModalShow] = useState(false);
    const [mostrarToast, setMostrarToast] = useState(false);
    const [mostrarEliminadoToast, setMostrarEliminadoToast] = useState(false);
    const [especialidadSeleccionada, setEspecialidadSeleccionada] = useState("Todas");

    const navigate = useNavigate();

    const handleVolver = () => {
        navigate("/");
    };

    const handleAgregarMedico = () => {
        const token = localStorage.getItem("token");
        if (!token) {
            alert("Debe iniciar sesión");
            localStorage.clear();
            navigate("/");
        } else {
            setAgregarMedicoModalShow(true);
        }
    };

    const handleEliminarMedico = async (medicoId) => {
        try {
            await deleteMedico(medicoId);
            setMostrarEliminadoToast(true);
            setMedicos(medicos.filter((medico) => medico.id !== medicoId));
        } catch (err) {
            if (err.response && (err.response.status === 401 || err.response.status === 403)) {
                alert("Debe iniciar sesión");
                localStorage.clear();
                navigate("/");
            } else {
                setError("Error al eliminar el médico");
            }
        }
    };

    useEffect(() => {
        const fetchTodosLosMedicos = async () => {
            try {
                const response = await getAllMedicos();
                setMedicos(response.data);
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };

        fetchTodosLosMedicos();
    }, [mostrarToast, mostrarEliminadoToast]);

    const medicosFiltrados =
        especialidadSeleccionada === "Todas"
            ? medicos
            : medicos.filter(
                (medico) => medico.especialidad === especialidadSeleccionada
            );

    return (
        <>
            <AgregarMedicoModal
                show={agregarMedicoModalShow}
                onHide={() => setAgregarMedicoModalShow(false)}
                onAccionExitosa={() => setMostrarToast(true)}
            />

            <ConMedToast
                mostrarToast={mostrarToast}
                setMostrarToast={setMostrarToast}
                titulo="Medico agregado exitosamente"
                descripcion="Has agregado un Medico"
            />

            <ConMedToast
                mostrarToast={mostrarEliminadoToast}
                setMostrarToast={setMostrarEliminadoToast}
                titulo="Medico eliminado exitosamente"
                descripcion="Has eliminado un Medico"
            />

            <Container className="mt-5">
                <h2 className="mb-4 text-center">Todos los Médicos</h2>

                {loading && (
                    <div className="d-flex justify-content-center">
                        <Spinner animation="border" variant="primary" />
                    </div>
                )}

                {error && <Alert variant="danger">{error}</Alert>}

                {!loading && !error && (
                    <div className="d-flex flex-column gap-2">
                        <div className="d-flex justify-content-between align-items-center mb-3">
                            <div>
                                <Form.Label className="me-2">
                                    Filtrar por especialidad:
                                </Form.Label>
                                <Form.Select
                                    className="d-inline w-auto"
                                    value={especialidadSeleccionada}
                                    onChange={(e) =>
                                        setEspecialidadSeleccionada(e.target.value)
                                    }
                                >
                                    <option value="Todas">Todas</option>
                                    {especialidadesLista.sort((a, b) => a.localeCompare(b)).map((esp, idx) => (
                                        <option key={idx} value={esp}>
                                            {esp}
                                        </option>
                                    ))}
                                </Form.Select>
                            </div>
                            <Button variant="success" onClick={handleAgregarMedico}>
                                Agregar Médico
                            </Button>
                        </div>

                        <Table striped bordered hover responsive>
                            <thead>
                            <tr className="text-center">
                                <th>Nombre</th>
                                <th>Apellido</th>
                                <th>Matricula</th>
                                <th>DNI</th>
                                <th>Especialidad</th>
                                <th>Acciones</th>
                            </tr>
                            </thead>
                            <tbody>
                            {medicosFiltrados.map((medico, index) => (
                                <tr className="text-center" key={index}>
                                    <td>{medico.nombre}</td>
                                    <td>{medico.apellido}</td>
                                    <td>{medico.matricula}</td>
                                    <td>{medico.dni}</td>
                                    <td>{medico.especialidad}</td>
                                    <td>
                                        <Button
                                            variant="danger"
                                            onClick={() => {
                                                const confirmacion = window.confirm(
                                                    "¿Estás seguro de que querés eliminar este médico?"
                                                );
                                                if (confirmacion) {
                                                    handleEliminarMedico(
                                                        medico.idMedico
                                                    );
                                                    setMostrarEliminadoToast(true);
                                                }
                                            }}
                                        >
                                            Eliminar
                                        </Button>
                                    </td>
                                </tr>
                            ))}
                            </tbody>
                        </Table>
                    </div>
                )}

                <Button variant="primary" className="w-100 mt-4" onClick={handleVolver}>
                    Volver
                </Button>
            </Container>
        </>
    );
};

export default TodosLosMedicosPage;
