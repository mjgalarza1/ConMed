import { Modal, Button, Form } from "react-bootstrap";
import { useState, useEffect } from "react";

const EditarPerfilModal = ({ show, handleClose, usuario, onGuardar }) => {
    const [formData, setFormData] = useState({
        nombre: "",
        apellido: "",
        mail: "",
        matricula: ""
    });

    const [errors, setErrors] = useState({});

    useEffect(() => {
        if (usuario) {
            setFormData({
                nombre: usuario.nombre || "",
                apellido: usuario.apellido || "",
                mail: usuario.mail || "",
                matricula: usuario.matricula || ""
            });
            setErrors({});
        }
    }, [usuario]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    const validar = () => {
        const nuevosErrores = {};

        if (!/^[a-zA-Z\s]+$/.test(formData.nombre)) {
            nuevosErrores.nombre = "Solo se permiten letras";
        }

        if (!/^[a-zA-Z\s]+$/.test(formData.apellido)) {
            nuevosErrores.apellido = "Solo se permiten letras";
        }

        if (usuario.role === "PACIENTE" && !formData.mail.includes("@")) {
            nuevosErrores.mail = "Correo inválido";
        }

        if (usuario.role === "MEDICO") {
            if (!formData.matricula.trim()) {
                nuevosErrores.matricula = "Campo obligatorio";
            } else if (!/^MD-\d{6}$/.test(formData.matricula)) {
                nuevosErrores.matricula = "Formato inválido. Debe ser MD- seguido de 6 números (ej. MD-512369)";
            }
        }

        setErrors(nuevosErrores);
        return Object.keys(nuevosErrores).length === 0;
    };

    const handleSubmit = () => {
        if (validar()) {
            const datosActualizados = {
                nombre: formData.nombre,
                apellido: formData.apellido
            };

            if (usuario.role === "PACIENTE") {
                datosActualizados.mail = formData.mail;
            }

            if (usuario.role === "MEDICO") {
                datosActualizados.matricula = formData.matricula;
            }

            onGuardar(datosActualizados);
            handleClose();
        }
    };

    return (
        <Modal show={show} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>Editar perfil</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form>
                    <Form.Group controlId="nombre">
                        <Form.Label>Nombre</Form.Label>
                        <Form.Control
                            type="text"
                            name="nombre"
                            value={formData.nombre}
                            onChange={handleChange}
                            isInvalid={!!errors.nombre}
                        />
                        <Form.Control.Feedback type="invalid">
                            {errors.nombre}
                        </Form.Control.Feedback>
                    </Form.Group>

                    <Form.Group controlId="apellido" className="mt-2">
                        <Form.Label>Apellido</Form.Label>
                        <Form.Control
                            type="text"
                            name="apellido"
                            value={formData.apellido}
                            onChange={handleChange}
                            isInvalid={!!errors.apellido}
                        />
                        <Form.Control.Feedback type="invalid">
                            {errors.apellido}
                        </Form.Control.Feedback>
                    </Form.Group>

                    {usuario.role === "PACIENTE" && (
                        <Form.Group controlId="mail" className="mt-2">
                            <Form.Label>Correo electrónico</Form.Label>
                            <Form.Control
                                type="email"
                                name="mail"
                                value={formData.mail}
                                onChange={handleChange}
                                isInvalid={!!errors.mail}
                            />
                            <Form.Control.Feedback type="invalid">
                                {errors.mail}
                            </Form.Control.Feedback>
                        </Form.Group>
                    )}

                    {usuario.role === "MEDICO" && (
                        <Form.Group controlId="matricula" className="mt-2">
                            <Form.Label>Matrícula</Form.Label>
                            <Form.Control
                                type="text"
                                name="matricula"
                                value={formData.matricula}
                                onChange={handleChange}
                                isInvalid={!!errors.matricula}
                            />
                            <Form.Control.Feedback type="invalid">
                                {errors.matricula}
                            </Form.Control.Feedback>
                        </Form.Group>
                    )}
                </Form>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={handleClose}>
                    Cancelar
                </Button>
                <Button variant="primary" onClick={handleSubmit}>
                    Guardar cambios
                </Button>
            </Modal.Footer>
        </Modal>
    );
};

export default EditarPerfilModal;
