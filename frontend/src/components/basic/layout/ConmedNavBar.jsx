import Container from "react-bootstrap/Container";
import Navbar from "react-bootstrap/Navbar";
import ConmedLogo from "./ConmedLogo.jsx";
import {Button} from "react-bootstrap";
import {useNavigate} from "react-router-dom";
import CerrarSesionModal from "../../modals/CerrarSesionModal.jsx";
import {useEffect, useState} from "react";

function ConmedNavBar() {
    const navigate = useNavigate();

    const [usuarioLogueado, setUsuarioLogueado] = useState(false);
    const [mostrarModal, setMostrarModal] = useState(false);

    const showCerrarSesionButton = () => {
        return <Button
            variant="outline-primary"
            onClick={handleCerrarSesion}
        >
            Cerrar sesión
        </Button>
    };

    const handleCerrarSesion = () => {
        setMostrarModal(true);
    };

    const confirmarCerrarSesion = () => {
        localStorage.clear();
        setMostrarModal(false);
        navigate("/");
        window.location.reload();
    };

    useEffect(() => {
        const token = localStorage.getItem("token");
        if (token) {
            setUsuarioLogueado(true);
        } else {
            setUsuarioLogueado(false);
        }
    }, [])

    return (
        <>
            <CerrarSesionModal
                show={mostrarModal}
                onConfirm={confirmarCerrarSesion}
                onCancel={() => setMostrarModal(false)}
            />

            <Navbar expand="lg" className="bg-white">
                <Container>
                    <Navbar.Brand href="/">
                        <ConmedLogo/>
                    </Navbar.Brand>
                    <Navbar.Collapse className="justify-content-end">
                        <Navbar.Text>
                            {usuarioLogueado ? showCerrarSesionButton() : null}
                        </Navbar.Text>
                    </Navbar.Collapse>
                </Container>
            </Navbar>
        </>
    )
}

export default ConmedNavBar;
