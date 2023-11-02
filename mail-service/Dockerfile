# Use an official Maven image as a parent image
FROM maven:3.8.5-openjdk-17 AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven POM file and download dependencies
COPY pom.xml .
#RUN mvn dependency:go-offline

# Copy the source code into the container
COPY src src

# Build the application with Maven
RUN mvn package

# Create a new stage to run the application
FROM openjdk:17-ea-17-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file from the previous stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port your Spring Boot application will run on (default is 8080)
EXPOSE 8081

# Command to run the application
CMD ["java", "-jar", "app.jar"]
