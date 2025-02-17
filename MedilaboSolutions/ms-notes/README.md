# Microservice `ms-notes`

This microservice is responsible for **managing patient medical notes**.

## Features

- Add a medical note
- Retrieve all notes for a patient 

## Technologies Used

- **Spring Boot Framework**
- **MongoDB**: collections: `notes` (stores notes in NoSQL collection)

## MongoDB database

The MongoDB database consists of a `notes` collection for medical notes. 

## Insert initial data into MongoDB database

1. Ensure MongoDB is running.
2. Navigate to the `ms-notes/` directory.
3. Run:
```bash
   mongosh < database/init-mongo.js
```

This will insert medical notes collection with sample medical notes.

## How to run the project

### 1 Prerequisites

- **Java 17**
- **Maven 3+**
- **MongoDB**

### 2 Start the microservices

```bash
cd ms-notes && mvn spring-boot:run
```
