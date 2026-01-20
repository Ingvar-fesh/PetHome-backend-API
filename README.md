# 🐾 PetHome Backend API

**PetHome** is a Spring Boot RESTful API designed to power a community platform for pet owners. It provides functionalities for a social forum, user pet profiles, and an interactive map to find pet-friendly locations (Vets, Parks, Shops).

The application follows a **Layered Architecture** and uses **Docker** for database management.

---

## 🚀 Features

### 🔐 Authentication & Users
* **JWT Security:** Secure Stateless authentication using JSON Web Tokens.
* **User Management:** Register, Login, Update Profile, Delete Account.
* **Profile Images:** Support for profile image URLs ("Upload First" pattern).

### 💬 Community Forum
* **Topics:** Categorized discussions (e.g., Health, Training, Nutrition).
* **Posts:** Users can create, update, and delete posts.
* **Comments:** Users can comment on posts.
* **Likes:** Toggle likes on both Posts and Comments.

### 🐶 Pet Management
* **Pet Profiles:** Users can add their pets (Name, Type, Picture URL).
* **Pet Types:** Standardized categories (Dog, Cat, Bird, etc.).
* **Ownership:** Secure management ensuring users can only edit/delete their own pets.

### 🗺️ Geo-Services (Interactive Map)
* **Places:** Database of pet-friendly locations (Vets, Parks, Shops).
* **Search by Type:** Filter map markers by category (e.g., "Show me only Vets").
* **Search Nearby:** Find places within a specific radius (km) using the Haversine formula.
* **Public Access:** Map data is accessible without login for easy browsing.

---

## 📂 Project Structure

The project follows a standard **Layered Architecture** to separate concerns:

```text
src/main/java/fr/epita/pethome
├── config/           # App configuration (DataInitializer, OpenAPI, WebConfig)
├── controllers/      # REST Controllers (API Endpoints layer)
├── datamodel/        # JPA Entities (Database tables) and DTOs
├── repositories/     # Interfaces for Database Access (Spring Data JPA)
├── security/         # JWT Filters, Security Config, and Password Encoding
└── services/         # Business Logic (Validation, ownership checks, calculations)
```

## Setup & Run
### 1. Prerequisites
- Java JDK 17 or higher
- Maven
- Docker & Docker Compose (for the database)
### 2. Start the Database
Use the provided **docker-compose.yml** to start the PostgreSQL container:
```bash
docker-compose up -d
```
### 3. Configure Environment
Ensure src/main/resources/application.properties matches your Docker configuration and includes a secure JWT secret:
```bash
# Database Configuration (Must match docker-compose.yml)
spring.datasource.url=jdbc:postgresql://localhost:5432/pethome_db
spring.datasource.username=user
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update

# File Upload Configuration
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

# ⚠️ JWT SECURITY: Must be at least 32 characters long!
jwt.secret=YourSuperSecretKeyForSigningTokens12345!
```
## 📖 API Documentation (Swagger)
This project includes fully interactive API documentation. Once the server is running, visit:
```bash
http://localhost:8080/swagger-ui/index.html
```

How to Authenticate in Swagger:
1. Go to the POST /api/users/auth endpoint.
2. Enter valid credentials (see Default Data below).
3. Copy the token string from the response.
4. Click the green Authorize button at the top right.
5. Enter the token (value only, no quotes).
6. Click Authorize. Now you can test secured endpoints like Create Post or Add Pet.

## 💾 Default Data (DataInitializer)
On startup, the application **(DataInitializer.java)** automatically populates the database with sample data:
Users:
* *Admin*: admin_test / securepass
* *User*: commenter_bob / bobpass
* *Topics*: Health, Training, Nutrition.
* *Place Types*: VET, PARK, SHOP (with icon URLs).
* *Places*: Sample locations in Paris (e.g., "Paris Vet Clinic", "Tuileries Garden").
