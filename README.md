# Student Internship Management System

Spring Boot web application for managing student internships, companies, internship offers and applications.

## Project Overview

The system supports the complete process of publishing internship positions and applying for them.

Students can browse internship offers, submit applications and track their application status.

Companies or mentors can manage their own internship offers and review student applications.

Administrators can manage users, companies, offers, applications and platform statistics.

## Team Members and Responsibilities

| Team Member | Responsibility |
|------------|----------------|
| Student 1 | Backend entities, repositories and database model |
| Student 2 | Services, validation and business logic |
| Student 3 | Security, documentation and final demonstration |

## Technology Stack

- Java 17
- Spring Boot 3
- Spring Web / REST API
- Spring Data JPA
- PostgreSQL
- Jakarta Bean Validation
- Spring Security with roles
- Maven
- Swagger / OpenAPI
- Git and GitHub

## User Roles

The system has three user roles:

- ADMIN
- STUDENT
- COMPANY

## Main Workflow

1. An administrator registers or approves a company profile.
2. A company publishes an internship offer.
3. A student searches available offers and submits an application.
4. The company reviews the application and changes its status.
5. The student sees the current application status.
6. The administrator reviews platform statistics and manages invalid content.

## Main Features

- User registration and login
- Student profile management
- Company profile management
- Internship offer creation and management
- Internship offer search and filtering
- Student application submission
- Application status tracking
- Company review of applications
- Admin management and basic statistics

## How to Run the Application

Instructions will be added during development.

## Database Configuration

See:

```text
src/main/resources/application-example.properties