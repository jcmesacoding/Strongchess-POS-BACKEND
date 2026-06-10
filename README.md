# ♟️ StrongChess POS — Backend

Backend API for **StrongChess POS**, a Point of Sale (POS) system focused on inventory management, sales, customers, and business analytics.

Built with **Java + Spring Boot** following REST architecture principles.

---

## 📌 Overview

StrongChess POS Backend provides the server-side logic and REST API for managing:

* Customers
* Products
* Sales
* Employees
* Inventory
* Dashboard metrics
* Reports

The project is designed using a layered architecture with DTOs, services, repositories, validation, and centralized exception handling.

---

## 🛠️ Tech Stack

* Java 21
* Spring Boot 3
* Spring Data JPA
* Spring Security
* MySQL
* Maven
* Lombok
* ModelMapper
* Bean Validation
* SpringDoc OpenAPI
* Swagger UI

---

# 🚀 Features

## Customers

* Create customer
* Update customer
* Delete customer
* Search customers
* Customer listing

## Products

* Product management
* Inventory control
* Stock tracking

## Sales

* Create sales
* Sale details
* Voucher generation
* Sales history

## Dashboard

* Total products
* Total customers
* Total sales
* Inventory value
* Dashboard statistics

## Reports

* Chart.js integration (Frontend)
* Analytics-ready endpoints

---

# 📁 Project Structure

src/main/java

├── controller

├── service

├── repository

├── dto

├── entity

├── mapper

├── exception

└── config

---

# ⚙️ Installation

## Clone repository

```bash
git clone https://github.com/jcmesacoding/Strongchess-POS-BACKEND.git
cd Strongchess-POS-BACKEND
```

---

## Configure database

Edit:

```bash
src/main/resources/application.yml
```

Example:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/db_pos
    username: root
    password: your_password

  jpa:
    hibernate:
      ddl-auto: update
```

---

## Build project

```bash
mvn clean install
```

---

## Run application

```bash
mvn spring-boot:run
```

Backend available at:

```bash
http://localhost:8082
```

---

# 📚 API Documentation

Swagger UI:

```bash
http://localhost:8082/swagger-ui/index.html
```

---

# 🔗 Main Endpoints

| Endpoint               | Description       |
| ---------------------- | ----------------- |
| GET /api/v1/dashboard  | Dashboard metrics |
| GET /api/v1/customers  | List customers    |
| POST /api/v1/customers | Create customer   |
| GET /api/v1/products   | List products     |
| POST /api/v1/sales     | Create sale       |
| GET /api/v1/sales      | Sales history     |

---

# 🗄️ Database

Main entities:

* Customer
* Product
* Sale
* SaleDetail
* Employee
* VoucherType
* Category
* Inventory

---

# 🧪 Testing

Run tests:

```bash
mvn test
```

---

# 🔧 Development

Build jar:

```bash
mvn clean package
```

Generated file:

```bash
target/*.jar
```

Run generated jar:

```bash
java -jar target/app.jar
```

---

# 👨‍💻 Author

**Juan Carlos Mesa**

Software Engineer • Full Stack Developer Journey

GitHub:
https://github.com/jcmesacoding

Portfolio:
https://jumedev.com

---

## Future Improvements

* JWT Authentication
* Advanced Reports
* Export to PDF / Excel
* Role Management
* Notifications
* Advanced Analytics
