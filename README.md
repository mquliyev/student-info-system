# Student Information System (SIS)

This is a Spring Boot 3.2.3 backend application for managing student data with JWT security.

## Tech Stack
- **Java 21**
- **Spring Boot 3.2.3**
- **Spring Security (JWT)**
- **PostgreSQL**
- **Maven**
- **Swagger UI (OpenAPI 3.0)**

## Features
- **Authentication:** Secure Login/Register with JWT tokens.
- **Student Management:** CRUD operations for students.
- **Global Error Handling:** Centralized exception handling with JSON responses.

## How to Run
1. Configure your PostgreSQL database in `src/main/resources/application.properties`.
2. Run `mvn spring-boot:run`.
3. Access Swagger UI: `http://localhost:8080/swagger-ui.html`