# Student Management REST API

A complete RESTful API for managing student records with CRUD operations, validation, exception handling, and search functionality.

[`API_DOCUMENTATION`](./API_DOCUMENTATION.md)

## ✨ Features

### Core Features
- ✅ Create, Read, Update, Delete (CRUD) operations
- ✅ Input validation (email format, age constraints)
- ✅ Global exception handling
- ✅ Service layer architecture
- ✅ Proper HTTP status codes (200, 201, 400, 404, 409)
- ✅ H2 in-memory database

### Other Features
- ✅ Swagger/OpenAPI documentation
- ✅ Pagination and sorting
- ✅ Search by name
- ✅ Search by course
- ✅ Combined search functionality

## 🛠️ Technologies Used

| Technology | Version | Purpose |
|-----------|---------|---------|
| Java | 17 | Programming Language |
| Spring Boot | 3.2.0 | Application Framework |
| Spring Data JPA | 3.2.0 | Database Access |
| H2 Database | 2.2.224 | In-Memory Database |
| Maven | 3.9+ | Build Tool |
| Swagger/OpenAPI | 2.2.0 | API Documentation |
| Jakarta Validation | 3.0.2 | Input Validation |

## 📋 Prerequisites

- Java JDK 17 or higher
- Maven 3.6+
- Postman (for testing)
- Git

## 🚀 Quick Start

[`API_DOCUMENTATION`](./API_DOCUMENTATION.md)

### 1. Clone or Extract the Project
```bash
# If using Git
git clone https://github.com/gunathilakax/student-management-api.git
cd student-management

# Or extract the ZIP file and navigate to the folder
```

### 2. Build the Project
```bash
mvn clean install
```

### 3. Run the Application
```bash
mvn spring-boot:run
```

**Alternative:** Run from IDE (IntelliJ/Eclipse)
- Open `StudentManagementApplication.java`
- Click the Run button (▶)

### 4. Access the Application

- **API Base URL:** http://localhost:8080/api/students
- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **H2 Console:** http://localhost:8080/h2-console
    - JDBC URL: `jdbc:h2:mem:studentdb`
    - Username: `sa`
    - Password: (leave empty)

## 📚 API Documentation

### Base URL
```
http://localhost:8080/api/students
```

### Endpoints

#### 1️⃣ Create Student
```http
POST /api/students
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "course": "Computer Science",
  "age": 20
}
```
**Response:** `201 Created`

#### 2️⃣ Get All Students
```http
GET /api/students
```
**Response:** `200 OK`

#### 3️⃣ Get Student by ID
```http
GET /api/students/{id}
```
**Response:** `200 OK` or `404 Not Found`

#### 4️⃣ Update Student
```http
PUT /api/students/{id}
Content-Type: application/json

{
  "name": "John Updated",
  "email": "john.updated@example.com",
  "course": "Data Science",
  "age": 21
}
```
**Response:** `200 OK` or `404 Not Found`

#### 5️⃣ Delete Student
```http
DELETE /api/students/{id}
```
**Response:** `200 OK` or `404 Not Found`

```json
{
  "message": "Student deleted successfully"
}
```

#### 6️⃣ Get Students with Pagination
```http
GET /api/students/paginated?page=0&size=10&sortBy=name&sortDir=asc
```

#### 7️⃣ Search by Name
```http
GET /api/students/search/name?name={searchTerm}
```

#### 8️⃣ Search by Course
```http
GET /api/students/search/course?course={courseName}
```

#### 9️⃣ Search by Keyword
```http
GET /api/students/search?keyword={searchTerm}
```

## 🔍 Validation Rules

| Field | Constraints |
|-------|------------|
| name | Required, 2-100 characters |
| email | Required, Valid format, Unique |
| course | Required |
| age | Required, ≥ 18, < 100 |

## ❌ Error Responses

### 1. Validation Error (400 Bad Request)
```json
{
  "timestamp": "2024-11-05T10:30:00",
  "status": 400,
  "error": "Validation Failed",
  "message": "Invalid input data",
  "path": "/api/students",
  "details": [
    "name: Name must be between 2 and 100 characters",
    "email: Email must be valid",
    "age: Age must be at least 18"
  ]
}
```

### 2. Resource Not Found (404 Not Found)
```json
{
  "timestamp": "2024-11-05T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Student not found with id : '999'",
  "path": "/api/students/999"
}
```

