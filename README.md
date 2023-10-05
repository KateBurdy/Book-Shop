## Project overview

The project represents a Book Shop implemented as a microservices architecture. The key technologies used are Spring Boot, Spring Cloud, Spring Security, and JWT for authentication.


## Components

**API Gateway:** The entry point for all external requests. Uses Spring Cloud Gateway and handles routing to internal services.

**Authentication Service:** Registers users and performs login, generating JWT tokens.

**Config server** - Centralizes and manages common configurations across all microservices. Utilizes Git backend for version control and delivers enhanced security through encrypted property storage and decryption-on-fetch mechanisms.

**Db-init service** - automates the creation of tables and their initialization with data with Liquibase.

**User Service:** - Manages user-related data.

**Order Service:** - Manages orders and shopping carts for users

**Payment service** - Generates link for payment and marks payment as successful.

**Product service** - Creates and keeps track of products.

**Authors service** - Creates authors of the books and keeps track of them.

**File service** - Stores all files, will be mainly used by product service.


# Key Features

## API Gateway

The API gateway is an entry point to our app. It accepts requests with header named Authorization which contains JWT token. Then, the API gateway validates this token, extracts the user-id from it, and redirects the request to the intenal service. With the redirection, API gateway transmits the header X-User-Id with the user-id it it.

Logging is implemented using the LoggingGlobalFilter class. This logs the original URI and the routed URI for every incoming request.

A circuit breaker pattern is implemented with Resilience4J in the RouteConfig class. This ensures that the application can degrade gracefully in the event of failure of a downstream service.

The gateway uses reactive programming models with Project Reactor to efficiently handle asynchronous operations and back pressure.

### Authentication service

The authentication module registers user in the system and allows to log into the system. After the successful login, the JWT token is generated with user-id in it. In the response to login request, token is shared though the header with name Authorization. This JWT token will be needed for all remaining external requests.

Utilizes HMAC for token signing, providing a secure way to validate the origin of the JWT.

Utilizes BCrypt for password encryption, increasing the security of stored user passwords.

Makes use of JWT to implement stateless authentication which is more scalable and easier to implement in distributed systems.


## Preliminary requirements
Java 17, PostgreSQL installed on your pc

## How run the project?

1. **Create postgreSQL database. Database properties:**
    
    Host: `localhost`
    
    Port: `5432`
    
    User: `postgres`
    
    Password: `12345678`
    
    Database: `users_api`
    
    Url: `jdbc:postgresql://localhost:5432/users_api`
    
    
2. **Run config-server first, then db-init module.**

## How to test the project?

1. **Use postman collection to test every endpoint, the link to postman collection:**

   https://warped-firefly-815960.postman.co/workspace/My-Workspace~e8655763-2e02-42ca-82f6-d04e27dc4d16/collection/23597328-938138e0-9d29-4bd9-ac5c-39862aa8aea1?action=share&creator=23597328

   In case you don’t have access, ping me on kateburdeinaia@gmail.com

2. **Run config-server module.**
4. **Run the rest of the services: authentication, gateway, products, payments, orders, users, files, authors.**
   **While using postman collection, note that all endpoints require valid JWT token. To receive JWT token, send register and login request with prefilled data from postman**

   - You will receive header with name Authorization with the JWT token after sending request on login. Please go to variables tab in postman of the project and update jwt token in bearer header.
 

