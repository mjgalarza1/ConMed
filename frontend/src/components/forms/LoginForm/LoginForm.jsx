import {Button, Form} from "react-bootstrap";
import {useState} from "react";
import {Link} from "react-router-dom";

const LoginForm = ({handleSubmitLogin}) => {

    const [dni, setDni] = useState("");
    const [password, setPassword] = useState("");

    const onSubmit = (e) => {
        e.preventDefault();
        handleSubmitLogin(dni, password);
    }

    return (
        <Form onSubmit={onSubmit} className="d-flex flex-column rounded-4 p-4"
              style={{
                  border: '1px solid #cccccc',
                  width: '500px'
              }}>

            <h2 className="d-flex align-items-center border-bottom pb-4">Ingrese sus datos</h2>

            <div id="login-form-group">

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

                <Form.Group className="mb-3" controlId="formGroupPassword">
                    <Form.Label>Contraseña</Form.Label>
                    <Form.Control
                        type="password"
                        onChange={(e => setPassword(e.currentTarget.value))}
                        placeholder="Ingrese su contraseña"/>
                </Form.Group>

            </div>

            <div className="d-flex justify-content-between align-items-center mb-3">
                <Link to="/RestablecerContrasenia" className="text-decoration-none text-primary">
                    ¿Olvidaste tu contraseña?
                </Link>
            </div>

            <Button variant="primary" type="submit">Iniciar sesión</Button>
        </Form>
    );
}

export default LoginForm;
