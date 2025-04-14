import {useEffect, useState} from "react";
import {Table, Container, Spinner, Alert, Button} from "react-bootstrap";
import {verMedicosDisponibles} from "../services/AxiosService.js";
import {useNavigate} from "react-router-dom";

const MedicosDisponiblesPage = () => {
    const [medicos, setMedicos] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const navigate = useNavigate();

    const handleVolver = () => {
        navigate("/");
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
                    <tr>
                        <th>Nombre</th>
                        <th>Apellido</th>
                        <th>N° de Matrícula</th>
                        <th>Especialidad</th>
                    </tr>
                    </thead>
                    <tbody>
                    {medicos.map((medico, index) => (
                        <tr key={index}>
                            <td>{medico.nombre}</td>
                            <td>{medico.apellido}</td>
                            <td>{medico.matricula}</td>
                            <td>{medico.especialidad}</td>
                        </tr>
                    ))}
                    </tbody>
                </Table>
            )}
            <Button variant="primary" className="w-100" onClick={handleVolver}>
                Volver
            </Button>
        </Container>

    );
};

export default MedicosDisponiblesPage;
