import './index.css'
import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { createBrowserRouter, RouterProvider } from "react-router-dom"
import HomePage from './pages/HomePage.jsx'
import LoginPage from "./pages/LoginPage.jsx";
import Layout from "./pages/Layout.jsx";

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
            }
        ]
    }
])

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <RouterProvider router={router} />
  </StrictMode>,
)
