# Student Management API - Documentation

## Table of Contents
1. [Overview](#overview)
2. [Base URL](#base-url)
3. [Authentication](#authentication)
4. [HTTP Status Codes](#http-status-codes)
5. [Error Handling](#error-handling)
6. [API Endpoints](#api-endpoints)
   - [Create Student](#1-create-student)
   - [Get All Students](#2-get-all-students)
   - [Get Student by ID](#3-get-student-by-id)
   - [Update Student](#4-update-student)
   - [Delete Student](#5-delete-student)
   - [Pagination (Bonus)](#6-get-students-with-pagination)
   - [Search by Name (Bonus)](#7-search-students-by-name)
   - [Search by Course (Bonus)](#8-search-students-by-course)
   - [Search by Keyword (Bonus)](#9-search-by-keyword)
7. [Data Models](#data-models)
8. [Validation Rules](#validation-rules)
9. [Example Workflows](#example-workflows)

---

## Overview

The Student Management API is a RESTful web service that allows you to manage student records. It provides full CRUD (Create, Read, Update, Delete) operations along with advanced features like pagination and search.

### Key Features
- RESTful architecture
- JSON request/response format
- Input validation
- Global exception handling
- Search and filtering capabilities
- Pagination support

### Technology Stack
- Spring Boot 3.2.0
- Spring Data JPA
- H2 Database
- Jakarta Validation

---

## Base URL

```
http://localhost:8080/api/students
```

**Note:** Change `localhost:8080` to your server's address if deployed elsewhere.

---

## Authentication

**Current Version:** No authentication required (v1.0)

**Future Versions:** Will implement JWT-based authentication.

---

## HTTP Status Codes

The API uses standard HTTP status codes to indicate the success or failure of requests:

| Status Code | Description | When Used |
|------------|-------------|-----------|
| **200 OK** | Success | GET, PUT, DELETE successful |
| **201 Created** | Resource created successfully | POST successful |
| **400 Bad Request** | Invalid input data | Validation errors |
| **404 Not Found** | Resource not found | Student ID doesn't exist |
| **409 Conflict** | Resource conflict | Duplicate email |
| **500 Internal Server Error** | Server error | Unexpected errors |

---

## Error Handling

All errors follow a consistent format:

### Error Response Structure

```json
{
  "timestamp": "2024-11-07T10:30:00",
  "status": 400,
  "error": "Error Type",
  "message": "Human-readable error message",
  "path": "/api/students",
  "details": ["Detailed error 1", "Detailed error 2"]
}
```

### Error Types

#### 1. Validation Error (400)
```json
{
  "timestamp": "2024-11-07T10:30:00",
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

#### 2. Not Found Error (404)
```json
{
  "timestamp": "2024-11-07T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Student not found with id : '123'",
  "path": "/api/students/123"
}
```

#### 3. Conflict Error (409)
```json
{
  "timestamp": "2024-11-07T10:30:00",
  "status": 409,
  "error": "Conflict",
  "message": "Student with email john.doe@example.com already exists",
  "path": "/api/students"
}
```

---

## API Endpoints

### 1. Create Student

Creates a new student record in the system.

**Endpoint:** `POST /api/students`

**Request Headers:**
```
Content-Type: application/json
```

**Request Body:**
```json
{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "course": "Computer Science",
  "age": 20
}
```

**Success Response (201 Created):**
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "course": "Computer Science",
  "age": 20
}
```

**Error Responses:**

*400 Bad Request - Invalid Input:*
```json
{
  "timestamp": "2024-11-07T10:30:00",
  "status": 400,
  "error": "Validation Failed",
  "message": "Invalid input data",
  "path": "/api/students",
  "details": [
    "email: Email must be valid",
    "age: Age must be at least 18"
  ]
}
```

*409 Conflict - Duplicate Email:*
```json
{
  "timestamp": "2024-11-07T10:30:00",
  "status": 409,
  "error": "Conflict",
  "message": "Student with email john.doe@example.com already exists",
  "path": "/api/students"
}
```

**cURL Example:**
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john.doe@example.com",
    "course": "Computer Science",
    "age": 20
  }'
```

---

### 2. Get All Students

Retrieves all student records from the system.

**Endpoint:** `GET /api/students`

**Request Headers:** None required

**Success Response (200 OK):**
```json
[
  {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@example.com",
    "course": "Computer Science",
    "age": 20
  },
  {
    "id": 2,
    "name": "Jane Smith",
    "email": "jane.smith@example.com",
    "course": "Engineering",
    "age": 22
  },
  {
    "id": 3,
    "name": "Bob Johnson",
    "email": "bob.johnson@example.com",
    "course": "Data Science",
    "age": 21
  }
]
```

**Empty Response:**
```json
[]
```

**cURL Example:**
```bash
curl -X GET http://localhost:8080/api/students
```

---

### 3. Get Student by ID

Retrieves a specific student by their ID.

**Endpoint:** `GET /api/students/{id}`

**Path Parameters:**
- `id` (required) - Student ID (Long)

**Success Response (200 OK):**
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "course": "Computer Science",
  "age": 20
}
```

**Error Response (404 Not Found):**
```json
{
  "timestamp": "2024-11-07T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Student not found with id : '999'",
  "path": "/api/students/999"
}
```

**cURL Example:**
```bash
curl -X GET http://localhost:8080/api/students/1
```

---

### 4. Update Student

Updates an existing student's information.

**Endpoint:** `PUT /api/students/{id}`

**Path Parameters:**
- `id` (required) - Student ID (Long)

**Request Headers:**
```
Content-Type: application/json
```

**Request Body:**
```json
{
  "name": "John Updated",
  "email": "john.updated@example.com",
  "course": "Data Science",
  "age": 21
}
```

**Success Response (200 OK):**
```json
{
  "id": 1,
  "name": "John Updated",
  "email": "john.updated@example.com",
  "course": "Data Science",
  "age": 21
}
```

**Error Responses:**

*404 Not Found:*
```json
{
  "timestamp": "2024-11-07T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Student not found with id : '999'",
  "path": "/api/students/999"
}
```

*409 Conflict - Email Already Exists:*
```json
{
  "timestamp": "2024-11-07T10:30:00",
  "status": 409,
  "error": "Conflict",
  "message": "Student with email john.updated@example.com already exists",
  "path": "/api/students/1"
}
```

**cURL Example:**
```bash
curl -X PUT http://localhost:8080/api/students/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Updated",
    "email": "john.updated@example.com",
    "course": "Data Science",
    "age": 21
  }'
```

---

### 5. Delete Student

Deletes a student record from the system.

**Endpoint:** `DELETE /api/students/{id}`

**Path Parameters:**
- `id` (required) - Student ID (Long)

**Success Response (200 OK):**
```json
{
  "message": "Student deleted successfully"
}
```

**Error Response (404 Not Found):**
```json
{
  "timestamp": "2024-11-07T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Student not found with id : '999'",
  "path": "/api/students/999"
}
```

**cURL Example:**
```bash
curl -X DELETE http://localhost:8080/api/students/1
```

---

### 6. Get Students with Pagination

Retrieves students with pagination and sorting support (BONUS FEATURE).

**Endpoint:** `GET /api/students/paginated`

**Query Parameters:**
| Parameter | Type | Required | Default | Description |
|-----------|------|----------|---------|-------------|
| page | Integer | No | 0 | Page number (0-indexed) |
| size | Integer | No | 10 | Number of records per page |
| sortBy | String | No | id | Field to sort by (id, name, email, course, age) |
| sortDir | String | No | asc | Sort direction (asc, desc) |

**Example Request:**
```
GET /api/students/paginated?page=0&size=5&sortBy=name&sortDir=asc
```

**Success Response (200 OK):**
```json
{
  "content": [
    {
      "id": 3,
      "name": "Alice Williams",
      "email": "alice.williams@example.com",
      "course": "Computer Science",
      "age": 23
    },
    {
      "id": 2,
      "name": "Bob Johnson",
      "email": "bob.johnson@example.com",
      "course": "Data Science",
      "age": 21
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 5,
    "sort": {
      "sorted": true,
      "unsorted": false,
      "empty": false
    }
  },
  "totalElements": 10,
  "totalPages": 2,
  "last": false,
  "first": true,
  "number": 0,
  "size": 5,
  "numberOfElements": 5,
  "empty": false
}
```

**cURL Example:**
```bash
curl -X GET "http://localhost:8080/api/students/paginated?page=0&size=5&sortBy=name&sortDir=asc"
```

---

### 7. Search Students by Name

Searches for students whose name contains the given search term (case-insensitive) (BONUS FEATURE).

**Endpoint:** `GET /api/students/search/name`

**Query Parameters:**
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| name | String | Yes | Name to search for (partial match) |

**Example Request:**
```
GET /api/students/search/name?name=John
```

**Success Response (200 OK):**
```json
[
  {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@example.com",
    "course": "Computer Science",
    "age": 20
  },
  {
    "id": 4,
    "name": "Johnny Walker",
    "email": "johnny.walker@example.com",
    "course": "Engineering",
    "age": 19
  }
]
```

**Empty Result:**
```json
[]
```

**cURL Example:**
```bash
curl -X GET "http://localhost:8080/api/students/search/name?name=John"
```

---

### 8. Search Students by Course

Searches for students enrolled in a specific course (case-insensitive) (BONUS FEATURE).

**Endpoint:** `GET /api/students/search/course`

**Query Parameters:**
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| course | String | Yes | Course name (exact match, case-insensitive) |

**Example Request:**
```
GET /api/students/search/course?course=Computer Science
```

**Success Response (200 OK):**
```json
[
  {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@example.com",
    "course": "Computer Science",
    "age": 20
  },
  {
    "id": 3,
    "name": "Alice Williams",
    "email": "alice.williams@example.com",
    "course": "Computer Science",
    "age": 23
  }
]
```

**cURL Example:**
```bash
curl -X GET "http://localhost:8080/api/students/search/course?course=Computer%20Science"
```

---

### 9. Search by Keyword

Searches for students by name OR course using a single keyword (BONUS FEATURE).

**Endpoint:** `GET /api/students/search`

**Query Parameters:**
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| keyword | String | Yes | Search term to match in name or course |

**Example Request:**
```
GET /api/students/search?keyword=Science
```

**Success Response (200 OK):**
```json
[
  {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@example.com",
    "course": "Computer Science",
    "age": 20
  },
  {
    "id": 2,
    "name": "Bob Johnson",
    "email": "bob.johnson@example.com",
    "course": "Data Science",
    "age": 21
  }
]
```

**cURL Example:**
```bash
curl -X GET "http://localhost:8080/api/students/search?keyword=Science"
```

---

## Data Models

### Student Object

| Field | Type | Required | Constraints | Description |
|-------|------|----------|-------------|-------------|
| id | Long | Auto-generated | - | Unique identifier |
| name | String | Yes | 2-100 characters | Student's full name |
| email | String | Yes | Valid email, Unique | Student's email address |
| course | String | Yes | Not blank | Course of study |
| age | Integer | Yes | 18 ≤ age < 100 | Student's age |

**JSON Schema:**
```json
{
  "type": "object",
  "properties": {
    "id": {
      "type": "integer",
      "format": "int64",
      "readOnly": true
    },
    "name": {
      "type": "string",
      "minLength": 2,
      "maxLength": 100
    },
    "email": {
      "type": "string",
      "format": "email"
    },
    "course": {
      "type": "string",
      "minLength": 1
    },
    "age": {
      "type": "integer",
      "minimum": 18,
      "maximum": 99
    }
  },
  "required": ["name", "email", "course", "age"]
}
```

---

## Validation Rules

### Field-Level Validation

#### Name
- ✅ **Required:** Cannot be null or blank
- ✅ **Length:** 2 to 100 characters
- ❌ Single character names rejected
- ❌ Names over 100 characters rejected

#### Email
- ✅ **Required:** Cannot be null or blank
- ✅ **Format:** Must be valid email format (contains @ and domain)
- ✅ **Unique:** No two students can have same email
- ❌ Invalid formats: `notanemail`, `missing@domain`, `@example.com`

#### Course
- ✅ **Required:** Cannot be null or blank
- ✅ **Any text:** No specific format required

#### Age
- ✅ **Required:** Cannot be null
- ✅ **Minimum:** Must be at least 18
- ✅ **Maximum:** Must be less than 100
- ❌ Ages below 18 rejected
- ❌ Ages 100 or above rejected

### Business Logic Validation

#### Create Student
- Email must not already exist in the database
- All field validations must pass

#### Update Student
- Student ID must exist
- If email is changed, new email must not exist in database
- All field validations must pass

---

## Example Workflows

### Workflow 1: Complete Student Lifecycle

```bash
# 1. Create a new student
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john.doe@example.com",
    "course": "Computer Science",
    "age": 20
  }'
# Response: {"id": 1, ...}

# 2. Retrieve the student
curl -X GET http://localhost:8080/api/students/1

# 3. Update the student
curl -X PUT http://localhost:8080/api/students/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Updated",
    "email": "john.updated@example.com",
    "course": "Data Science",
    "age": 21
  }'

# 4. Delete the student
curl -X DELETE http://localhost:8080/api/students/1
```

### Workflow 2: Search and Filter

```bash
# 1. Get all students with pagination
curl -X GET "http://localhost:8080/api/students/paginated?page=0&size=10&sortBy=name&sortDir=asc"

# 2. Search by name
curl -X GET "http://localhost:8080/api/students/search/name?name=John"

# 3. Search by course
curl -X GET "http://localhost:8080/api/students/search/course?course=Computer%20Science"

# 4. Combined search
curl -X GET "http://localhost:8080/api/students/search?keyword=Science"
```

### Workflow 3: Error Handling

```bash
# Try to create student with invalid data
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{
    "name": "A",
    "email": "invalid-email",
    "course": "",
    "age": 15
  }'
# Response: 400 Bad Request with validation errors

# Try to get non-existent student
curl -X GET http://localhost:8080/api/students/999
# Response: 404 Not Found

# Try to create duplicate email
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Jane Doe",
    "email": "john.doe@example.com",
    "course": "Engineering",
    "age": 22
  }'
# Response: 409 Conflict
```

---

## Support and Contact

For issues or questions:
- **GitHub:** https://github.com/gunathilakax
- **Email:** sithijashehara2001@gmail.com
- **Swagger UI:** http://localhost:8080/swagger-ui.html

---

## Changelog

### Version 1.0.0 (Current)
- Initial release
- CRUD operations
- Input validation
- Global exception handling
- Pagination support
- Search functionality

---

**Last Updated:** November 7, 2025  
**Author:** Sithija Shehara Gunathilaka  
**University:** University of Sri Jayewardenepura