# Student Internship Management System

## Project Overview

**Student Internship Management System** is a Spring Boot web application designed to manage the process of internship offers, company registration requests, student applications, and administrative review.

The system is built for a university-style environment where students, companies, and administrators have different responsibilities and access rights.

The main idea of the project is to provide a structured platform where:

- students can access internship offers using university credentials;
- companies can request access to the platform;
- administrators can verify company registration requests;
- approved companies can publish internship offers;
- students can apply for internships;
- companies can review and manage received applications.

---

## Main User Roles

The system supports three main roles:

### Admin

The administrator is already created in the system and does not register publicly.

Admin responsibilities:

- log in with a pre-created local account;
- review company registration requests;
- approve or reject companies;
- manage users;
- monitor internship offers;
- view system statistics.

---

### Student

Students do not register manually.

A student must log in using university credentials, for example through a university Microsoft Teams account.

For the current MVP version, the university / Teams login can be emulated.

Student responsibilities:

- log in with university credentials;
- manage student profile;
- browse active internship offers;
- apply for internship offers;
- view application status.

---

### Company

Companies cannot immediately access the system.

A company must first submit a registration request with detailed information.  
The administrator manually reviews the request and either approves or rejects it.

Company responsibilities after approval:

- log in to the system;
- manage company profile;
- create internship offers;
- edit or close internship offers;
- view received student applications;
- approve or reject student applications.

---

## Updated Authentication and Registration Logic

The project does not use free public registration for all users.

Instead, the system follows this logic:

```text
Admin:
Pre-created local account

Student:
University / Teams login

Company:
Registration request → Admin review → Approved / Rejected