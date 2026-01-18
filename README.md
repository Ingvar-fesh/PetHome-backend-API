# Project structure

The project follows a standard **Layered Architecture**:
```text
src/main/java/fr/epita/pethome
├── config/           # App configuration (DataInitializer for default topics)
├── controllers/      # REST Controllers (API Endpoints layer)
├── datamodel/        # JPA Entities (Database tables) and DTOs
├── repositories/     # Interfaces for Database Access (Spring Data JPA)
├── security/         # JWT Filters, Security Config, and Password Encoding
└── services/         # Business Logic (Validation, ownership checks)
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
Ensure src/main/resources/application.properties has the correct database settings and a secure JWT secret:
```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/pethome_db
spring.datasource.username=user
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update

# ⚠️ IMPORTANT: Must be at least 32 characters long!
jwt.secret=YourSuperSecretKeyForSigningTokens12345!
```
### 4. Run the Application
