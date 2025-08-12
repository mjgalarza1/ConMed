import { useState } from 'react';
import { Button, Modal, Form, Spinner } from 'react-bootstrap';
import { agregarMedico } from '../../services/AxiosService'; // Asegurate de tener esta función
import { especialidadesLista } from '../../data/especialidades';
import {useNavigate} from "react-router-dom";

function AgregarMedicoModal({ show, onHide, onAccionExitosa }) {
    const [cargando, setCargando] = useState(false);
    const [formData, setFormData] = useState({
        nombre: '',
        apellido: '',
        especialidad: '',
        dni: '',
        matricula: '',
        passwordMedico: ''
    });

    const navigate = useNavigate();


    const handleInputChange = (e) => {
        const { name, value } = e.target;

        if (name === "nombre" || name === "apellido") {
            const soloLetrasYEspacios = /^[A-Za-zÁÉÍÓÚÜáéíóúüÑñ ]*$/;
            if (value === "" || soloLetrasYEspacios.test(value)) {
                const normalizado = value.replace(/\s{2,}/g, " ");
                setFormData({ ...formData, [name]: normalizado });
            }
        } else if (name === "passwordMedico") {
            const soloAlfanumerico = value.replace(/[^A-Za-z0-9]/g, '');
            setFormData({ ...formData, [name]: soloAlfanumerico });
        } else {
            setFormData({ ...formData, [name]: value });
        }
    };






    const handleConfirmar = async () => {
        const token = localStorage.getItem("token"); // O el nombre que uses para el token

        if (!token) {
            alert("Debe iniciar sesión");
            localStorage.clear();
            navigate("/"); // Redirigir al inicio
            return; // Evitar continuar si no hay token
        }

        const soloLetrasSinEspacio = /^[A-Za-zÁÉÍÓÚÜáéíóúüÑñ]+$/;
        const soloLetrasYUnEspacio = /^[A-Za-zÁÉÍÓÚÜáéíóúüÑñ]+(?: [A-Za-zÁÉÍÓÚÜáéíóúüÑñ]+)?$/;
        const alfanumericoRegex = /^[A-Za-z0-9]+$/;
        const tieneLetra = /[A-Za-z]/;
        const tieneNumero = /\d/;

        // Validaciones
        if (!soloLetrasYUnEspacio.test(formData.nombre)) {
            alert("El nombre es incorrecto.");
            return;
        }

        if (!soloLetrasSinEspacio.test(formData.apellido)) {
            alert("El apellido es incorrecto.");
            return;
        }

        if (!formData.especialidad) {
            alert("Seleccione una especialidad.");
            return;
        }

        if (!alfanumericoRegex.test(formData.passwordMedico) ||
            !tieneLetra.test(formData.passwordMedico) ||
            !tieneNumero.test(formData.passwordMedico) ||
            formData.passwordMedico.length < 6) {
            alert("La contraseña debe ser alfanumérica, contener al menos una letra, al menos un número y tener mínimo 6 caracteres.");
            return;
        }



        // Si pasa todas las validaciones, continúa
        setCargando(true);
        try {
            await agregarMedico(
                formData.nombre,
                formData.apellido,
                formData.dni,
                formData.especialidad,
                formData.matricula,
                formData.passwordMedico
            );
            if (onAccionExitosa) onAccionExitosa();
            setFormData({
                nombre: '',
                apellido: '',
                especialidad: '',
                dni: '',
                matricula: '',
                passwordMedico: ''
            })
            onHide();
        } catch (error) {
            console.error("Error al agregar médico", error);
            if (error.response && error.response.data) {
                alert(error.response.data);
            } else {
                alert("Error desconocido al agregar médico.");
            }
        } finally {
            setCargando(false);
        }
    };


    return (
        <Modal show={show} onHide={onHide} backdrop="static" keyboard={false} centered>
            <Modal.Header closeButton>
                <Modal.Title>Agregar Médico</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form>
                    <Form.Group className="mb-3">
                        <Form.Label>Nombre</Form.Label>
                        <div className="d-flex align-items-center">
                            <span className="me-1">Dr</span>
                            <Form.Control
                                type="text"
                                name="nombre"
                                value={formData.nombre}
                                onChange={handleInputChange}
                                placeholder="Ej: Juan"
                            />
                        </div>
                    </Form.Group>


                    <Form.Group className="mb-3">
                        <Form.Label>Apellido</Form.Label>
                        <Form.Control
                            type="text"
                            name="apellido"
                            value={formData.apellido}
                            onChange={handleInputChange}
                            placeholder="Ej: Pérez"
                        />
                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Label>Especialidad</Form.Label>
                        <Form.Select
                            name="especialidad"
                            value={formData.especialidad}
                            onChange={handleInputChange}
                        >
                            <option value="">Seleccione una especialidad</option>
                            {especialidadesLista.map((esp, idx) => (
                                <option key={idx} value={esp}>{esp}</option>
                            ))}
                        </Form.Select>
                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Label>DNI</Form.Label>
                        <Form.Control
                            type="text"
                            name="dni"
                            value={formData.dni}
                            onChange={(e) => {
                                const valor = e.target.value;
                                if (/^\d{0,8}$/.test(valor)) {
                                    handleInputChange(e);
                                }
                            }}
                            placeholder="Ej: 30123456"
                            maxLength={8}
                            pattern="\d{8}"
                            required
                        />
                        <Form.Text className="text-muted">Debe contener exactamente 8 dígitos.</Form.Text>
                    </Form.Group>


                    <Form.Group className="mb-3">
                        <Form.Label>Matrícula</Form.Label>
                        <div className="d-flex align-items-center">
                            <span className="me-1">MD-</span>
                            <Form.Control
                                type="text"
                                name="matricula"
                                value={formData.matricula.replace(/^MD-/, '')}
                                onChange={(e) => {
                                    const soloNumeros = e.target.value.replace(/\D/g, '').slice(0, 6); // Solo 6 dígitos numéricos
                                    setFormData(prev => ({ ...prev, matricula: `MD-${soloNumeros}` }));
                                }}
                                placeholder="123456"
                                maxLength={6}
                            />
                        </div>
                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Label>Contraseña</Form.Label>
                        <Form.Control
                            type="password"
                            name="passwordMedico"
                            value={formData.passwordMedico}
                            onChange={handleInputChange}
                            placeholder="********"
                        />
                    </Form.Group>
                </Form>
            </Modal.Body>
            <Modal.Footer>
                <Button
                    variant="secondary"
                    onClick={() => {
                        setFormData({
                            nombre: '',
                            apellido: '',
                            especialidad: '',
                            dni: '',
                            matricula: '',
                            passwordMedico: ''
                        });
                        onHide();
                    }}
                >
                    Cancelar
                </Button>

                <Button variant="success" onClick={handleConfirmar} disabled={cargando}>
                    {cargando ? <Spinner animation="border" size="sm" /> : "Confirmar"}
                </Button>
            </Modal.Footer>
        </Modal>
    );
}

export default AgregarMedicoModal;
