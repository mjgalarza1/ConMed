import {useState} from 'react';
import { Button, Spinner, Modal } from 'react-bootstrap';
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { registerLocale } from  "react-datepicker";
import { es } from 'date-fns/locale/es';
import {agregarTurno} from "../../services/AxiosService.js";
registerLocale('es', es)

function AgregarTurnoModal({show, onHide, onAccionExitosa}) {
    const [cargandoTurno, setCargandoTurno] = useState(false);
    const [startDate, setStartDate] = useState(getNextQuarterHourDate());

    const handleConfirmar = async () => {
        setCargandoTurno(true);
        try {
            const user_id = JSON.parse(localStorage.getItem("usuario"))?.id;
            const fecha = formatearFechaLocal(startDate)
            const tiempo = startDate.toTimeString().split(' ')[0].slice(0, 5);
            await agregarTurno(user_id, fecha, tiempo, "DISPONIBLE");

            if (onAccionExitosa) {
                onAccionExitosa();
            }
            onHide();
        } catch (error) {
            console.error("Error al agregar el turno", error);
            const status = error.response?.status;
            switch (status) {
                case 401:
                    alert("No estás logueado. Por favor iniciá sesión.");
                    break;
                case 403:
                    alert("No tenés permisos para hacer esto.");
                    break;
                case 404:
                    alert("No se encontró el recurso.");
                    break;
                case 500:
                    alert("Error del servidor. Intentalo más tarde.");
                    break;
                default:
                    alert(error.response.data || "Ocurrió un error inesperado. Intentalo nuevamente.");
                    break;
            }
        } finally {
            setCargandoTurno(false);
            setStartDate(getNextQuarterHourDate());
        }
    };

    const tiempoFiltrado = (time) => {
        const diaActual = new Date()
        const fechaSeleccionada = new Date(time)
        return diaActual.getTime() < fechaSeleccionada.getTime()
    }

    function getNextQuarterHourDate(date = new Date()) {
        const ms = 1000 * 60 * 15;
        return new Date(Math.ceil(date.getTime() / ms) * ms);
    }

    function formatearFechaLocal(date) {
        const año = date.getFullYear();
        const mes = String(date.getMonth() + 1).padStart(2, '0');
        const dia = String(date.getDate()).padStart(2, '0');
        return `${año}-${mes}-${dia}`;
    }

    function formatearHoraLocal(date) {
        const horas = String(date.getHours()).padStart(2, '0');
        const minutos = String(date.getMinutes()).padStart(2, '0');
        return `${horas}:${minutos}`;
    }

    return (
        <Modal
            show={show}
            onHide={onHide}
            backdrop="static"
            keyboard={false}
            centered
        >
            <Modal.Header closeButton>
                <Modal.Title>Agregar turno</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <div className="w-100 text-center m-auto">
                    <h5>Seleccione una fecha y hora</h5>
                    <DatePicker
                        selected={startDate}
                        onChange={(date) => setStartDate(date)}
                        className="form-control"
                        showTimeSelect
                        dateFormat="Pp"
                        timeIntervals={15}
                        locale="es"
                        minDate={new Date()}
                        filterTime={tiempoFiltrado}
                        inline
                    />
                </div>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={onHide}>
                    Cancelar
                </Button>
                <Button
                    variant="success"
                    onClick={handleConfirmar}
                    disabled={cargandoTurno}
                >
                    {cargandoTurno ? <Spinner animation="border" size="sm" /> : "Confirmar"}
                </Button>
            </Modal.Footer>
        </Modal>
    );
}

export default AgregarTurnoModal;
