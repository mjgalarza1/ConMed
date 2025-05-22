import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { Modal, Button, Form } from 'react-bootstrap';
import { changePassOldForPassNew } from '../services/AxiosService.js'; // o tu ruta real

const CambiarContraseñaPage = () => {

    const usuario = JSON.parse(localStorage.getItem("usuario"));
    const dni = usuario?.dni;
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        actual: '',
        nueva: '',
        repetir: '',
    });

    useEffect(() => {
        const token = localStorage.getItem('token');
        if (!token) {
            navigate('/login');
        }
    }, [navigate]);

    const [errors, setErrors] = useState({});
    const [showSuccessModal, setShowSuccessModal] = useState(false);
    const [serverError, setServerError] = useState('');

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
        setErrors({ ...errors, [e.target.name]: false });
        setServerError('');
    };

    const esAlfanumericaYValida = (str) => /^[a-zA-Z0-9]{6,}$/.test(str);

    const handleSubmit = async (e) => {
        e.preventDefault();

        const nuevosErrores = {};

        if (!esAlfanumericaYValida(formData.actual)) {
            nuevosErrores.actual = true;
        }

        if (!esAlfanumericaYValida(formData.nueva)) {
            nuevosErrores.nueva = true;
        }

        if (formData.nueva !== formData.repetir) {
            nuevosErrores.repetir = true;
        }

        if (Object.keys(nuevosErrores).length > 0) {
            setErrors(nuevosErrores);
            return;
        }

        try {
            await changePassOldForPassNew(dni, formData.actual, formData.nueva);
            setShowSuccessModal(true);
        } catch (error) {
            setServerError(error.response?.data || "Error al cambiar la contraseña");
            setErrors({ actual: true });
        }
    };

    const handleClose = () => {
        setShowSuccessModal(false);
        navigate('/miPerfil');
    };

    return (
        <div className="container mt-5" style={{ maxWidth: '500px' }}>
            <h3 className="mb-4">Cambiar Contraseña</h3>
            <Form onSubmit={handleSubmit}>
                <Form.Group className="mb-3">
                    <Form.Label>Contraseña Actual</Form.Label>
                    <Form.Control
                        type="password"
                        name="actual"
                        value={formData.actual}
                        onChange={handleChange}
                        isInvalid={errors.actual}
                    />
                    <Form.Control.Feedback type="invalid">
                        {serverError || "Contraseña incorrecta o no válida."}
                    </Form.Control.Feedback>
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>Nueva Contraseña</Form.Label>
                    <Form.Control
                        type="password"
                        name="nueva"
                        value={formData.nueva}
                        onChange={handleChange}
                        isInvalid={errors.nueva}
                    />
                    <Form.Control.Feedback type="invalid">
                        Debe tener al menos 6 caracteres alfanuméricos.
                    </Form.Control.Feedback>
                </Form.Group>

                <Form.Group className="mb-4">
                    <Form.Label>Repetir Contraseña</Form.Label>
                    <Form.Control
                        type="password"
                        name="repetir"
                        value={formData.repetir}
                        onChange={handleChange}
                        isInvalid={errors.repetir}
                    />
                    <Form.Control.Feedback type="invalid">
                        Las contraseñas no coinciden.
                    </Form.Control.Feedback>
                </Form.Group>

                <Button variant="primary" type="submit">
                    Guardar Contraseña
                </Button>
            </Form>

            <Modal show={showSuccessModal} onHide={handleClose} centered>
                <Modal.Header closeButton>
                    <Modal.Title>Éxito</Modal.Title>
                </Modal.Header>
                <Modal.Body>Su contraseña ha sido cambiada con éxito.</Modal.Body>
                <Modal.Footer>
                    <Button variant="success" onClick={handleClose}>
                        Aceptar
                    </Button>
                </Modal.Footer>
            </Modal>
        </div>
    );
};

export default CambiarContraseñaPage;
