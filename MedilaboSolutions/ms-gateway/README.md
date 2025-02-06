# Microservice `ms-gateway`

This microservice is the  **API gateway** of this `MedilaboSolutions` application. 
The Gateway is responsible for routing and securing requests between the frontend and microservices.  

## Features

- API routing to backend microservices 
`ms-backend-patient`
`ms-notes`
`ms-diabetes-risk`

- Secure endpoints
- Configure CORS and security filters

## How to run the project

### 1 Prerequisites

- **Java 17**
- **Maven 3+**

### 2 Start the gateway

```bash
cd ms-gateway && mvn spring-boot:run
```
