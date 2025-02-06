# Microservice `ms-diabetes-risk`

This microservice **evaluate a patients's diabetes risk** based on their medical history and medical notes.

## Features

- Calculate diabetes risk based on medical notes
- Communicate with `ms-backend-patient`and `ms-notes` via Feign so do not have its own database.
- Classify diabetes risk as: `None`, `Borderline`, `In Danger`, `Early onset`

## Technologies Used

- **Spring Boot Framework**
- **Feign Client** : for inter-service communication. 
- **Regex and Pattern Matching** : for analyzing medical notes. 

## How to run the project

### 1 Prerequisites

- **Java 17**
- **Maven 3+**
- **Open Feign**

### 2 Start the microservices

```bash
cd ms-diabetes-risk && mvn spring-boot:run
```
