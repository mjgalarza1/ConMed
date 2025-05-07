import { Card, Container, Row, Col } from "react-bootstrap";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";

const MyProfilePage = () => {

    const [usuario, setUsuario] = useState(null);

    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem("token");
        const datosUsuario = localStorage.getItem("usuario");

        if (!token || !datosUsuario) {
            localStorage.clear();
            navigate("/");
        } else {
            setUsuario(JSON.parse(datosUsuario));
        }
    }, [navigate]);

    if (!usuario) return null;

    const { nombre, apellido, dni, role, matricula, especialidad } = usuario;

    return (
        <Container className="mt-4">
            <Card>
                <Card.Body>
                    <Card.Title className="text-center">Mis Datos Personales</Card.Title>
                    <Row className="mb-2">
                        <Row md={6}><strong>Nombre:</strong> {nombre}</Row>
                        <Row md={6}><strong>Apellido:</strong> {apellido}</Row>
                        <Row md={6}><strong>DNI:</strong> {dni}</Row>
                        <Row md={6}><strong>Rol:</strong> {role}</Row>
                        {role === "MEDICO" && (
                            <Row>
                                <Row md={6}><strong>Matricula:</strong> {matricula}</Row>
                                <Row md={6}><strong>Especialidad:</strong> {especialidad}</Row>
                            </Row>
                        )}
                    </Row>
                </Card.Body>
            </Card>
        </Container>
    );
};

export default MyProfilePage;
