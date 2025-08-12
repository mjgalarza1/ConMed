import {Button, Form} from "react-bootstrap";
import {useState} from "react";

const RegisterForm = ({handleSubmitRegistration}) => {

    const [dni, setDni] = useState("");
    const [passwordPaciente, setPasswordPaciente] = useState("");
    const [nombre, setNombre] = useState("");
    const [mail, setMail] = useState("");

    const onSubmit = (e) => {
        e.preventDefault();
        handleSubmitRegistration(nombre, dni, passwordPaciente, mail);
    }

    return (
        <Form onSubmit={onSubmit} className="d-flex flex-column rounded-4 p-4"
              style={{
                  border: '1px solid #cccccc',
                  width: '500px'
              }}>

            <h2 className="d-flex align-items-center border-bottom pb-4">Ingrese sus datos</h2>

            <div id="login-form-group" className="pb-4">

                <Form.Group className="mb-3 pt-2" controlId="formGroupName">
                    <Form.Label>Nombre completo</Form.Label>
                    <Form.Control
                        type="name"
                        placeholder="Ingrese su nombre y apellido"
                        onChange={(e => setNombre(e.currentTarget.value))}
                    />
                </Form.Group>

                <Form.Group className="mb-3 pt-2" controlId="formGroupDNI">
                    <Form.Label>Número de Documento</Form.Label>
                    <Form.Control
                        type="dni"
                        inputMode="numeric"
                        pattern="[0-9]*"
                        placeholder="Ingrese su DNI"
                        onChange={(e => setDni(e.currentTarget.value))}
                    />
                </Form.Group>

                <Form.Group className="mb-3 pt-2" controlId="formEmail">
                    <Form.Label>Correo Electrónico</Form.Label>
                    <Form.Control
                        type="email"
                        placeholder="Ingrese su correo electrónico"
                        onChange={(e => setMail(e.currentTarget.value))}
                    />
                </Form.Group>

                <Form.Group className="mb-3" controlId="formGroupPassword">
                    <Form.Label>Contraseña</Form.Label>
                    <Form.Control
                        type="password"
                        aria-describedby="passwordHelpBlock"
                        onChange={(e => setPasswordPaciente(e.currentTarget.value))}
                        placeholder="Ingrese su contraseña"/>
                </Form.Group>
                <Form.Text id="passwordHelpBlock" muted>
                    Su contraseña debe tener un mínimo de 6 caracteres, contener números y letras,
                    y no debe contener espacios, caracteres especiales, o emojis.
                </Form.Text>

            </div>
            <Button variant="primary" type="submit">Registrarse</Button>
        </Form>
    );
}

export default RegisterForm;
