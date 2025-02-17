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
- **MySQL** : Stores patient's details

## MySQL database

The MySQL database consists of a `patients` table that contains patients details.

## Insert initial data into MySQL database

1. Navigate to the `ms-backend-patient/database/` directory.
2. Import the **init.sql** script into your MySQL database:
```bash
mysql -u root -p medilabo_patients < database/init.sql
```
This will create the patients table and insert initial patient data.

## How to run the project

### 1 Prerequisites

- **Java 17**
- **Maven 3+**
- **MySQL**

### 2 Start the microservices

```bash
cd ms-backend-patient && mvn spring-boot:run
```
