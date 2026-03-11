# Student Information System (SIS)

A full-stack Student Information System built with Spring Boot and a single-file HTML frontend. Supports JWT authentication, complete CRUD operations, enrollment management, grade assignment, and GPA tracking.

## Tech Stack

**Backend**
- Java 21
- Spring Boot 3.2.3
- Spring Security (JWT)
- PostgreSQL
- Gradle
- Swagger UI (OpenAPI 3.0)

**Frontend**
- Single HTML file (no framework, no installation)
- Vanilla JS + CSS
- Connects directly to the backend REST API

## Features

- **Authentication** — Register and login with JWT tokens
- **Student Management** — Add, edit, delete students with tuition tracking
- **Course Management** — Add, edit, delete courses with capacity control
- **Enrollment Management** — Enroll students to courses, assign grades
- **Grade System** — A (≥91) / B (≥81) / C (≥71) / D (≥51) / F (<51)
- **GPA Calculation** — Automatically calculated from grades
- **Student Profile** — Click any student to view full profile, enrolled courses, payment status
- **Course Details** — Click any course to view enrolled students, capacity usage, average score
- **Unit Tests** — 23 tests across all service layers

## Project Structure

```
src/
├── main/java/com/example/sis/
│   ├── config/          # JWT, Security, Exception handling
│   ├── controller/      # REST controllers
│   ├── dto/             # Request/Response objects
│   ├── entity/          # JPA entities
│   ├── repository/      # Spring Data repositories
│   └── service/         # Business logic
└── test/java/com/example/sis/
    ├── EnrollmentServiceTest.java   # 7 tests
    ├── StudentServiceTest.java      # 6 tests
    ├── CourseServiceTest.java       # 6 tests
    └── AuthenticationServiceTest.java # 4 tests
```

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register new user |
| POST | `/api/auth/authenticate` | Login |
| GET | `/api/students` | Get all students |
| POST | `/api/students` | Add student |
| GET | `/api/students/{id}` | Get student by ID |
| PUT | `/api/students/{id}` | Update student |
| DELETE | `/api/students/{id}` | Delete student |
| GET | `/api/courses` | Get all courses |
| POST | `/api/courses` | Add course |
| GET | `/api/courses/{id}` | Get course by ID |
| PUT | `/api/courses/{id}` | Update course |
| DELETE | `/api/courses/{id}` | Delete course |
| GET | `/api/enrollments` | Get all enrollments |
| POST | `/api/enrollments/{studentId}/{courseId}` | Enroll student |
| PUT | `/api/enrollments/{id}/grade` | Assign grade |
| DELETE | `/api/enrollments/{id}` | Delete enrollment |

## How to Run

### Option 1 — Docker (Recommended)

No Java or PostgreSQL installation required. Only Docker needed.

```bash
git clone https://github.com/mquliyev/student-info-system
cd student-info-system
docker-compose up --build
```

App runs at `http://localhost:8080`

### Option 2 — Local (IntelliJ)

1. Install Java 21 and PostgreSQL
2. Create a `.env` file in the project root:

```env
DB_URL=jdbc:postgresql://localhost:5432/sis_db
DB_USERNAME=postgres
DB_PASSWORD=your_password
JWT_SECRET=your_secret_key
JWT_EXPIRATION=86400000
```

3. Run with IntelliJ (enable EnvFile plugin) or:

```bash
./gradlew bootRun
```

4. Access Swagger UI: `http://localhost:8080/swagger-ui.html`

## Frontend

Open `sis-frontend.html` directly in your browser — no installation needed.

Make sure the backend is running on `http://localhost:8080` first.

## Running Tests

```bash
./gradlew test
```