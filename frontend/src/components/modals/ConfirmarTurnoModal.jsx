import { useEffect, useState } from 'react';
import { Button, Form, Spinner } from 'react-bootstrap';
import { verTurnosDisponiblesDelMedico } from "../../services/AxiosService.js";
import { reservarTurno } from "../../services/AxiosService.js";
import Modal from 'react-bootstrap/Modal';

function ConfirmarTurnoModal({ medico, show, onHide, onReservaExitosa }) {
    const [turnos, setTurnos] = useState([]);
    const [cargandoTurnos, setCargandoTurnos] = useState(false);
    const [cargandoReserva, setCargandoReserva] = useState(false);
    const [turnoSeleccionado, setTurnoSeleccionado] = useState(null);

    useEffect(() => {
        const obtenerTurnos = async () => {
            if (!medico?.id || !show) return;

            setCargandoTurnos(true);
            try {
                const response = await verTurnosDisponiblesDelMedico(medico.id);
                setTurnos(Array.from(response.data));
                if(turnoSeleccionado == null) {
                    setTurnoSeleccionado(0);
                }
            } catch (error) {
                console.error("Error al obtener los turnos del médico", error);
                setTurnos([]);
            } finally {
                setCargandoTurnos(false);
            }
        };

        obtenerTurnos();
    }, [medico, show]);

    const handleConfirmar = async () => {
        setCargandoReserva(true);
        try {
            const turno = turnos[turnoSeleccionado];
            const paciente_id = JSON.parse(localStorage.getItem("usuario"))?.id;
            await reservarTurno(paciente_id, turno.idTurno);
            setTurnoSeleccionado(null);

            if (onReservaExitosa) {
                onReservaExitosa();
            }
            onHide();
        } catch (error) {
            console.error("Error al reservar el turno", error);
        } finally {
            setCargandoReserva(false);
        }
    };

    return (
        <Modal
            show={show}
            onHide={onHide}
            backdrop="static"
            keyboard={false}
            centered
        >
            <Modal.Header closeButton>
                <Modal.Title>Reservar turno</Modal.Title>
            </Modal.Header>
            <Modal.Body className="d-flex flex-column gap-2">
                <div>
                    <h5>Nombre del médico:</h5>
                    <p>{medico?.nombre} {medico?.apellido}</p>
                </div>
                <div>
                    <h5>Matrícula:</h5>
                    <p>{medico?.matricula}</p>
                </div>
                <div>
                    <h5>Especialidad:</h5>
                    <p>{medico?.especialidad}</p>
                </div>

                <h6 className="border-top pt-3">Seleccione un turno disponible</h6>
                {cargandoTurnos ? (
                    <div className="d-flex justify-content-center">
                        <Spinner animation="border" variant="primary" />
                    </div>
                ) : (
                    <Form.Select
                        value={turnoSeleccionado ?? ""}
                        onChange={(e) => setTurnoSeleccionado(e.target.value)}
                        disabled={cargandoReserva}
                    >
                        {turnos.length === 0 ? (
                            <option disabled>No hay turnos disponibles</option>
                        ) : (
                            turnos.map((turno, index) => (
                                <option key={index} value={index}>
                                    {turno.fecha} - {turno.hora}hs
                                </option>
                            ))
                        )}
                    </Form.Select>
                )}
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={onHide}>
                    Cancelar
                </Button>
                <Button
                    variant="success"
                    onClick={handleConfirmar}
                    disabled={turnos.length === 0 || cargandoReserva}
                >
                    {cargandoReserva ? <Spinner animation="border" size="sm" /> : "Confirmar"}
                </Button>
            </Modal.Footer>
        </Modal>
    );
}

export default ConfirmarTurnoModal;
