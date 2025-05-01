import { Modal, Button } from 'react-bootstrap';

function CerrarSesionModal({ show, onConfirm, onCancel }) {
    return (
        <Modal show={show} onHide={onCancel}>
            <Modal.Header>
                <Modal.Title>¿Estás seguro de cerrar sesión?</Modal.Title>
            </Modal.Header>
            <Modal.Footer>
                <Button variant="secondary" onClick={onCancel}>
                    No
                </Button>
                <Button variant="danger" onClick={onConfirm}>
                    Sí
                </Button>
            </Modal.Footer>
        </Modal>
    );
}

export default CerrarSesionModal;
