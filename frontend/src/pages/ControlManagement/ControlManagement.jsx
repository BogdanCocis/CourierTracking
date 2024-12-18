import React, {useState, useEffect} from "react";
import Modal from "react-modal";
import axios from "axios";
import "./ControlManagement.css";

Modal.setAppElement("#root");

const ControlManagement = () => {
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [isPackagesModalOpen, setIsPackagesModalOpen] = useState(false);
    const [isEditModalOpen, setIsEditModalOpen] = useState(false);
    const [deliveryAddress, setDeliveryAddress] = useState("");
    const [payOnDelivery, setPayOnDelivery] = useState(false);
    const [clientEmail, setClientEmail] = useState("");
    const [packages, setPackages] = useState([]);
    const [couriers, setCouriers] = useState([]);
    const [selectedPackageId, setSelectedPackageId] = useState(null);
    const [editDeliveryAddress, setEditDeliveryAddress] = useState("");
    const [editCourierId, setEditCourierId] = useState("");

    const openModal = () => {
        setDeliveryAddress("");
        setPayOnDelivery(false);
        setClientEmail("");
        setIsModalOpen(true);
    };

    const closeModal = () => setIsModalOpen(false);

    const openPackagesModal = async () => {
        try {
            const response = await axios.get("http://localhost:8080/api/packages/with-courier");
            setPackages(response.data);
            setIsPackagesModalOpen(true);
        } catch (error) {
            console.error("Error fetching packages:", error);
        }
    };

    const closePackagesModal = () => setIsPackagesModalOpen(false);

    const fetchCouriers = async () => {
        try {
            const response = await axios.get("http://localhost:8080/api/couriers");
            setCouriers(response.data);
        } catch (error) {
            console.error("Error fetching couriers:", error);
        }
    };

    const openEditModal = (pkg) => {
        setSelectedPackageId(pkg.idPackage);
        setEditDeliveryAddress(pkg.deliveryAddress);
        setEditCourierId(pkg.courierId || "");
        fetchCouriers();
        setIsEditModalOpen(true);
    };

    const closeEditModal = () => setIsEditModalOpen(false);

    const handleSubmit = async (e) => {
        e.preventDefault();

        const packageData = {
            deliveryAddress,
            payOnDelivery,
            clientEmail,
        };

        try {
            await axios.post("http://localhost:8080/api/packages", packageData, {
                headers: {"Content-Type": "application/json"},
            });
            closeModal();
        } catch (error) {
            console.error("Error adding package:", error);
        }
    };

    const handleUpdatePackage = async (e) => {
        e.preventDefault();

        const updatedPackage = {
            deliveryAddress: editDeliveryAddress,
            courierId: editCourierId,
        };

        try {
            await axios.put(`http://localhost:8080/api/packages/${selectedPackageId}`, updatedPackage);
            closeEditModal();
            openPackagesModal();
        } catch (error) {
            console.error("Error updating package:", error);
        }
    };

    return (
        <div className="control-management-container">
            <h1>Control Management</h1>
            <div className="button-grid">
                <div className="button-card">
                    <button className="button-card-button" onClick={openModal}>
                        Add Package
                    </button>
                </div>
                <div className="button-card">
                    <button className="button-card-button" onClick={openPackagesModal}>
                        View Packages
                    </button>
                </div>
            </div>

            <Modal
                isOpen={isModalOpen}
                onRequestClose={closeModal}
                contentLabel="Add Package Modal"
                className="modal"
                overlayClassName="overlay"
            >
                <h2>Add a New Package</h2>
                <form onSubmit={handleSubmit} className="add-package-form">
                    <label htmlFor="deliveryAddress">Delivery Address:</label>
                    <input
                        type="text"
                        id="deliveryAddress"
                        value={deliveryAddress}
                        onChange={(e) => setDeliveryAddress(e.target.value)}
                        required
                    />
                    <label htmlFor="payOnDelivery">Pay on Delivery:</label>
                    <select
                        id="payOnDelivery"
                        value={payOnDelivery}
                        onChange={(e) => setPayOnDelivery(e.target.value === "true")}
                        required
                    >
                        <option value="false">No</option>
                        <option value="true">Yes</option>
                    </select>
                    <label htmlFor="clientEmail">Client Email:</label>
                    <input
                        type="email"
                        id="clientEmail"
                        value={clientEmail}
                        onChange={(e) => setClientEmail(e.target.value)}
                        required
                    />
                    <div className="form-buttons">
                        <button type="submit" className="submit-button">Submit</button>
                        <button type="button" className="cancel-button" onClick={closeModal}>Cancel</button>
                    </div>
                </form>
            </Modal>

            <Modal
                isOpen={isPackagesModalOpen}
                onRequestClose={closePackagesModal}
                contentLabel="View Packages Modal"
                className="modal"
                overlayClassName="overlay"
            >
                <h2>All Packages</h2>
                <ul className="packages-list">
                    {packages.map((pkg) => (
                        <li key={pkg.idPackage} className="package-item" onClick={() => openEditModal(pkg)}>
                            <p><strong>Delivery Address:</strong> {pkg.deliveryAddress}</p>
                            <p><strong>Assigned Courier:</strong> {pkg.courierName || "Not Assigned"}</p>
                        </li>
                    ))}
                </ul>
                <button className="close-packages-button" onClick={closePackagesModal}>Close</button>
            </Modal>

            <Modal
                isOpen={isEditModalOpen}
                onRequestClose={closeEditModal}
                contentLabel="Edit Package Modal"
                className="modal"
                overlayClassName="overlay"
            >
                <h2>Edit Package</h2>
                <form onSubmit={handleUpdatePackage} className="edit-package-form">
                    <label htmlFor="editDeliveryAddress">Delivery Address:</label>
                    <input
                        type="text"
                        id="editDeliveryAddress"
                        value={editDeliveryAddress}
                        onChange={(e) => setEditDeliveryAddress(e.target.value)}
                        required
                    />
                    <label htmlFor="editCourier">Assign Courier:</label>
                    <select
                        id="editCourier"
                        value={editCourierId}
                        onChange={(e) => setEditCourierId(e.target.value)}
                        required
                    >
                        <option value="">Select Courier</option>
                        {couriers.map((courier) => (
                            <option key={courier.idCourier} value={courier.idCourier}>
                                {courier.name}
                            </option>
                        ))}
                    </select>
                    <div className="form-buttons">
                        <button type="submit" className="submit-button">Save</button>
                        <button type="button" className="cancel-button" onClick={closeEditModal}>Cancel</button>
                    </div>
                </form>
            </Modal>
        </div>
    );
};

export default ControlManagement;