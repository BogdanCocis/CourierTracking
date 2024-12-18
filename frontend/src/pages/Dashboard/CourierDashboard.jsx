import React, {useState, useEffect} from "react";
import {useLocation} from "react-router-dom";
import axios from "axios";
import Confetti from "react-confetti";
import {toast, ToastContainer} from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import Navbar from "../UserNavbar/Navbar";
import "./CourierDashboard.css";

const CourierDashboard = () => {
    const [deliveries, setDeliveries] = useState([]);
    const [showConfetti, setShowConfetti] = useState(false);
    const location = useLocation();
    const courierId = location.state?.courierId;
    const role = location.state?.role;

    const sortDeliveries = (deliveries) => {
        const order = ["NEW", "PENDING", "NOT_HOME", "DELIVERED", "DENIED"];
        return deliveries.sort(
            (a, b) =>
                order.indexOf(a.deliveryPackageStatus) - order.indexOf(b.deliveryPackageStatus)
        );
    };

    useEffect(() => {
        if (!courierId) {
            console.error("Courier ID not found. Please log in again.");
            return;
        }

        axios
            .get(`http://localhost:8080/api/packages/courier/${courierId}`)
            .then((response) => {
                if (response.data) {
                    const sortedDeliveries = sortDeliveries(response.data);
                    setDeliveries(sortedDeliveries);
                }
            })
            .catch((error) => {
                console.error("Error fetching deliveries:", error);
            });
    }, [courierId]);

    const handleAction = (deliveryId, action) => {
        const currentDelivery = deliveries.find((del) => del.idPackage === deliveryId);
        let updatedStatus = action;

        if (action === "NOT_HOME" && currentDelivery?.deliveryPackageStatus === "NOT_HOME") {
            updatedStatus = "DENIED";
        }

        const url = `http://localhost:8080/api/packages/${deliveryId}/status`;
        const data = {deliveryPackageStatus: updatedStatus};

        axios
            .put(url, data, {headers: {"Content-Type": "application/json"}})
            .then((response) => {
                const updatedDelivery = response.data;

                if (action === "DELIVERED") {
                    setShowConfetti(true);
                    toast.success("Congratulations! You have successfully delivered the package!");
                    setTimeout(() => setShowConfetti(false), 3000);
                }

                setDeliveries((prevDeliveries) =>
                    sortDeliveries(
                        prevDeliveries.map((del) =>
                            del.idPackage === deliveryId
                                ? {...del, deliveryPackageStatus: updatedDelivery.deliveryPackageStatus}
                                : del
                        )
                    )
                );
            })
            .catch((error) => {
                console.error(`Error performing action "${action}" on delivery:`, error.response || error);
            });
    };

    return (
        <div className="courier-dashboard-container">
            <Navbar role={role}/>

            {showConfetti && <Confetti/>}
            <ToastContainer/>

            {deliveries.length === 0 ? (
                <p className="no-deliveries">No deliveries assigned to you.</p>
            ) : (
                <div className="delivery-list">
                    {deliveries.map((delivery) => (
                        <div key={delivery.idPackage} className="delivery-card">
                            <p className="delivery-card-text">
                                <strong>Delivery Address:</strong> {delivery.deliveryAddress || "Not provided"}
                            </p>
                            <p className="delivery-card-text">
                                <strong>Email:</strong> {delivery.clientEmail || "Not provided"}
                            </p>
                            <p className="delivery-card-text">
                                <strong>Payment on Delivery:</strong> {delivery.payOnDelivery ? "Yes" : "No"}
                            </p>
                            <p className="delivery-card-text">
                                <strong>Status:</strong> {delivery.deliveryPackageStatus || "Unknown"}
                            </p>
                            <div className="delivery-actions">
                                <button
                                    className={`delivery-button in-progress ${
                                        delivery.deliveryPackageStatus !== "NEW" ? "disabled" : ""
                                    }`}
                                    onClick={() => handleAction(delivery.idPackage, "PENDING")}
                                    disabled={delivery.deliveryPackageStatus !== "NEW"}
                                >
                                    In Progress
                                </button>
                                <button
                                    className={`delivery-button delivered ${
                                        ["DELIVERED", "DENIED", "NEW"].includes(delivery.deliveryPackageStatus)
                                            ? "disabled"
                                            : ""
                                    }`}
                                    onClick={() => handleAction(delivery.idPackage, "DELIVERED")}
                                    disabled={["DELIVERED", "DENIED", "NEW"].includes(delivery.deliveryPackageStatus)}
                                >
                                    Delivered
                                </button>
                                <button
                                    className={`delivery-button not-home ${
                                        ["DELIVERED", "DENIED", "NEW"].includes(delivery.deliveryPackageStatus)
                                            ? "disabled"
                                            : ""
                                    }`}
                                    onClick={() => handleAction(delivery.idPackage, "NOT_HOME")}
                                    disabled={["DELIVERED", "DENIED", "NEW"].includes(delivery.deliveryPackageStatus)}
                                >
                                    Not Home
                                </button>
                                <button
                                    className={`delivery-button denied ${
                                        ["DELIVERED", "DENIED", "NEW"].includes(delivery.deliveryPackageStatus)
                                            ? "disabled"
                                            : ""
                                    }`}
                                    onClick={() => handleAction(delivery.idPackage, "DENIED")}
                                    disabled={["DELIVERED", "DENIED", "NEW"].includes(delivery.deliveryPackageStatus)}
                                >
                                    Denied
                                </button>
                            </div>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
};

export default CourierDashboard;