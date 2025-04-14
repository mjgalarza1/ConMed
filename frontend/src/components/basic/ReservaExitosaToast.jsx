import { Toast, ToastContainer } from 'react-bootstrap';

const ReservaExitosaToast = ({ mostrarToast, setMostrarToast }) => {
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
                    <strong className="me-auto">Reserva exitosa</strong>
                </Toast.Header>
                <Toast.Body className="text-white">Has reservado un turno</Toast.Body>
            </Toast>
        </ToastContainer>
    );
};

export default ReservaExitosaToast;
