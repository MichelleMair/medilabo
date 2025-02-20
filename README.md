# MedilaboSolutions

MedilaboSolutions is a **microservices-based application** designed to manage patient records and assess diabetes risk using patient data and medical notes.
This app helps healthcare professionals evaluate the likelihood of diabetes based on medical records, age, gender and specific triggers.

---

## Table of Contents

1. [Project Overview](#project-overview)
2. [Built With](#built-with)
3. [Architecture Overview](#architecture-overview)
4. [Getting Started](#getting-started)
5. [How to Run the Application](#how-to-run-the-application)
6. [Authentication](#authentication)
7. [Technical Information](#technical-information)
8. [Green Code Practices](#green-code-practices)
9. [Microservices Documentation](#microservices-documentation)

---

## Project Overview

MedilaboSolutions helps medical professionals monitor and manage patient health data with an emphasis on diabetes risk evaluation.
It uses a combination of patients records and medical notes to assess the likelihood of diabetes. 

---

## Built With

### **Frontend**
- **Angular** : Provides a dynamic and responsive user interface

### **Backend**
- **Java 17** : Backend development using the Java programming language.
- **Spring Boot** : Framework for building the microservices.
- **Spring cloud Gateway** : Manages routing and authentication between microservices.
- **Spring Security** : Handles authentication and authorization
- **Spring Data** : Simplifies data interaction with databases.
- **OpenFeign** : Facilitates inter-service communication.

### **Databases**
- **MySQL** : MySQL database for storing patient's details (name,age gender, address, number phone)
- **MongoDB** :  NoSQL database for storing patient's medical note data. 

---

## Architecture Overview

MedilaboSOlutions is structured as a **microservices architecture** for better scalability and flexibility. 

**ms-backend-patient** : manages patient data (CRUD operations on MySQL)
**ms-notes** : stores and manages patient medical notes (MongoDB)
**ms-diabetes-risk** : Evaluates diabetes risk using data from `ms-backend-patient` and `ms-notes`via Feign
**ms-gateway** : handles routing, authentication and security configurations
**ms-frontend** : Angular-based user interface for interacting with the system

--- 

## Getting Started

Follow these instructions to set up the project on your local machine. 

### Prerequisites

Ensure you have the followingtools installed:

- **Java 17**
- **Maven 3+**
- **MySQL** (running locally for `ms-backend-patient`)
- **MongoDB** (running locally for `ms-notes`)
- **Node.js + Angular CLI**

---

## How to Run the Application

### Clone the Project

Open your terminal and run : 

```bash
git clone https://github.com/MichelleMair/medilabo.git
cd MedilaboSolutions
```

### Install Frontend Dependencies

Navigate to the frontend directory and install the required packages

```bash
cd ms-frontend
npm install
```

### Insert Initial Data into MongoDB 

Be careful, if you use Docker to start the microservices, **no manual data insertions is required**.

Please refer to **Start the microservices using Docker**

To insert initial data into MySQL database, table **patients** and MongoDB database, collection **notes**:

1. **Patients**
 - navigate to the `ms-backend-patient/database/` directory
 - import the init.sql script into your MySQL database:
 
```bash 
mysql -u root -p medilabo_patients < database/init.sql
```
 - This will create patients table and insert initial patients' data. 

2. **Notes** 
 - Ensure MongoDB is running locally on you computer
 - Navigate to the `ms-notes/` directory and run:
 
```bash 
mongosh < init-mongo.js
```

 - this will insert medical records in `notes` collections. 

---

### Start the microservices using Docker

All microservices can be started using Docker for easier management.
When using DOcker Compose to start microservices, **no manual data insertions is required**

The **docker-compose.yml** file ensures that all necessary databases are initialized with their respective datasets automatically. 

- MySQL (ms-backend-patient): The init.sql script is executed on startup to set up the patients table and populate it with sample data. 
- MongoDB (ms-notes): the init-mongo.js script runs automatically when the MongoDB container starts, inserting initial medical notes into the database. 

#### How to run with **docker-compose.yml**

1. **Ensure Docker is running on your machine.**

2. **Build the Docker images for all microservices:**

```bash
docker-compose build
```

3. **Start all microservices using Docker compose:**
```bash
docker-compose up
```

4. **Alternative commands to build and start all microservices using Docker compose:**
```bash
docker-compose up --build
```

Once all microservices are running, access the application at [http://localhost:4200/ms-frontend/auth](http://localhost:4200/ms-frontend/auth)

---

## Authentication

To access the application, use the following credentials: 

- **Username:** `user`
- **Password:** `password`

---

## Technical information

- **Databases**: MySQl (Table : `patients`) and MongoDB (collections: `notes`)
- **Backend Framework** : Spring Boot (Java 17)
- **Frontend Framework** : Angular 
- **Security** : Spring Security 
- **Inter-service Communication** : OpenFeign
- **API Gateway** : Spring Cloud Gateway for routing and security 

---

## Green code practices

To make our application more sustainable and energy-efficient,the following **Green Code** practices have been applied or recommended

### 1. Applied Green Code Principles

#### **Minimizing Unused Features**

- **Simplified Risk Evaluation Logic:**
  - The `DiabetesRiskEvaluationService` uses a concise filtering method with Java Streams to process notes, avoiding unnecessary iterations.
  - The `TriggerTerms` utility class prevents instantiation, ensuring no redundant object creation.

#### **Optimizing Data Storage**

- **MySQL Usage:**
  - Only essential fields are stored. Optional fields like `address` and `phoneNumber` are nullable and not mandatory.
  - Efficient querying in `PatientRepository` with methods like `existsByFirstNameAndLastNameAndDateOfBirth` to avoid duplicate patient entries.

#### **Frontend Optimization**
- Implemented lazy loading for Angular modules to minimize resource consumption.
- Optimized static assets to reduce load times.

#### **Pagination Over infinite Scrolling**
- Implements pagination in patient lists to limit the amount of data loaded at once.

  
#### **Efficient Logging**
- **Use of SLF4J Logger:**
  - Consistent use of `Logger` instead of `System.out.println` for logging in `PatientService`, `LoginController`, and other services.
  - Example:
    ```java
    private static final Logger logger = LoggerFactory.getLogger(PatientService.class);
    logger.info("Adding patient : {}", patient);
    ```

#### **Resource Efficiency**
- **OpenFeign for Inter-service Communication:**
  - Efficiently manages API calls between microservices, reducing overhead.
- **Token-Based Authentication:**
  - JWT tokens are used for secure and efficient stateless authentication across services.

#### **Code Reusability and Modularity**
- **Utility Classes and Configuration Files:**
  - `TriggerTerms` as a reusable utility class.
  - Security configurations are modular and consistently applied across microservices.


---

### 2. Future Improvments for Green Code

#### **Microservices Caching**

- Implement caching of microservice results to reduce redundant calls and processing

#### **Reduce logs in Production**
- Configure logging levels to minimize unnecessary log entries in production, reducing I/O operations. 

#### **Compress API Response**
- Enable JSON response compression to reduce network data transfer.


--- 

## Microservices Documentation

Each microservice has a dedicated **README** :

- [ms-backend-patient README](./MedilaboSolutions/ms-backend-patient/README.md)
- [ms-notes README](./MedilaboSolutions/ms-notes/README.md)
- [ms-diabetes-risk README](./MedilaboSolutions/ms-diabetes-risk/README.md)
- [ms-gateway README](./MedilaboSolutions/ms-gateway/README.md)
- [ms-frontend README](./MedilaboSolutions/ms-frontend/README.md)
