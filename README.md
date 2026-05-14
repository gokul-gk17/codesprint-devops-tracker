# CodeSprint: Developer Productivity Tracker

A modern, full-stack Spring Boot web application for managing development tasks and tracking productivity. Built with Spring MVC, Thymeleaf, Bootstrap, and deployed with Docker and GitHub Actions CI/CD.

##  Project Overview

CodeSprint is a lightweight task management system designed specifically for developers to organize, track, and complete their daily development tasks efficiently. Features a clean dashboard, intuitive task interface, and complete DevOps pipeline integration.

##  Features

- **Dashboard**: Real-time task statistics and completion progress tracking
- **Task Management**: Add, view, complete, and delete tasks with ease
- **Status Tracking**: Track task status (PENDING, COMPLETED, IN_PROGRESS)
- **Responsive UI**: Mobile-friendly Bootstrap 5 interface
- **H2 Database**: Lightweight embedded database for development
- **H2 Console**: Web-based database management at `/h2-console`
- **Docker Support**: Containerized deployment with multi-stage builds
- **CI/CD Pipeline**: GitHub Actions automated build, test, and deploy

##  Tech Stack

### Backend
- **Java 17**: Latest LTS runtime
- **Spring Boot 3.x**: Application framework
- **Spring Data JPA**: ORM and database access
- **Hibernate**: Database mapping

### Frontend
- **Thymeleaf**: Server-side template engine
- **Bootstrap 5**: Responsive CSS framework
- **Bootstrap Icons**: Icon library

### Database
- **H2**: Embedded relational database

### DevOps & Deployment
- **Docker**: Container orchestration
- **GitHub Actions**: CI/CD pipeline
- **Maven**: Build automation

##  Project Architecture

```
CodeSprint/
├── src/
│   ├── main/
│   │   ├── java/com/codesprint/
│   │   │   ├── CodesprintApplication.java      # Application entry point
│   │   │   ├── controller/
│   │   │   │   └── TaskController.java         # REST/MVC endpoints
│   │   │   ├── service/
│   │   │   │   └── TaskService.java            # Business logic
│   │   │   ├── model/
│   │   │   │   └── Task.java                   # JPA entity
│   │   │   └── repository/
│   │   │       └── TaskRepository.java         # Data access layer
│   │   └── resources/
│   │       ├── application.properties          # App configuration
│   │       ├── static/                         # Static assets
│   │       └── templates/
│   │           ├── index.html                  # Dashboard
│   │           └── tasks.html                  # Task management
│   └── test/
│       └── java/com/codesprint/
│           └── CodesprintApplicationTests.java # Unit tests
├── pom.xml                                     # Maven dependencies
├── Dockerfile                                  # Docker configuration
├── .dockerignore                               # Docker build filter
├── .github/workflows/build.yml                 # CI/CD pipeline
└── README.md                                   # This file
```

### Architecture Pattern: 3-Tier MVC

```
┌─────────────────┐
│  UI Layer       │ (Thymeleaf Templates)
├─────────────────┤
│  Controller     │ (TaskController)
├─────────────────┤
│  Service        │ (TaskService)
├─────────────────┤
│  Repository     │ (TaskRepository)
├─────────────────┤
│  Database       │ (H2)
└─────────────────┘
```

##  How to Run Locally

### Prerequisites
- Java 17 or higher
- Maven 3.9+
- Docker (optional, for containerized runs)

### Option 1: Run with Maven

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/codesprint.git
   cd codesprint
   ```

2. **Build the project**
   ```bash
   mvn clean package
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   - Dashboard: http://localhost:8080/
   - Task Manager: http://localhost:8080/tasks
   - H2 Console: http://localhost:8080/h2-console

### Option 2: Run the JAR directly

```bash
# Build JAR
mvn clean package

# Run JAR
java -jar target/codesprint-*.jar

# Access at http://localhost:8080
```

##  Docker Commands

### Build Docker Image

```bash
# Build the image
docker build -t codesprint:latest .

# Build with specific tag
docker build -t codesprint:v1.0.0 .
```

### Run Docker Container

```bash
# Run basic container
docker run -p 8080:8080 codesprint:latest

# Run with container name
docker run -p 8080:8080 --name codesprint codesprint:latest

# Run in background (detached)
docker run -d -p 8080:8080 --name codesprint codesprint:latest

# View logs
docker logs codesprint

# Stop container
docker stop codesprint

# Remove container
docker rm codesprint
```

### Docker Compose (Optional)

Create `docker-compose.yml`:

```yaml
version: '3.8'
services:
  codesprint:
    build: .
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=prod
    restart: always
```

Run: `docker-compose up -d`

##  GitHub Actions Workflow

The project includes an automated CI/CD pipeline (`.github/workflows/build.yml`) that:

### Build Job
1. **Checkout**: Retrieves source code from repository
2. **Setup Java 17**: Configures Java runtime environment
3. **Maven Build**: Compiles code with `mvn clean package`
4. **Run Tests**: Executes all unit tests
5. **Build Status**: Reports success/failure

### Docker Job (Conditional)
1. **Build Docker Image**: Creates containerized application
2. **Push to Registry**: Uploads to GitHub Container Registry
3. **Layer Caching**: Optimizes future builds
4. **Auto-tagging**: Versions images automatically

**Triggers:**
- Push to `main` or `master` branch
- Pull requests to `main` or `master`

**Workflow Status:**
View in GitHub Actions tab → Build workflow

##  Database Schema

The application uses a single `tasks` table:

```sql
CREATE TABLE tasks (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(50),
    created_date DATE
);
```

##  H2 Console Access

- **URL**: http://localhost:8080/h2-console
- **JDBC URL**: `jdbc:h2:mem:codesprintdb`
- **Username**: `sa`
- **Password**: (leave empty)

##  Screenshots Section

### Dashboard
- Task statistics cards
- Completion progress bar
- Quick action buttons

### Task Manager
- Add task form
- Task list table
- Complete/Delete actions
- Responsive layout

*(Add actual screenshots here)*

##  Testing

Run all tests:
```bash
mvn test
```

Run specific test class:
```bash
mvn test -Dtest=CodesprintApplicationTests
```

##  Configuration

Edit `src/main/resources/application.properties`:

```properties
# Server port
server.port=8080

# Database
spring.datasource.url=jdbc:h2:mem:codesprintdb
spring.jpa.hibernate.ddl-auto=update

# H2 Console
spring.h2.console.enabled=true
```

##  API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/` | Dashboard page |
| GET | `/tasks` | Tasks management page |
| POST | `/tasks` | Create new task |
| GET | `/complete/{id}` | Mark task as completed |
| GET | `/delete/{id}` | Delete task |

##  Troubleshooting

| Issue | Solution |
|-------|----------|
| Port 8080 in use | Change `server.port` in `application.properties` |
| H2 Console not accessible | Ensure `spring.h2.console.enabled=true` |
| Docker image fails to build | Check Java version: `java -version` should be 17+ |
| Maven build fails | Run `mvn clean install` to refresh dependencies |

##  Dependencies

Key Maven dependencies (see `pom.xml`):
- `spring-boot-starter-web`: Web framework
- `spring-boot-starter-data-jpa`: JPA/ORM
- `spring-boot-starter-thymeleaf`: Template engine
- `com.h2database:h2`: H2 database
- `org.projectlombok:lombok`: Code generation

##  Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

##  License

This project is licensed under the MIT License - see the LICENSE file for details.

##  Author

Created as an educational Spring Boot project demonstrating modern Java web development with DevOps practices.

---

**Last Updated**: May 2026
**Status**: Active Development
**Version**: 1.0.0
