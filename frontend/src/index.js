import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import reportWebVitals from './reportWebVitals';
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import Login from "./pages/Autentification/Login";
import Register from "./pages/Autentification/Register";
import HomePage from "./pages/HomePage/HomePage";
import ManagerDashboard from "./pages/Dashboard/ManagerDashboard";
import CourierDashboard from "./pages/Dashboard/CourierDashboard";
import ControlManagement from "./pages/ControlManagement/ControlManagement";

const router = createBrowserRouter([
    {
        path: "/",
        element: <HomePage/>
    },
    {
        path: "/login",
        element: <Login/>,
    },
    {
        path: "/register",
        element: <Register/>,
    },
    {
        path: "/managerdashboard",
        element: <ManagerDashboard/>
    },
    {
        path: "/courierdashboard",
        element: <CourierDashboard/>
    },
    {
        path: "controlmanagement",
        element: <ControlManagement/>
    }
]);

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        <RouterProvider router={router}/>
    </React.StrictMode>
);
reportWebVitals();
