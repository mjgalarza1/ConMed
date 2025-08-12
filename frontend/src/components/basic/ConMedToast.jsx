import { Toast, ToastContainer } from 'react-bootstrap';

const ConMedToast = ({ mostrarToast, setMostrarToast, titulo, descripcion }) => {
    return (
        <ToastContainer position="bottom-end" className="p-3">
            <Toast
                onClose={() => setMostrarToast(false)}
                show={mostrarToast}
                delay={3000}
                autohide
                bg="success"
            >
                <Toast.Header closeButton={false}>
                    <strong className="me-auto">{titulo}</strong>
                </Toast.Header>
                <Toast.Body className="text-white">{descripcion}</Toast.Body>
            </Toast>
        </ToastContainer>
    );
};

export default ConMedToast;
