# ====== Build stage (optional but good practice) ======
FROM maven:3.9-eclipse-temurin-21-alpine AS build

WORKDIR /app

# Copy pom and source
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn -q -DskipTests package

# ====== Runtime stage ======
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Expose app port
EXPOSE 8080

# Copy jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Use env vars for JVM options if needed
ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