### 3. Duplicate Email (409 Conflict)
```json
{
  "timestamp": "2024-11-05T10:30:00",
  "status": 409,
  "error": "Conflict",
  "message": "Student with email john.doe@example.com already exists",
  "path": "/api/students"
}
```

## 🚩 HTTP Status Codes Used

| Status Code | Description | When Used |
|------------|-------------|-----------|
| 200 OK | Success | GET, PUT, DELETE successful |
| 201 Created | Resource created | POST successful |
| 400 Bad Request | Validation failed | Invalid input data |
| 404 Not Found | Resource not found | Student ID doesn't exist |
| 409 Conflict | Duplicate resource | Email already exists |
| 500 Internal Server Error | Server error | Unexpected errors |


## 🗂️ Project Structure
```
student-management-api/
├── src/
│   ├── main/
│   │   ├── java/com/university/studentmanagement/
│   │   │   ├── StudentManagementApplication.java
│   │   │   ├── controller/
│   │   │   │   └── StudentController.java
│   │   │   ├── entity/
│   │   │   │   └── Student.java
│   │   │   ├── repository/
│   │   │   │   └── StudentRepository.java
│   │   │   ├── service/
│   │   │   │   ├── StudentService.java
│   │   │   │   └── StudentServiceImpl.java
│   │   │   └── exception/
│   │   │       ├── ResourceNotFoundException.java
│   │   │       ├── DuplicateResourceException.java
│   │   │       ├── ErrorResponse.java
│   │   │       └── GlobalExceptionHandler.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── screenshots/
├── pom.xml
└── README.md
```

## Database Schema

### Students Table

| Column | Type | Constraints |
|--------|------|-------------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT |
| name | VARCHAR(100) | NOT NULL |
| email | VARCHAR(255) | NOT NULL, UNIQUE |
| course | VARCHAR(100) | NOT NULL |
| age | INT | NOT NULL, CHECK (age >= 18) |

**Note:** This project uses H2 in-memory database. All data will be lost when the application stops. For production use, configure a persistent database like MySQL or PostgreSQL.


## 📸 Screenshots

All Postman testing screenshots are available in the [`screenshots/`](./screenshots) folder:
- CRUD operations
- Validation errors
- Exception handling
- Other features (pagination, search)
- H2 Console

## 🧪 Testing with Postman

### Import Steps:
1. Open Postman
2. Create a new collection named "Student Management API"
3. Add requests for each endpoint listed above

### Sample Test Data

**Student 1:**
```json
{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "course": "Computer Science",
  "age": 20
}
```

**Student 2:**
```json
{
  "name": "Jane Smith",
  "email": "jane.smith@example.com",
  "course": "Engineering",
  "age": 22
}
```

**Student 3:**
```json
{
  "name": "Bob Johnson",
  "email": "bob.johnson@example.com",
  "course": "Data Science",
  "age": 21
}
```

**Invalid Student (for testing validation):**
```json
{
  "name": "A",
  "email": "not-an-email",
  "course": "",
  "age": 15
}
```

## 🐛 Troubleshooting

### Problem: Port 8080 already in use
**Solution:** Change port in `application.properties`:
```properties
server.port=8081
```

### Problem: Maven dependencies not downloading
**Solution:**
```bash
mvn clean install -U
```
Or in IntelliJ: Right-click project → Maven → Reload Project

### Problem: Application doesn't start
**Solution:**
- Check Java version: `java -version` (should be 17+)
- Check console for error messages
- Ensure no other application is using port 8080

### Problem: H2 Console not accessible
**Solution:**
- Ensure `spring.h2.console.enabled=true` in application.properties
- Use correct JDBC URL: `jdbc:h2:mem:studentdb`

## Additional Resources


[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-Educational-blue.svg)](./LICENSE)

- [Spring Data JPA Documentation](https://spring.io/projects/spring-data-jpa)
- [Swagger Documentation](https://springdoc.org/)

## 📞 Contact

**Developer:** Sithija Shehara Gunathilaka   
**Email:** sithijashehara2001@gmail.com  
**University:** University of Sri Jayewardenepura

[<img src="https://img.shields.io/badge/GitHub-@gunathilakax-black?style=for-the-badge&#x26;logo=github" alt="GitHub">](https://github.com/gunathilakax)
[<img src="https://img.shields.io/badge/LinkedIn-Sithija%20Gunathilaka-blue?style=for-the-badge&#x26;logo=linkedin" alt="LinkedIn">
](https://www.linkedin.com/in/sithijagunathilaka)

---

**⭐ If you found this project helpful, please give it a star!**

---