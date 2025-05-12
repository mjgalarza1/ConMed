import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { getAllUsuarios } from "../services/AxiosService.js";
import { Alert, Button, Container, Spinner, Table } from "react-bootstrap";

const TodosLosUsuariosPage = () => {
    const [usuarios, setUsuarios] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    const handleVolver = () => {
        navigate("/");
    };

    useEffect(() => {
        const fetchTodosLosUsuarios = async () => {
            try {
                const response = await getAllUsuarios();
                setUsuarios(response.data);
                // eslint-disable-next-line no-unused-vars
            } catch (err) {
                setError("Error al obtener todos los usuarios");
            } finally {
                setLoading(false);
            }
        };

        fetchTodosLosUsuarios();
    }, []);

    return (
        <Container className="mt-5">
            <h2 className="mb-4 text-center">Usuarios</h2>

            {loading && (
                <div className="d-flex justify-content-center">
                    <Spinner animation="border" variant="primary" />
                </div>
            )}

            {error && <Alert variant="danger">{error}</Alert>}

            {!loading && !error && (
                <Table striped bordered hover responsive>
                    <thead>
                    <tr className="text-center">
                        <th>DNI</th>
                        <th>Nombre y Apellido</th>
                        <th>Rol</th>
                    </tr>
                    </thead>
                    <tbody>
                    {usuarios.map((usuario, index) => (
                        <tr className="text-center" key={index}>
                            <td>{usuario.dni}</td>
                            <td>{usuario.nombreCompleto}</td>
                            <td>{usuario.role}</td>
                        </tr>
                    ))}
                    </tbody>
                </Table>
            )}

            <Button variant="primary" className="w-100 mt-3" onClick={handleVolver}>
                Volver
            </Button>
        </Container>
    );
};

export default TodosLosUsuariosPage;
