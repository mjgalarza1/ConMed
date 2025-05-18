import { Modal, Button, Form } from "react-bootstrap";
import { useState, useEffect } from "react";
import {getAllMails} from "../../services/AxiosService.js";

// Simula un llamado a base de datos
const emailExiste = async (email) => {
    const response = await getAllMails();
    const emailsEnUso = response.data;
    return emailsEnUso.includes(email.toLowerCase());
};

const EditarPerfilModal = ({ show, handleClose, usuario, onGuardar }) => {
    const [formData, setFormData] = useState({
        nombre: "",
        apellido: "",
        mail: "",
        matricula: ""
    });

    const [errors, setErrors] = useState({});
    const [loading, setLoading] = useState(false);

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

    const validarEmailFormato = (email) => {
        const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        return emailRegex.test(email);
    };

    const validar = async () => {
        const nuevosErrores = {};

        if (!formData.nombre.trim()) {
            nuevosErrores.nombre = "El nombre no puede estar vacío";
        } else if (!/^[a-zA-Z\s]+$/.test(formData.nombre.trim())) {
            nuevosErrores.nombre = "Solo se permiten letras";
        }

        if (!formData.apellido.trim()) {
            nuevosErrores.apellido = "El apellido no puede estar vacío";
        } else if (!/^[a-zA-Z\s]+$/.test(formData.apellido.trim())) {
            nuevosErrores.apellido = "Solo se permiten letras";
        }

        if (usuario.role === "PACIENTE") {
            const mail = formData.mail.trim();

            if (!mail) {
                nuevosErrores.mail = "El correo no puede estar vacío";
            } else if (!validarEmailFormato(mail)) {
                nuevosErrores.mail = "Formato de correo inválido";
            } else if (mail.split("@").length !== 2) {
                nuevosErrores.mail = "Debe contener un solo '@'";
            } else if (await emailExiste(mail) && mail !== usuario.mail) {
                nuevosErrores.mail = "El correo ya está en uso";
            }
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

    const handleSubmit = async () => {
        setLoading(true);
        const esValido = await validar();
        setLoading(false);

        if (esValido) {
            const datosActualizados = {
                nombre: formData.nombre.trim(),
                apellido: formData.apellido.trim()
            };

            if (usuario.role === "PACIENTE") {
                datosActualizados.mail = formData.mail.trim();
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
                        <Form.Group controlId="formEmail" className="mt-2">
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
                <Button variant="primary" onClick={handleSubmit} disabled={loading}>
                    {loading ? "Validando..." : "Guardar cambios"}
                </Button>
            </Modal.Footer>
        </Modal>
    );
};

export default EditarPerfilModal;
