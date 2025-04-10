import './App.css'
import {useEffect, useState} from "react";

function App() {

    const [mensaje, setMensaje] = useState('Cargando...');

    useEffect(() => {
        fetch('http://localhost:8080/')
            .then(res => res.text())
            .then(data => setMensaje(data))
            .catch(err => setMensaje("Error al conectar con el backend"));
    }, [])

    useEffect(() => {

    }, []);

  return (
    <>
        <h1>{mensaje}</h1>
    </>
  )
}

export default App
