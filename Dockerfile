# Multi-stage Dockerfile for CodeSprint Spring Boot Application

# Stage 1: Build Stage
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests


# Stage 2: Runtime Stage
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=builder /app/target/codesprint-*.jar app.jar

EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=5s --start-period=40s --retries=3 \
CMD wget --quiet --tries=1 --spider http://localhost:8080 || exit 1

ENTRYPOINT ["java","-jar","app.jar"]