import './index.css'
import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { createBrowserRouter, RouterProvider } from "react-router-dom"
import HomePage from './pages/HomePage.jsx'
import LoginPage from "./pages/LoginPage.jsx";
import RegisterPage from "./pages/RegisterPage.jsx";
import Layout from "./pages/Layout.jsx";
import MedicosDisponiblesPage from "./pages/MedicosDisponiblesPage.jsx";
import ReservasDeTurnosPage from "./pages/ReservasDeTurnosPage.jsx";
import TurnosDelMedicoPage from "./pages/TurnosDelMedicoPage.jsx";
import TodosLosMedicosPage from "./pages/TodosLosMedicosPage.jsx";
import MyProfilePage from "./pages/MyProfilePage.jsx";
import TodosLosUsuariosPage from './pages/TodosLosUsuariosPage.jsx';
import RestablecerContraseniaForm from "./components/forms/LoginForm/RestablecerContraseniaForm.jsx";
import CambiarContraseniaPage from "./pages/CambiarContraseñaPage.jsx";

const router = createBrowserRouter([
    {
        element: <Layout />,
        children: [
            {
                path: "/",
                element: <HomePage />,
            },
            {
                path: "/login",
                element: <LoginPage />
            },
            {
                path: "/register",
                element: <RegisterPage />
            },
            {
                path: "/medicosDisponibles",
                element: <MedicosDisponiblesPage />
            },
            {
                path: "/reservasDeTurnos",
                element: <ReservasDeTurnosPage />
            },
            {
                path: "/turnosDelMedico",
                element: <TurnosDelMedicoPage />
            },
            {
                path: "/todosLosMedicos",
                element: <TodosLosMedicosPage />
            },
            {
                path: "/todosLosUsuarios",
                element: <TodosLosUsuariosPage />
            },
            {
                path: "/RestablecerContrasenia",
                element: <RestablecerContraseniaForm />
            },
            {
                path: "/miPerfil",
                element: <MyProfilePage/>
            },
            {
                path: "/cambiar-contraseña",
                element: <CambiarContraseniaPage/>
            }
        ]
    }
])

createRoot(document.getElementById('root')).render(
    <StrictMode>
        <RouterProvider router={router} />
    </StrictMode>,
)

