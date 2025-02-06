# Microservice `ms-notes`

This microservice is responsible for **managing patient medical notes**.

## Features

- Add a medical note
- Retrieve all notes for a patient 

## Technologies Used

- **Spring Boot Framework**
- **MongoDB**: collections: `notes`

## MongoDB database

The MongoDB database consists of a `notes` collection for medical notes. 

## Insert initial data into MongoDB database
To insert the initial data into the MongoDB database:
For **notes collection**: 

 - enable the **DataLoader** class located in the package `com.medilabo.notes.config`
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
cd ms-notes && mvn spring-boot:run
```
