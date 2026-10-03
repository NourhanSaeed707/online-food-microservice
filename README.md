# 🍽️ Online Food — Microservices

A **Java Spring Boot Microservices** project designed to demonstrate a distributed restaurant management system using modern backend technologies and microservices architecture.

The system is divided into independent services responsible for **Users, Restaurants, and Orders**, with centralized configuration, service discovery, API Gateway, PostgreSQL databases, and Docker containerization.

---

## 🏗️ Architecture

```text
                         ┌─────────────────────┐
                         │     API Gateway     │
                         │      Port: 8222     │
                         └──────────┬──────────┘
                                    │
                    ┌───────────────┼────────────────┐
                    │               │                │
                    ▼               ▼                ▼
             ┌────────────┐  ┌──────────────┐  ┌──────────────┐
             │   User     │  │ Restaurant   │  │    Order     │
             │  Service   │  │   Service    │  │   Service    │
             └─────┬──────┘  └──────┬───────┘  └──────┬───────┘
                   │                │                  │
                   └────────────────┼──────────────────┘
                                    │
                                    ▼
                           ┌─────────────────┐
                           │ Eureka Discovery│
                           │     Server      │
                           └─────────────────┘

                           ┌─────────────────┐
                           │  Config Server  │
                           │                 │
                           └────────┬────────┘
                                    │
                                    ▼
                         Centralized Configuration

                           ┌─────────────────┐
                           │   PostgreSQL    │
                           │    Database     │
                           └─────────────────┘

                           ┌─────────────────┐
                           │     Docker      │
                           │   Containers    │
                           └─────────────────┘
```

---

# 🚀 Microservices

## 👤 User Service

Responsible for managing users within the system.

### Responsibilities

* Create users
* Retrieve users
* Update user information
* Delete users
* Manage user-related data
* Expose REST APIs for user operations

---

## 🍴 Restaurant Service

Responsible for managing restaurants and their information.

### Responsibilities

* Create restaurants
* Retrieve restaurants
* Update restaurant information
* Delete restaurants
* Manage restaurant data
* Expose REST APIs for restaurant operations

---

## 🛒 Order Service

Responsible for handling customer orders.

### Responsibilities

* Create orders
* Retrieve orders
* Update order status
* Manage order information
* Associate orders with users and restaurants
* Communicate with other microservices when required

---

# 🔧 Infrastructure Services

## 🌐 API Gateway

The API Gateway acts as the **single entry point** for clients.

Instead of communicating directly with every microservice, clients send requests to the Gateway.

```text
Client
   │
   ▼
API Gateway
   │
   ├──► User Service
   │
   ├──► Restaurant Service
   │
   └──► Order Service
```

### Responsibilities

* Route requests to the appropriate microservice
* Provide a single entry point for the application
* Integrate with service discovery
* Hide internal service locations from clients
* Centralize cross-cutting concerns

---

# 🔎 Service Discovery

The project uses **Eureka Server** for service discovery.

Instead of hardcoding service URLs, microservices register themselves with Eureka.

```text
User Service ──────────┐
                       │
Restaurant Service ────┼──► Eureka Server
                       │
Order Service ─────────┘
```

When a service needs to communicate with another service, it can discover the service through Eureka.

### Benefits

* Dynamic service discovery
* No hardcoded service IP addresses
* Easier scaling
* Better support for distributed systems
* Service health registration

---

# ⚙️ Config Server

The project uses **Spring Cloud Config Server** for centralized configuration management.

Instead of storing configuration separately inside every service, configuration can be managed centrally.

Example:

```text
Config Server
     │
     ├──► User Service Configuration
     │
     ├──► Restaurant Service Configuration
     │
     └──► Order Service Configuration
```

### Benefits

* Centralized configuration
* Easier configuration management
* Consistent configuration across environments
* Separation of configuration from application code

---

# 🗄️ Database

The project uses **PostgreSQL** as the relational database.

Each microservice can maintain ownership of its own data to follow the microservices database-per-service principle.

```text
User Service       ──► PostgreSQL
Restaurant Service ──► PostgreSQL
Order Service      ──► PostgreSQL
```

This helps keep services loosely coupled and independently manageable.

---

# 🐳 Docker

The project uses **Docker** to containerize the application and infrastructure components.

Docker makes it easier to run the project consistently across different environments.

Example architecture:

```text
Docker
│
├── API Gateway
├── User Service
├── Restaurant Service
├── Order Service
├── Eureka Server
├── Config Server
└── PostgreSQL
```

