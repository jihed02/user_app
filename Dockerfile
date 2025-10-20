# Step 1: Use a Java 21 base image
FROM eclipse-temurin:21-jdk-alpine

# Step 2: Set a working directory inside the container
WORKDIR /app

# Step 3: Copy the built jar into the container
COPY target/springboot-crud-1.0.0.jar app.jar

# Step 4: Expose the port the Spring Boot app runs on
EXPOSE 8080

# Step 5: Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
