import React from "react";
import { useNavigate } from "react-router-dom";
import "./HomePage.css";

const HomePage = () => {
    const navigate = useNavigate();

    return (
        <div className="homepage-body">
            <div className="navbar-homepage">
                <div className="navbar-title">Courier Platform</div>
                <div className="navbar-buttons">
                    <button onClick={() => navigate("/login")} className="btn-login">
                        Login
                    </button>
                    <button onClick={() => navigate("/register")} className="btn-register">
                        Register
                    </button>
                </div>
            </div>

            <div className="homepage-content">
                <h1 className="homepage-title">Fast, Secure, and Reliable Deliveries</h1>
                <p className="homepage-description">
                    Welcome to the Courier Platform. Deliver packages efficiently and ensure customer satisfaction with our advanced tools and optimized routes.
                </p>
            </div>

            <div className="homepage-stats">
                <div className="stat-card">
                    <h3>Fast Deliveries</h3>
                    <p>99% of packages are delivered within 24 hours.</p>
                </div>
                <div className="stat-card">
                    <h3>Happy Customers</h3>
                    <p>Over 10,000 positive reviews from satisfied clients.</p>
                </div>
                <div className="stat-card">
                    <h3>Nationwide Coverage</h3>
                    <p>We reach over 300 cities and locations across the country.</p>
                </div>
            </div>

            <div className="homepage-info">
                <h2>Why Choose Us?</h2>
                <div className="info-grid">
                    <div className="info-card">
                        <h4>Real-Time Tracking</h4>
                        <p>Track your deliveries in real-time with our advanced GPS technology.</p>
                    </div>
                    <div className="info-card">
                        <h4>Optimized Routes</h4>
                        <p>Our system calculates the best routes to save time and fuel costs.</p>
                    </div>
                    <div className="info-card">
                        <h4>Secure Payments</h4>
                        <p>Enjoy seamless and secure payment solutions for every transaction.</p>
                    </div>
                    <div className="info-card">
                        <h4>Dedicated Support</h4>
                        <p>Our support team is available 24/7 to assist with any issues.</p>
                    </div>
                </div>
            </div>

            <div className="homepage-testimonials">
                <h2>What Our Customers Say</h2>
                <div className="testimonial-grid">
                    <div className="testimonial-card">
                        <p>"Amazing service! My package arrived on time and in perfect condition."</p>
                        <h4>- John D.</h4>
                    </div>
                    <div className="testimonial-card">
                        <p>"The tracking system is incredible. I knew exactly when my delivery would arrive."</p>
                        <h4>- Sarah W.</h4>
                    </div>
                    <div className="testimonial-card">
                        <p>"Highly recommend! Their support team helped me resolve an issue quickly."</p>
                        <h4>- Alex T.</h4>
                    </div>
                </div>
            </div>

            <footer className="homepage-footer">
                &copy; 2024 Courier Platform. All rights reserved.
            </footer>
        </div>
    );
};

export default HomePage;