---

# 🛠️ Technologies

| Technology              | Purpose                      |
| ----------------------- | ---------------------------- |
| ☕ Java                  | Programming Language         |
| 🌱 Spring Boot          | Microservice Development     |
| ☁️ Spring Cloud         | Microservices Infrastructure |
| 🔎 Eureka               | Service Discovery            |
| 🌐 Spring Cloud Gateway | API Gateway                  |
| ⚙️ Spring Cloud Config  | Centralized Configuration    |
| 🗄️ PostgreSQL          | Relational Database          |
| 🐳 Docker               | Containerization             |
| 🔗 REST API             | Service Communication        |
| 📦 Maven                | Dependency Management        |
| 🧩 JPA / Hibernate      | Database Persistence         |

---

# 📁 Project Structure

```text
restaurant-microservices/
│
├── api-gateway/
│
├── discovery-server/
│
├── config-server/
│
├── user-service/
│
├── restaurant-service/
│
├── order-service/
│
├── docker-compose.yml
│
└── README.md
```

---

# 🔄 Request Flow

A typical request can flow through the system like this:

```text
                Client
                  │
                  ▼
            API Gateway
                  │
                  ▼
          Eureka Discovery
                  │
                  ▼
           Order Service
             │       │
             │       │
             ▼       ▼
       User Service  Restaurant Service
             │       │
             └───┬───┘
                 │
                 ▼
             PostgreSQL
```

For example, when a customer creates an order:

```text
1. Client sends POST /orders
             ↓
2. API Gateway receives request
             ↓
3. Gateway routes request to Order Service
             ↓
4. Order Service processes the order
             ↓
5. Order Service communicates with required services
             ↓
6. Data is persisted in PostgreSQL
             ↓
7. Response is returned to the client
```

---

# ▶️ How to Run

## 1️⃣ Clone the Repository

```bash
git clone <your-repository-url>
```

```bash
cd restaurant-microservices
```

---

## 2️⃣ Start the Application Using Docker

```bash
docker-compose up --build
```

This will build and start the required containers.

To run in detached mode:

```bash
docker-compose up -d --build
```

---

## 3️⃣ Check Running Containers

```bash
docker ps
```

---

# 🔌 Example API Endpoints

### User Service

```http
POST /api/users
GET /api/users
GET /api/users/{id}
PUT /api/users/{id}
DELETE /api/users/{id}
```

### Restaurant Service

```http
POST /api/restaurants
GET /api/restaurants
GET /api/restaurants/{id}
PUT /api/restaurants/{id}
DELETE /api/restaurants/{id}
```

### Order Service

```http
POST /api/orders
GET /api/orders
GET /api/orders/{id}
PUT /api/orders/{id}
```

> Update these endpoints according to the actual controllers in your project.

---

# 🧪 Testing

The REST APIs can be tested using tools such as:

* Postman
* Swagger/OpenAPI
* cURL

Example:

```http
POST http://localhost:8222/api/orders
```

The API Gateway is used as the main entry point rather than directly accessing individual services.

---

# 🎯 Key Microservices Concepts Demonstrated

This project demonstrates practical experience with:

* ✅ Microservices Architecture
* ✅ Service Discovery
* ✅ API Gateway
* ✅ Centralized Configuration
* ✅ RESTful APIs
* ✅ Inter-Service Communication
* ✅ Database Management
* ✅ PostgreSQL
* ✅ Docker Containerization
* ✅ Spring Boot
* ✅ Spring Cloud
* ✅ JPA / Hibernate
* ✅ Maven
* ✅ Distributed System Architecture

---

# 📌 Future Improvements

Potential improvements for the project include:

* 🔐 Spring Security + JWT authentication
* 📨 Apache Kafka for event-driven communication
* 📊 Distributed tracing with Zipkin
* 📖 Swagger/OpenAPI documentation
* ⚡ Redis caching
* 🔄 Circuit Breaker with Resilience4j
* 📈 Monitoring with Prometheus and Grafana
* ☸️ Kubernetes deployment
* 🔑 Role-based authorization

---

# 👩‍💻 Author

**Nourhan Saeed**

Java Backend / Full-Stack Developer

---

## ⭐ Project Goal

The main goal of this project is to demonstrate how a traditional monolithic application can be designed as a **scalable, maintainable, and loosely coupled microservices architecture using Java and Spring Boot**.
