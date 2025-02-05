# MedilaboSolutions

MedilaboSolutions is a **microservices-based** application designed to manage patient records and assess their diabetes risk based on medical data.


## Project Architecture

This project follows a **microservices architecture** with the framework **Spring Boot** for backend services and **Angular** for the frontend.
Each microservice has a distinct responsability:

**ms-backend-patient** : manages patient records (CRUD on MongoDB)
**ms-notes** : stores and manages patient medical notes (MongoDB)
**ms-diabetes-risk** : Evaluates diabetes risk using data from `ms-backend-patient` and `ms-notes`via Feign
**ms-gateway** : handles security, routing and authentication
**ms-frontend** : user interface built with Angular

## How to run the project

### 1 Prerequisites

- **Java 17**
- **Maven 3+**
- **MongoDB**
- **Node.js + Angular CLI**

### 2 Start the microservices

Each microservice must be started separately :
```bash
cd ms-backend-patient && mvn spring-boot:run
cd ms-notes && mvn spring-boot:run 
cd ms-diabetes-risk && mvn spring-boot:run 
cd ms-frontend && npm start
```
## Technical information

- **Database**: MongoDB (collections: `patients`and `notes`)
- **Backend Framework** : Spring Boot
- ** Frontend Framework** : Angular 
- **Security** : Spring Security 
- **Inter-service Communication** : OpenFeign

## Microservices

Please refer to the **README file for each microservices and frontend for details** 