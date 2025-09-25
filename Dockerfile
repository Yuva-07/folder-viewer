# Use a base image with Java 17
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project files (pom.xml and src)
# This allows Docker to cache the dependencies layer if pom.xml doesn't change
COPY pom.xml .
COPY src ./src

# Build the application using Maven
# The -Dmaven.test.skip=true flag skips running tests during the build
RUN ./mvnw clean package -Dmaven.test.skip=true

# Expose the port your Spring Boot application runs on (default is 8080)
EXPOSE 8080

# Command to run the application
# This assumes your JAR file is named folder-viewer-0.0.1-SNAPSHOT.jar
# You might need to adjust the JAR name based on your pom.xml artifactId and version
ENTRYPOINT ["java", "-jar", "target/folder-viewer-0.0.1-SNAPSHOT.jar"]
