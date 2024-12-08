import React, {useState, useEffect} from "react";
import {useLocation} from "react-router-dom";
import axios from "axios";
import Confetti from "react-confetti";
import {toast, ToastContainer} from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "./ManagerDashboard.css";

const ManagerDashboard = () => {
    const [packages, setPackages] = useState([]);
    const [showConfetti, setShowConfetti] = useState(false);
    const location = useLocation();
    const courierId = location.state?.courierId;

    const sortPackages = (packages) => {
        const order = ["NEW", "PENDING", "NOT_HOME", "DELIVERED", "DENIED"];
        return packages.sort(
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
                    const sortedPackages = sortPackages(response.data);
                    setPackages(sortedPackages);
                }
            })
            .catch((error) => {
                console.error("Error fetching packages:", error);
            });
    }, [courierId]);

    const handleAction = (packageId, action) => {
        const currentPackage = packages.find((pkg) => pkg.idPackage === packageId);
        let updatedStatus = action;

        if (action === "NOT_HOME" && currentPackage?.deliveryPackageStatus === "NOT_HOME") {
            updatedStatus = "DENIED";
        }

        const url = `http://localhost:8080/api/packages/${packageId}/status`;
        const data = {deliveryPackageStatus: updatedStatus};

        axios
            .put(url, data, {headers: {"Content-Type": "application/json"}})
            .then((response) => {
                const updatedPackage = response.data;

                if (action === "DELIVERED") {
                    setShowConfetti(true);
                    toast.success("Congratulations! The package has been successfully delivered!");
                    setTimeout(() => setShowConfetti(false), 3000);
                }

                setPackages((prevPackages) =>
                    sortPackages(
                        prevPackages.map((pkg) =>
                            pkg.idPackage === packageId
                                ? {...pkg, deliveryPackageStatus: updatedPackage.deliveryPackageStatus}
                                : pkg
                        )
                    )
                );
            })
            .catch((error) => {
                console.error(`Error performing action "${action}" on package:`, error.response || error);
            });
    };

    return (
        <div className="manager-dashboard-container">
            <h1 className="manager-dashboard-title">Hello Manager</h1>

            {showConfetti && <Confetti/>}

            <ToastContainer/>

            {packages.length === 0 ? (
                <p className="no-packages">No packages available for this courier.</p>
            ) : (
                <div className="package-list">
                    {packages.map((pkg) => (
                        <div key={pkg.idPackage} className="package-card">
                            <p className="package-card-text">
                                <strong>Delivery Address:</strong> {pkg.deliveryAddress || "Not provided"}
                            </p>
                            <p className="package-card-text">
                                <strong>Email:</strong> {pkg.clientEmail || "Not provided"}
                            </p>
                            <p className="package-card-text">
                                <strong>Payment on Delivery:</strong> {pkg.payOnDelivery ? "Yes" : "No"}
                            </p>
                            <p className="package-card-text">
                                <strong>Status:</strong> {pkg.deliveryPackageStatus || "Unknown"}
                            </p>
                            <div className="package-actions">
                                <button
                                    className={`package-button in-progress ${
                                        pkg.deliveryPackageStatus !== "NEW" ? "disabled" : ""
                                    }`}
                                    onClick={() => handleAction(pkg.idPackage, "PENDING")}
                                    disabled={pkg.deliveryPackageStatus !== "NEW"}
                                >
                                    In Progress
                                </button>
                                <button
                                    className={`package-button delivered ${
                                        ["DELIVERED", "DENIED", "NEW"].includes(pkg.deliveryPackageStatus)
                                            ? "disabled"
                                            : ""
                                    }`}
                                    onClick={() => handleAction(pkg.idPackage, "DELIVERED")}
                                    disabled={["DELIVERED", "DENIED", "NEW"].includes(pkg.deliveryPackageStatus)}
                                >
                                    Delivered
                                </button>
                                <button
                                    className={`package-button not-home ${
                                        ["DELIVERED", "DENIED", "NEW"].includes(pkg.deliveryPackageStatus)
                                            ? "disabled"
                                            : ""
                                    }`}
                                    onClick={() => handleAction(pkg.idPackage, "NOT_HOME")}
                                    disabled={["DELIVERED", "DENIED", "NEW"].includes(pkg.deliveryPackageStatus)}
                                >
                                    Not Home
                                </button>
                                <button
                                    className={`package-button denied ${
                                        ["DELIVERED", "DENIED", "NEW"].includes(pkg.deliveryPackageStatus)
                                            ? "disabled"
                                            : ""
                                    }`}
                                    onClick={() => handleAction(pkg.idPackage, "DENIED")}
                                    disabled={["DELIVERED", "DENIED", "NEW"].includes(pkg.deliveryPackageStatus)}
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

export default ManagerDashboard;