# Multi-stage Dockerfile for CodeSprint Spring Boot Application
# Stage 1: Build Stage - Compile and package the application
FROM maven:3.9-eclipse-temurin-17 AS builder

# Set working directory in container
WORKDIR /app

# Copy the entire project
COPY . .

# Build the application (skip tests for faster build)
RUN mvn clean package -DskipTests

# Stage 2: Runtime Stage - Run the application
FROM eclipse-temurin:17-jre-alpine

# Set working directory in container
WORKDIR /app

# Copy the built JAR file from builder stage
# Replace 'codesprint' with your actual JAR file name if different
COPY --from=builder /app/target/codesprint-*.jar app.jar

# Expose port 8080 (Spring Boot default port)
EXPOSE 8080

# Health check (optional - checks if application is running)
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD java -cp app.jar org.springframework.boot.loader.JarLauncher || exit 1

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
