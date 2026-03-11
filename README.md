# Student Information System (SIS)

A Spring Boot backend application for managing student records and course enrollments with JWT-based security.

## Tech Stack
- **Java 21**
- **Spring Boot 3.2.3**
- **Spring Security (JWT)**
- **PostgreSQL**
- **Gradle**
- **Swagger UI (OpenAPI 3.0)**

## Features
- **Authentication:** Register and login with JWT tokens
- **Student Management:** Full CRUD operations
- **Course Management:** Full CRUD operations
- **Enrollment:** Enroll students to courses with capacity check
- **Grading:** Assign scores with automatic letter grade and GPA calculation
- **Global Error Handling:** Centralized exception handling

## Setup

1. Create a `.env` file in the root directory based on `.env.example`:
```
DB_URL=jdbc:postgresql://localhost:5432/sis_db
DB_USERNAME=postgres
DB_PASSWORD=your_password
JWT_SECRET=your_secret_key
JWT_EXPIRATION=86400000
```

2. Run the application:
```
./gradlew bootRun
```

3. Access Swagger UI:
```
http://localhost:8080/swagger-ui/index.html
```

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/auth/register | Register a new user |
| POST | /api/auth/authenticate | Login and get JWT token |
| GET | /api/students | Get all students |
| GET | /api/students/{id} | Get student by ID |
| POST | /api/students | Add a new student |
| PUT | /api/students/{id} | Update student |
| DELETE | /api/students/{id} | Delete student |
| GET | /api/courses | Get all courses |
| GET | /api/courses/{id} | Get course by ID |
| POST | /api/courses | Add a new course |
| PUT | /api/courses/{id} | Update course |
| DELETE | /api/courses/{id} | Delete course |
| POST | /api/enrollments/{studentId}/{courseId} | Enroll student to course |
| PUT | /api/enrollments/{enrollmentId}/grade | Assign grade |

## Notes
- All endpoints except `/api/auth/**` require a valid JWT token.
- Add the token to the Swagger "Authorize" section to test protected endpoints.