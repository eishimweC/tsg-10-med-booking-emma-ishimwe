## Medical Appointment Booking Portal

This repository contains a Medical Appointment Booking System with the following components:

- Frontend: A React application for the user interface.
- Backend: A Spring Boot application for managing the business logic and APIs.
- Database: PostgreSQL for data storage.

Features

- User-friendly React-based frontend.
- Spring Boot RESTful API for backend functionalities.
- PostgreSQL database integration.
- Dockerized setup for seamless deployment.
- Pre-populated database with seed data for testing.

Setup and Installation Instructions
Prerequisites
Ensure the following tools are installed on your system:

Java 17: Download Java
Node.js: Download Node.js
Docker: Download Docker
Docker Compose: Comes with Docker Desktop.

Running the Application Locally in Docker Containers

1. Clone the Repository:
   git clone https://github.com/eishimweC/tsg-10-med-booking-emma-ishimwe.git

2. Build the Docker Images and Start Services: Run the following command from the project root directory (where the docker-compose.yml file is located):

docker-compose up --build

3. Access Services:

- Frontend: http://localhost/3000
- Backend API: http://localhost:8080
- Database: PostgreSQL is accessible on localhost:5432 with:
  Username: admin
  Password: admin

4. Stop Services

docker-compose down

Database Schema and Seed Data

Database Schema
Specializations:

    id: Primary key
    name: Name of specialization (e.g., "Cardiology").

    Doctors:

    id: Primary key
    firstName: Doctor's first name.
    lastName: Doctor's last name.
    specializationId: Foreign key referencing specialization.

    Patients:

    id: Primary key
    firstName: Patient's first name.
    lastName: Patient's last name.
    email: Patient's email.
    dob: Patient's date of birth.

    Appointments:

    id: Primary key
    doctorId: Foreign key referencing doctor.
    patientId: Foreign key referencing patient.
    dateTime: Appointment date and time.
    appointmentStatus: Enum (CONFIRMED, PENDING, CANCELLED).
    visitType: Enum (IN_PERSON or TELEHEALTH).

Seed Data

The application comes with preloaded data defined in the ConfigData class:

    - Specializations: Cardiology, Pediatrics, Dermatology, Orthopedics, Neurology.
    - Doctors: 10 doctors with various specializations.
    - Patients: 10 patients with different profiles.
    - Appointments: Pre-scheduled appointments.

Docker Commands
Building and Running the Containers
Build and start all services: : docker-compose up --build

Run services in detached mode: docker-compose up -d

Stop and remove all containers: docker-compose down

Access Docker Logs

Application Components

Frontend (React)

    The frontend is built using React and can be accessed at http://localhost.
    It interacts with the backend through RESTful APIs.

Backend (Spring Boot)
The backend provides API endpoints for managing appointments, patients, doctors, and specializations.
Environment variables for the backend are configured in the docker-compose.yml file.

Database (PostgreSQL)
The database is pre-configured with seed data.
Access credentials:
Username: admin
Password: admin
Database: medicaldb

Troubleshooting
Common Issues

- Port Conflict: If a service fails to start due to a port conflict, update the ports in the docker-compose.yml file.

- Docker Build Failure: Ensure the Dockerfile paths and configurations are correct.

- Database Connection Error: Verify that the SPRING_DATASOURCE_URL in the backend environment variables points to the db service in Docker Compose.
