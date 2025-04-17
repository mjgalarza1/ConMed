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

const router = createBrowserRouter([
    {
        element:<Layout/>,
        children:[
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
            }
        ]
    }
])

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <RouterProvider router={router} />
  </StrictMode>,
)
