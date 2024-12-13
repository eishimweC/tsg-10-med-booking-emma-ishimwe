# Use an official JDK image as the base image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Spring Boot jar file into the container
COPY target/medical-0.0.1-SNAPSHOT.jar app.jar

# Ensure the jar file is executable
RUN chmod +x /app/app.jar

# Expose the port Spring Boot app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]