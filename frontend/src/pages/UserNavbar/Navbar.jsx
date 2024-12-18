import React from "react";
import {FaUserCircle} from "react-icons/fa";
import {useNavigate} from "react-router-dom";
import "./Navbar.css";


const Navbar = ({role}) => {
    const navigate = useNavigate();

    const handleManagementClick = () => {
        navigate("/ControlManagement");
    };

    return (
        <div className="navbar-container">
            <h1 className="navbar-title">
                {role === "COURIER" ? "Hello Courier" : "Hello Manager"}
            </h1>
            <div className="navbar-icon-container">
                {role === "MANAGER" && (
                    <button
                        className="navbar-management-button"
                        onClick={handleManagementClick}
                    >
                        Management
                    </button>
                )}
                <FaUserCircle className="navbar-icon"/>
            </div>
        </div>
    );
};

export default Navbar;