import { Card, Container, Row, Button } from "react-bootstrap";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import EditarPerfilModal from "../components/modals/EditarPerfilModal.jsx";
import { actualizarPerfil } from "../services/AxiosService"; // 🔄 Import axios function

const MyProfilePage = () => {
    const [usuario, setUsuario] = useState(null);
    const [showModal, setShowModal] = useState(false);
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

    const handleGuardarCambios = async (nuevosDatos) => {
        const usuarioActualizado = { ...usuario, ...nuevosDatos };

        try {
            await actualizarPerfil(usuarioActualizado);
            setUsuario(usuarioActualizado);
            localStorage.setItem("usuario", JSON.stringify(usuarioActualizado));
            alert("Perfil actualizado con éxito.");
        } catch (error) {
            console.error("Error al actualizar perfil:", error);
            alert("Error al actualizar el perfil.");
        }
    };

    const { nombre, apellido, dni, role, matricula, especialidad, mail } = usuario;

    return (
        <Container className="mt-4">
            <Card>
                <Card.Body>
                    <Card.Title className="text-center">Mis Datos Personales</Card.Title>
                    <Row className="mb-2">
                        <Row md={6}><strong>Nombre:</strong> {nombre}</Row>
                        <Row md={6}><strong>Apellido:</strong> {apellido}</Row>
                        <Row md={6}><strong>DNI:</strong> {dni}</Row>
                        {role === "PACIENTE" && (
                            <Row>
                                <Row md={6}><strong>Correo Electrónico:</strong> {mail}</Row>
                            </Row>
                        )}
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

            <div className="mt-3 d-flex justify-content-between">
                <div className="mt-3 d-flex gap-2">
                    <Button variant="primary" onClick={() => setShowModal(true)}>
                        Editar Perfil
                    </Button>
                    <Button variant="primary" onClick={() => navigate("/cambiar-contraseña")}>
                        Cambiar Contraseña
                    </Button>
                </div>

            </div>


            <EditarPerfilModal
                show={showModal}
                handleClose={() => setShowModal(false)}
                usuario={usuario}
                onGuardar={handleGuardarCambios}
            />
        </Container>
    );
};

export default MyProfilePage;
