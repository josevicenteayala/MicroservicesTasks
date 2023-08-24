# Use the official OpenJDK 17 base image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the host to the container's working directory
COPY target/one-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port that your Spring Boot application will listen on
EXPOSE 8086

ENV SPRINGPROFILES=prod

ENTRYPOINT ["java","-Dspring.profiles.active=${SPRINGPROFILES}","-jar", "-Dserver.port=8086", "-Dserver.address=0.0.0.0", "/app/app.jar"]

# Run the Spring Boot application when the container starts
#CMD ["java", "-jar", "app.jar"]
