# Courier Management System

## Overview
The Courier Management System is a comprehensive platform designed to streamline the process of managing couriers and their delivery packages. The system provides features for courier registration, package assignment, delivery status tracking, and more. It also includes a secure login mechanism and role-based access control for couriers and managers.

## Features
- **Courier Registration**: Register couriers with their details and assign roles.
- **Package Management**: Create, update, and track delivery packages.
- **Delivery Status Tracking**: Update and view the status of packages (NEW, DELIVERED, NOT_HOME, DENIED, PENDING).
- **User Authentication**: Secure login for couriers and managers.
- **Email Notifications**: Send email notifications to clients regarding their package status.

## Technologies Used
- **Backend**: 
  - Java
  - Spring Boot
  - Hibernate
  - MySQL
  - Spring Security
- **Frontend**: 
  - React
  - Axios
  - React Router
- **Other Tools**: 
  - Postman 

## Installation and Setup
1. **Clone the repository**:
    ```bash
    git clone https://github.com/BogdanCocis/CourierTracking.git
    cd project-repo
    ```

2. **Backend Setup**:
    - Ensure you have Java and Maven installed.
    - Update the `application.properties` file with your MySQL credentials and email settings.
    - Run the backend application:
      ```bash
      mvn spring-boot:run
      ```

3. **Frontend Setup**:
    - Ensure you have Node.js and npm installed.
    - Navigate to the frontend directory and install dependencies:
      ```bash
      cd frontend
      npm install
      ```
    - Start the frontend application:
      ```bash
      npm start
      ```



## Usage
- **Login**: Access the login page and enter your credentials to log in.
<div align="center">
  <img width="700" alt="log in" src="https://github.com/user-attachments/assets/12eda4ae-a644-49e3-98ae-f02d33554004" />
</div>
<br><br> 

- **Register**: Use the registration page to create a new courier account.
<div align="center"> 
  <img width="700" alt="Register" src="https://github.com/user-attachments/assets/a5dd1b51-cdd6-4194-aa24-35e8f3f107dd" />
</div>
<br><br> 

- **Dashboard**: 
  - Couriers can view and manage their assigned packages.
<div align="center"> 
  <img width="700" alt="courier_dashboard" src="https://github.com/user-attachments/assets/8780db75-29e9-41d1-84f7-d207ff4619d4" />
</div>
<br><br> 

  - Managers can view all couriers, manage packages, and view delivery statistics.
<div align="center"> 
  <img width="700" alt="manager_dashboard" src="https://github.com/user-attachments/assets/bed7e330-7a7c-4a3c-83fa-a12efde4af30" />
</div>
<br><br> 

<div align="center">
  <img width="700" alt="Control management" src="https://github.com/user-attachments/assets/bdfe84f1-b558-43d5-bdef-439e4cd48268" />
</div>
<br><br>

<div align="center"> 
  <img width="700" alt="add_package" src="https://github.com/user-attachments/assets/0e4c7015-6145-4be0-bfa0-2f07d5dd61a9" />
</div>
<br><br>

<div align="center">
  <img width="700" alt="edit_package" src="https://github.com/user-attachments/assets/3c23b25d-8613-4359-952e-128887fc08d2" />
</div>



 ## Database Schema

 The database schema for the Package Tracking System is visually represented below:

<img width="720" alt="db_courier" src="https://github.com/user-attachments/assets/d82a0c81-9086-4bce-ba0e-26a1fd920abb" />

## Diagrams
### Use Case Diagram

<img width="934" alt="Screenshot 2025-01-10 at 17 19 39" src="https://github.com/user-attachments/assets/d29a07e2-092a-4a2b-af7b-d8cc3e238817" />


### Flow Chart

![Flow](https://github.com/user-attachments/assets/852c9ed6-468a-4d33-a7b7-b769be416dbd)



