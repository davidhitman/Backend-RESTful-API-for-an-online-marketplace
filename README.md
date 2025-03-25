Awesomity Challenge


Overview

Features

- User Authentication (Sign-up, and Login) using JWT.
- Role-Based Access Control (Admin and User roles).
- CRUD(create, read, update and delete) Operations for Users, Products, and Orders.
- DTO-based Data Handling to ensure security and efficiency.
- Spring Security Configuration for API protection.
- Database Integration (JPA, PostgreSQL).
- Email Notifications for order status updates and User signups.

System Design

High-Level Architecture

The system follows a layered architecture:

1. Client (Frontend): Sends API requests.
2. API Gateway (Spring Boot Controllers): Routes requests and enforces security.
3. Security Configuration: Defines authentication and authorization rules.
4. JWT Authentication & Authorization**: Manages token generation and validation.
5. DTO Layer: Maps entities to objects used in API responses.
6. Service Layer: Implements system's logic.
7. Repositories (JPA): Handles database operations.
8. Database (PostgreSQL): Stores application data.

Component Interaction

- Users authenticate using JWT.
- API Gateway checks security configuration before processing requests.
- Service layer interacts with the database through repositories.
- DTOs ensure structured data exchange between client and backend.
- Email service notifies users of order status changes and successful signup .

API Endpoints

- Authentication

| Method | Endpoint      | Description  |
| ------ | ------------- | ------------ |
| POST   | `/auth/`      | User Sign-up |
| POST   | `/auth/login` | User Login   |

- User Management (Admin Only)

| Method | Endpoint        | Description          |
| ------ | --------------- | -------------------- |
| DELETE | `/user/{email}` | Delete user by email |
| PUT    | `/user/{email}` | Update user details  |
| GET    | `/user/`        | Get all users        |

Product Management

| Method | Endpoint                         | Description                                  |
| ------ | -------------------------------- |----------------------------------------------|
| POST   | `/product/`                      | Add a new product (Admin)                    |
| GET    | `/product/search/{category}`     | Search products by category (User and Admin) |
| DELETE | `/product/{name}`                | Delete product (Admin)                       |
| GET    | `/product/view-all-products`     | Get all products (Admin)                     |
| GET    | `/product/view-product/{name}`   | Get single product details (Admin)           |
| PUT    | `/product/update-product/{name}` | Update product (Admin)                       |
| PATCH  | `/product/{productId}/featured`  | Mark product as featured (Admin)             |
| GET    | `/product/categories`            | Get all product categories (Admin)           |
| PUT    | `/product/change-category/{id}`  | Update product category (Admin)              |

Order Management

| Method | Endpoint                       | Description                             |
| ------ | ------------------------------ |-----------------------------------------|
| POST   | `/order/`                      | Place an order (User and Admin)         |
| GET    | `/order/order-history/{email}` | Get user order history (User and Admin) |
| DELETE | `/order/{id}`                  | Delete an order     (Admin)             |
| PUT    | `/order/update-order/{id}`     | Update an order     (Admin)             |
| GET    | `/order/view-status/{orderId}` | Get order status      (User and Admin)  |
| PATCH  | `/order/{orderId}/status`      | Update order status (Admin)             |



Technologies

- Java 17+
- Spring Boot 3+
- PostgreSQL
- Maven

Steps to Run

1. Clone the repository:
   ```terminal
   git clone https://github.com/your-repo/awesomity-challenge.git
   cd awesomity-challenge
   ```
2. Configure the database in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/your_db
   spring.datasource.username=root
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   ```
3. Run the application:**
   ```terminal

   mvn spring-boot:run
   ````

Swagger API Documentation

Once the application is running, visit:

```
http://localhost:8080/swagger-ui.html
```

Security & Authentication

- JWT Token is required for accessing protected endpoints.
- Roles:
    - `USER`: Can place/view orders.
    - `ADMIN`: Can manage users, products, and orders.


Start Docker or build the composer
'docker compose up -d --build' - this is to start the docker composer
'docker compose down' - this is to shut the composer down


Run and Access of the Database in Docker
'docker pull postgres' - download the latest postgres sql
run the image in docker
'docker run -d --name my-postgres -e POSTGRES_PASSWORD=mysecretpassword -p 5432:5432 -v my_pgdata:/var/lib/postgresql/data postgres'

#Access the database
'docker ps' - list of all running containers
'docker exec -it <container_id> bash' - access the container shell
'su - postgres' - switch to Postgres user
'psql -U postgres' - connect to postgres interactive shell
'\l' - view all databases
'\c <database_name>' - connect to your database
'\dt' - check the tables in the database
'SELECT * FROM <table_name>;' - select all data from table
to exit type '\q' exit the postgres shell then 'control D'

To build a target folder that contain a .jar file
'mvn clean package -DskipTests'

to run docker database only 
docker composer up -d db

to run Kafka in Docker
docker pull apache/kafka:4.0.0
docker run -p 9092:9092 apache/kafka:4.0.0


