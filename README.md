# MedilaboSolutions

MedilaboSolutions is a **microservices-based application** designed to manage patient records and assess diabetes risk using patient data and medical notes.
This app helps healthcare professionals evaluate the likelihood of diabetes based on medical records, age gender and specific triggers.

---

## Table of Contents

1. [Project Overview] (#project-overview)
2. [Built With] (#built-with)
3. [Architecture Overview] (#architecture-overview)
4. [Getting Started] (#getting-started)
5. [How to Run the Application] (#how-to-run-the-application)
6. [Authentication] (#authentication)
7. [Technical Information] (#technical-information)
8. [Green Code Practices] (#green-code-practices)
9. [Microservices Documentation] (#microservices-documentation)

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
- **MongoDB** :  NoSQL database for storing patient and medical note data. 

---

## Architecture Overview

MedilaboSOlutions is structured as a **microservices architecture** for better scalability and flexibility. 

**ms-backend-patient** : manages patient data (CRUD operations on MongoDB)
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
- **MongoDB** (running locally or via Atlas MongoDB)
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

To insert initial data into MongoDB for **patients** and **notes**:

1. **Patients Collection**
 - enable the 'DataLoader' class located in the package 'com.medilabo.msbackendpatient.config'
 - uncomment the 'loadData()' method
 - Run the backend service : 
  ```bash 
  cd ms-backend-patientmvn spring-boot:run
  ```
 - Once the data is inserted, you can comment out the method and the **Configuration annotation** again to avoid reinserting data on every application startup

2. **Notes Collection** 
 - Follow the same steps for `ms-notes`: enable the 'DataLoader' class located in the package 'com.medilabo.notes.config'
 - uncomment the 'loadData' method
 - restart the application

Once the data has been inserted, comment the method and the **Configuration annotation** again to avoid reinserting data on every application startup

---

### Start the microservices

Each microservice must be started individually: 

```bash
cd ms-backend-patient && mvn spring-boot:run
cd ms-notes && mvn spring-boot:run 
cd ms-diabetes-risk && mvn spring-boot:run
cd ms-gateway && mvn spring-boot:run 
cd ms-frontend && npm start
```

Once all microservices are running, access the application at [http://localhost:4200/ms-frontend/auth](http://localhost:4200/ms-frontend/auth)

---

## Authentication

To access the application, use the following credentials: 

- **Username:** `user`
- **Password:** `password`

---

## Technical information

- **Database**: MongoDB (collections: `patients`and `notes`)
- **Backend Framework** : Spring Boot (Java 17)
- **Frontend Framework** : Angular 
- **Security** : Spring Security 
- **Inter-service Communication** : OpenFeign
- **API Gateway** : Spring Cloud Gateway for routing and security 

---

## Green code practices

To make our application more sustainable and energy-efficient,the following **Green Code** practices have been applied or recommended

### Applied Green Code Principles

1. **Minimizing Unused Features**
- Remove redundant features and simplified risk evaluation logic to avoid unnecessary processing.

2. **Optimizing Data Storage** 
- Stored only essential patient and medical note date in MongoDB 
- Optimized queries to reduce data retrieval time and energy consumption

3. **Frontend Optimization**
- Implemented lazy loading for Angular modules to minimize resource consumption.
- Optimized static assets to reduce load times.

4. **Pagination Over infinite Scrolling**
- Implements pagination in patient lists to limit the amount of data loaded at once.  

### Future Improvments for Green Code

1. **Microservices Caching**
- Implement caching of microservice results to reduce redundant calls and processing

2. **Reduce logs in Production**
- Configure logging levels to minimize unnecessary log entries in production, reducing I/O operations. 

3. **Compress API Response**
- Enable JSON response compression to reduce network data transfer.

--- 

## Microservices Documentation

Each microservice has a dedicated **README** :

- [ms-backend-patient README] (./ms-backend-patient/README.md)
- [ms-notes README] (./ms-notes/README.md)
- [ms-diabetes-risk README] (./ms-diabetes-risk/README.md)
- [ms-gateway README] (./ms-gateway/README.md)
- [ms-frontend README] (./ms-frontend/README.md)
