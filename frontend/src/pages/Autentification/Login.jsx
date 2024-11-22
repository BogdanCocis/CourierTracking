import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "./Login.css";

const Login = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const navigate = useNavigate();

    const handleSubmit = async (event) => {
        event.preventDefault();

        const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        if (!emailRegex.test(email)) {
            setError("Invalid email or password!");
            return;
        }

        const passwordRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,12}$/;
        if (!passwordRegex.test(password)) {
            setError("Invalid email or password!");
            return;
        }

        try {
            const response = await axios.post(
                "http://localhost:8080/api/login",
                {
                    email,
                    password,
                },
                { withCredentials: true }
            );

            const role = response.data;
            setError("Successful authentication");

            if (role === "MANAGER") {
                navigate("/ManagerDashboard");
            } else if (role === "COURIER") {
                navigate("/CourierDashboard");
            }
        } catch (error) {
            setError("Invalid email or password!");
        }
    };

    return (
        <div id="login-body">
            <div className="navbar-login">Log in</div>
            <form className="login-form" onSubmit={handleSubmit}>
                <label className="login-label" htmlFor="email">
                    Email
                </label>
                <input
                    className="login-input"
                    type="text"
                    id="email"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                />
                <label className="login-label" htmlFor="password">
                    Password
                </label>
                <input
                    type="password"
                    id="password"
                    className="login-input"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />
                {error && <p className="error">{error}</p>}
                <div className="input-button-class">
                    <button type="submit" className="input-button pretty-button">
                        Log in
                    </button>
                </div>
                <p className="center-link">
                    <a className="login-a" href="#" onClick={() => navigate("/Register")}>
                        Don't have an account?
                    </a>
                </p>
            </form>
        </div>
    );
};

export default Login;