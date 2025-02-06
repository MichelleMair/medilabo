# Microservice `ms-backend-patient`

This microservice manages **patient records**.

## Features

- Add new patient
- Update patient details
- Delete a patient 
- Retrieve all patients 
- Retrieve a specific patient by ID

## Technologies Used

- **Spring Boot Framework**
- **MongoDB**: collections: `patients`and `counters`

## MongoDB database

The MongoDB database consists of a `patients` collection for patients details and a `counters`collection for `patId`generation

## Insert initial data into MongoDB database
To insert the initial data into the MongoDB database:
For **patients** collection: 
 - enable the 'DataLoader' class located in the package 'com.medilabo.MedilaboSolutions.config'
 - uncomment the 'loadData' method
 - restart the application

Once the data has been inserted, you can comment out the method again to avoid reinserting data on every application startup

## How to run the project

### 1 Prerequisites

- **Java 17**
- **Maven 3+**
- **MongoDB**

### 2 Start the microservices

```bash
cd ms-backend-patient && mvn spring-boot:run
```
