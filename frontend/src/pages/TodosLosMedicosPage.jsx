import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import {getAllMedicos, deleteMedico} from "../services/AxiosService.js";
import {Alert, Button, Container, Spinner, Table} from "react-bootstrap";
import ConMedToast from "../components/basic/ConMedToast.jsx";
import AgregarMedicoModal from "../components/modals/AgregarMedicoModal.jsx";

const TodosLosMedicosPage = () => {
    const [medicos, setMedicos] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [agregarMedicoModalShow, setAgregarMedicoModalShow] = useState(false);
    const [mostrarToast, setMostrarToast] = useState(false);
    const [mostrarEliminadoToast, setMostrarEliminadoToast] = useState(false);

    const navigate = useNavigate();

    const handleVolver = () => {
        navigate("/");
    }

    const handleAgregarMedico = () => {
        const token = localStorage.getItem("token"); // O el nombre que uses para el token

        if (!token) {
            alert("Debe iniciar sesión");
            localStorage.clear();
            navigate("/"); // Redirigir al inicio
        } else {
            setAgregarMedicoModalShow(true); // Mostrar el modal si hay token
        }
    }

    const handleEliminarMedico = async (medicoId) => {
        try {
            await deleteMedico(medicoId);
            setMostrarEliminadoToast(true);
            // Refrescar la lista de médicos después de eliminar uno
            setMedicos(medicos.filter((medico) => medico.id !== medicoId));
            // eslint-disable-next-line no-unused-vars
        } catch (err) {
            // Si es un error de autenticación (401 o 403), redirigir
            if (err.response && (err.response.status === 401 || err.response.status === 403)) {
                alert("Debe iniciar sesión")
                localStorage.clear();
                navigate("/"); // Redirigir al inicio
            } else {
                setError("Error al eliminar el médico");
            }
        }
    };

    useEffect(() => {
        const fetchTodosLosMedicos = async () => {
            try {
                //const user_dni = JSON.parse(localStorage.getItem("usuario"))?.dni;
                const response = await getAllMedicos();
                setMedicos(response.data);
                // eslint-disable-next-line no-unused-vars
            } catch (err) {
                setError("Error al obtener todos los medicos");
            } finally {
                setLoading(false);
            }
        };

        fetchTodosLosMedicos();
    }, [mostrarToast, mostrarEliminadoToast]);

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
                titulo= "Medico agregado exitosamente"
                descripcion= "Has agregado un Medico"
            />

            <ConMedToast
                mostrarToast={mostrarEliminadoToast}
                setMostrarToast={setMostrarEliminadoToast}
                titulo= "Medico eliminado exitosamente"
                descripcion= "Has eliminado un Medico"
            />

            <Container className="mt-5">
                <h2 className="mb-4 text-center">Todos los Médicos</h2>
                {loading && (
                    <div className="d-flex justify-content-center">
                        <Spinner animation="border" variant="primary"/>
                    </div>
                )}

                {error && <Alert variant="danger">{error}</Alert>}

                {!loading && !error && (

                    <div className="d-flex flex-column gap-2">
                        <Button variant="success" onClick={handleAgregarMedico} className="ms-auto">
                            Agregar Medico
                        </Button>

                        <Table striped bordered hover responsive>
                            <thead>
                            <tr className="text-center">
                                <th>Nombre</th>
                                <th>Apellido</th>
                                <th>Matricula</th>
                                <th>DNI</th>
                                <th>Especialidad</th>
                                {/*<th>Contraseña</th>*/}
                                {/*<th>Acciones</th>*/}
                            </tr>
                            </thead>
                            <tbody>
                            {medicos.map((medicos, index) => (
                                <tr className="text-center" key={index}>
                                    <td>{medicos.nombre}</td>
                                    <td>{medicos.apellido}</td>
                                    <td>{medicos.matricula}</td>
                                    <td>{medicos.dni}</td>
                                    <td>{medicos.especialidad}</td>
                                    {/*<td>{medicos.passwordMedico}</td>*/}
                                    <td>
                                        <Button
                                            variant="danger"
                                            onClick={() => {
                                                const confirmacion = window.confirm("¿Estás seguro de que querés eliminar este médico?");
                                                if (confirmacion) {
                                                    handleEliminarMedico(medicos.idMedico);
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
                <Button variant="primary" className="w-100" onClick={handleVolver}>
                    Volver
                </Button>
            </Container>
        </>
    )
}

export default TodosLosMedicosPage;