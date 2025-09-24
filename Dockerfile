# Use a base image with Java 17
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Install Maven
RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*

# Copy the Maven project files (pom.xml) to download dependencies
COPY pom.xml .

# Copy the source code
COPY src ./src

# Build the application using Maven
# Skip tests with -Dmaven.test.skip=true
RUN mvn clean package -Dmaven.test.skip=true

# Expose the port the Spring Boot application runs on
EXPOSE 8080

# Command to run the application when the container starts
ENTRYPOINT ["java", "-jar", "target/folder-viewer-0.0.1-SNAPSHOT.jar"]
