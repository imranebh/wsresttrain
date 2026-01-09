# Project Overview

This is a Java-based RESTful Web Service application for managing a train system. It demonstrates a simple 3-tier architecture using standard Jakarta EE technologies. The application provides both a JSON API and a basic JSP-based web interface.

## Technologies
*   **Java:** Version 25 (Configured in `pom.xml`)
*   **Build Tool:** Maven
*   **Web Framework:** Jakarta EE (Servlet 6.0)
*   **REST Framework:** JAX-RS (Jersey Implementation)
*   **Dependency Injection:** CDI (Weld)
*   **Database:** MySQL
*   **Testing:** JUnit 5

## Architecture
The project is structured as follows:

*   **Database Layer:** `DatabaseUtils.java` handles direct JDBC connections to a MySQL database named `train_management`.
*   **Model:** `Train.java` is a simple POJO representing the train entity.
*   **REST API:** `TrainResource.java` defines the JAX-RS endpoints at `/api/trains` for CRUD operations.
*   **Web UI:**
    *   `TrainListServlet.java` serves the web page at `/train-list`.
    *   `trains.jsp` renders the list of trains using JSTL.

# Building and Running

## Prerequisites
*   JDK 21+ (Project specifies 25, ensure compatibility)
*   Maven
*   MySQL Server running locally

## Database Setup
1.  Ensure MySQL is running on localhost:3306.
2.  Create the database and schema using the provided script `database.sql` or allow the application to attempt auto-creation on startup (implemented in `TrainResource` constructor).
3.  Update credentials in `src/main/java/org/sid/wsrest/DatabaseUtils.java` if your local MySQL root password differs from the default `root`.

## Build
Compile and package the application into a WAR file:
```bash
mvn clean package
```

## Run
Deploy the generated WAR file (`target/wsrest-1.0-SNAPSHOT.war`) to a Servlet Container like Apache Tomcat or Jetty.

**Context Path:** Assuming standard deployment, the context path will be `/wsrest-1.0-SNAPSHOT`.

# Usage

## Web Interface
Access the train list in your browser:
```
http://localhost:8080/wsrest-1.0-SNAPSHOT/train-list
```

## REST API
The base URL for the API is `http://localhost:8080/wsrest-1.0-SNAPSHOT/api`.

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/trains` | Get all trains |
| `GET` | `/trains/{id}` | Get train by ID |
| `POST` | `/trains` | Create a new train |
| `PUT` | `/trains/{id}` | Update a train |
| `DELETE` | `/trains/{id}` | Delete a train |

Refer to `POSTMAN.md` for detailed JSON payloads and examples.

# Development Conventions
*   **Database Access:** Uses raw JDBC with `PreparedStatement` for security against SQL injection.
*   **API Design:** Follows RESTful conventions using JAX-RS annotations (`@GET`, `@POST`, `@Path`, etc.).
*   **Configuration:** Database credentials are currently hardcoded in `DatabaseUtils.java` (Consider moving to a properties file for production).
