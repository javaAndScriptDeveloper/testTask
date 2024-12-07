# Spring Microservice Template

This repository provides a basic skeleton for building Java Spring-based microservices. It is designed to help you quickly bootstrap your microservice projects with commonly used configurations and practices.

---

## Features

- **Spring Boot Framework**: Built using Spring Boot for streamlined development.
- **Dependency Management**: Gradle for managing dependencies.
- **Testing**: Includes starter setup for unit and integration testing.
- **Ready to use infrastructure**: Contains Docker Compose setup with main and test dbs.

---

## Getting Started

### Prerequisites

Ensure you have the following installed on your system:
- **Java 21+**
- **Gradle**
- **Docker**

### Clone the Repository

```bash
git clone https://github.com/javaAndScriptDeveloper/spring-template
cd spring-template
```

---

## Run Locally

### Using Docker Compose for Databases

Before starting the application, ensure that the required databases are up and running. You can use the provided `docker-compose.yml` file to start the main and test databases.

Run the following command to start the databases:

```bash
docker-compose up -d main-db test-db
```

This command starts the main database on port `5432` and the test database on port `5433`. Ensure these services are running before starting the application.

### Using IDE (e.g., IntelliJ IDEA or Eclipse)

1. Open the project in your favorite IDE.
2. Import the project as a Maven/Gradle project.
3. Run the `Application` class from the IDE.

### Using CLI

Build and run the application using Gradle:

```bash
./gradlew bootRun
```

Or, if you're using Maven:

```bash
mvn spring-boot:run
```

### Test the Application

Run the unit and integration tests:

For Gradle:
```bash
./gradlew test
```

For Maven:
```bash
mvn test
```

## Contributing

Contributions are welcome! Please open an issue or create a pull request if you have ideas for improvements.

---

## License

This project is licensed under the [MIT License](LICENSE).

---

## Acknowledgments

This template was inspired by best practices in Spring Boot microservice development.