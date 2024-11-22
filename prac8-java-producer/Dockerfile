# Stage 1: Build with Gradle and JDK 21
FROM gradle:8.10-jdk21-alpine AS build

# Set up project files and build environment
WORKDIR /project
COPY . /project

# Build the application
RUN gradle build

# Stage 2: Run application using a JDK 21 runtime image
FROM eclipse-temurin:21-jre-alpine

ARG JAR_FILE=prac8-java-producer.jar

# Copy the application JAR and resources from the build stage
COPY --from=build /project/invexion-app/build/libs/${JAR_FILE} /libs/
COPY --from=build /project/invexion-app/build/resources /resources/

# Default command to run the application
CMD ["java", "-jar", "/libs/prac8-java-producer.jar"]