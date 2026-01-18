# Spring Boot Banking API

A secure RESTful banking API built with Java and Spring Boot.  
This project demonstrates backend fundamentals such as authentication, authorization, transaction handling, and error management.

---

## 🚀 Features

- User registration and login with JWT authentication
- Deposit, withdraw, and transfer money
- Balance inquiry
- Transaction history (login required)
- Global exception handling
- Secure password hashing (BCrypt)
- Monetary values handled with BigDecimal

---

## 🛠 Tech Stack

- Java 17  
- Spring Boot  
- Spring Security (JWT)  
- Spring Data JPA  
- H2 In-Memory Database  
- Maven  

---

## 🔐 Authentication

This API uses **JWT (JSON Web Token)** for authentication.

### Public endpoints
- `POST /api/users/register`
- `POST /api/auth/login`

### Protected endpoints
All other endpoints require the following header:

Authorization: Bearer <JWT_TOKEN>


Passwords are securely hashed using **BCrypt**, and sensitive configuration values are excluded from version control.

---

## 📌 API Endpoints (Overview)

### User
- `POST /api/users/register` — Register a new user
- `POST /api/auth/login` — Login and obtain JWT token

### Account
- `POST /api/accounts/deposit` — Deposit money
- `POST /api/accounts/withdraw` — Withdraw money
- `POST /api/accounts/transfer` — Transfer money to another user
- `GET /api/accounts/balance` — Get current balance
- `GET /api/accounts/transactions` — View transaction history

---

## ▶️ Run Locally

### Prerequisites
- Java 17+
- Maven

### Run the application
```bash
mvn spring-boot:run
```
The application will start at:
http://localhost:8080

---
🧪 Testing the API

The API can be tested using Insomnia or Postman.

Suggested Testing Flow

1.Register a user

2.Login to receive a JWT token

3.Use the token to access protected endpoints

4.Perform deposit, withdrawal, transfer, and transaction queries
---

📂 Project Structure
src/main/java/com/bank
├── auth          # Authentication controllers and DTOs
├── controller    # REST controllers
├── service       # Business logic
├── security      # JWT and Spring Security configuration
├── user          # User entity and repository
├── transaction   # Transaction entity and history
├── exception     # Global exception handling
---

📄 License

This project is licensed under the MIT License.
