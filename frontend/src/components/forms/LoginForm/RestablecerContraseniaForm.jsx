import {Form, Button, Alert} from "react-bootstrap";
import {useState} from "react";
import {useNavigate} from "react-router-dom";
import {enviarMailRestablecimiento} from "../../../services/axiosService";

const RestablecerContraseniaForm = () => {
    const [email, setEmail] = useState("");
    const [alert, setAlert] = useState(null); // {type: 'success' | 'danger', message: string}
    const [showAlert, setShowAlert] = useState(false);
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();

        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email)) {
            mostrarMensajeError("Formato de correo inválido");
            return;
        }

        try {
            await enviarMailRestablecimiento(email);
            mostrarMensajeExito("Se ha enviado una nueva contraseña a tu correo.");
        } catch (error) {
            if (error.response && error.response.status === 404) {
                mostrarMensajeError("No se encontró una cuenta asociada a este correo");
            } else {
                mostrarMensajeError("Ocurrió un error inesperado");
            }
        }
    };

    const mostrarMensajeExito = (mensaje) => {
        setAlert({type: "success", message: mensaje});
        setShowAlert(true);
        setTimeout(() => {
            setShowAlert(false);
            navigate("/login");
        }, 3000);
    };

    const mostrarMensajeError = (mensaje) => {
        setAlert({type: "danger", message: mensaje});
        setShowAlert(true);
        setTimeout(() => setShowAlert(false), 3000);
    };

    return (
        <div className="d-flex justify-content-center align-items-center flex-grow-1" style={{height: "calc(100vh - 100px)"}}>
            <Form
                onSubmit={handleSubmit}
                className="d-flex flex-column rounded-4 p-4"
                style={{
                    border: "1px solid #cccccc",
                    width: "500px",
                }}
            >
                <h2 className="mb-4">Restablecer Contraseña</h2>

                {showAlert && (
                    <Alert
                        variant={alert.type}
                        onClose={() => setShowAlert(false)}
                        dismissible
                    >
                        <strong>{alert.message}</strong>
                    </Alert>
                )}

                <Form.Group className="mb-3" controlId="formGroupEmail">
                    <Form.Label>Correo electrónico</Form.Label>
                    <Form.Control
                        type="email"
                        placeholder="Ingrese su correo electrónico"
                        onChange={(e) => setEmail(e.currentTarget.value)}
                        value={email}
                        required
                    />
                </Form.Group>

                <Button variant="primary" type="submit">
                    Enviar Contraseña Temporal
                </Button>
            </Form>
        </div>
    );

};

export default RestablecerContraseniaForm;
