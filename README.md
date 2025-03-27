# 🛒 Awesomity Online Marketplace - Backend API

A robust, scalable, and secure RESTful API for an online marketplace. This backend enables users to register, authenticate, browse products, place orders, and leave ratings, while also providing admin-level functionalities for full platform management.

---

## 🚀 Features

### 🔐 Authentication & Authorization
- JWT-based login system
- Role-based access control (`USER`, `ADMIN`)
- Secure protected endpoints

### 👤 User Management
- User/Admin registration & login
- Profile update and deletion (by admin)
- Admin-only access to view all users

### 🛍️ Product Management
- Admins can add, update, delete, and feature products
- Users can browse and search products

### 🗂️ Categories
- Admins can manage product categories
- Products linked to categories
- Browsing products by category

### 📦 Orders
- Users can place orders
- Orders are processed asynchronously via Kafka
- Email notifications for status updates
- Admins can update order status

### ⭐ Ratings
- Users can rate only purchased products
- Ratings are viewable to all users

### 📨 Email Integration
- Welcome emails on signup
- Notifications on order updates

### ⚙️ Kafka Queue Integration
- Order processing is handled asynchronously via Kafka
- Ensures scalability under high load

---

## 🏗️ Architecture

- **Spring Boot 3.x**
- **PostgreSQL**
- **Spring Security**
- **Kafka**
- **Swagger (OpenAPI 3)**
- **Lombok**
- **Java Mail Sender**
- **Docker-Ready (optional extension)**

---

## 📁 Project Structure

\`\`\`
src/
├── controllers/        # REST controllers for handling HTTP requests
├── dto/                # Data transfer objects
├── entities/           # JPA entities
├── repositories/       # Spring Data JPA Repos
├── services/           # Interfaces and service logic
├── confugirations/     # JWT, Security, Kafka configs
├── mapper/             # Mapper classes for entity <-> DTO
├── exceptions/         # Custom error and access handlers
\`\`\`

---

## 🔧 Getting Started

### ✅ Prerequisites
- Java 17+
- Maven
- PostgreSQL
- Kafka & Zookeeper (running locally or via Docker)

### 🔨 Setup & Run

\`\`\`bash
# Clone the repo
git clone https://github.com/davidhitman/Backend-RESTful-API-for-an-online-marketplace.git

cd awesomity-marketplace-backend

# Configure application.properties
# (Set PostgreSQL and Kafka config)

# Run the app
./mvnw spring-boot:run
\`\`\`

---

## 🔐 Environment Variables

Create your own `application.properties`:

\`\`\`
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/marketplace
spring.datasource.username=yourusername
spring.datasource.password=yourpassword

# JWT
jwt.secret=your-super-secret-key

# Kafka
spring.kafka.bootstrap-servers=localhost:9092
\`\`\`

---

## 🧪 Tests

- Unit and integration tests in place
- Run tests via:

\`\`\`bash
./mvnw test
\`\`\`

Minimum coverage: **60%+**

---

## 🧭 API Documentation

- Swagger UI available at:  
  \`\`\`
  http://localhost:8080/swagger-ui.html
  \`\`\`

---

## 🐳 Docker & Deployment

### 🚀 Starting Docker Compose

\`\`\`bash
docker compose up -d --build      # Start containers
docker compose down               # Stop containers
\`\`\`

---

### 🛠️ Running PostgreSQL via Docker (Standalone)

\`\`\`bash
# Pull Postgres
docker pull postgres

# Run container
docker run -d \
  --name my-postgres \
  -e POSTGRES_PASSWORD=mysecretpassword \
  -p 5432:5432 \
  -v my_pgdata:/var/lib/postgresql/data \
  postgres
\`\`\`

---

### 🧭 Accessing PostgreSQL Container

\`\`\`bash
docker ps                                   # List containers
docker exec -it <container_id> bash        # Access container shell
su - postgres                              # Switch user
psql -U postgres                           # Connect to psql

\l           # List databases
\c <db>      # Connect to db
\dt          # List tables
SELECT * FROM <table>;  # View table data
\q           # Exit psql
Ctrl + D      # Exit container
\`\`\`

---

### 🏗️ Build the Application

\`\`\`bash
mvn clean package -DskipTests     # Build .jar file
\`\`\`

---

### ⚙️ Run Specific Docker Services

\`\`\`bash
docker compose up -d db           # Start only database service
\`\`\`

---

### 🛰️ Run Kafka in Docker (Standalone)

\`\`\`bash
docker pull apache/kafka:4.0.0
docker run -p 9092:9092 apache/kafka:4.0.0
\`\`\`

---

## 📚 Technologies Used

- Spring Boot
- Spring Security
- PostgreSQL
- Kafka
- Swagger
- JWT
- Docker
- Mail Sender (SMTP)

---



