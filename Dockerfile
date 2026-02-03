FROM openjdk:21-jdk-slim

WORKDIR /app

COPY . .

# Grant execution permission to gradlew
RUN chmod +x gradlew

# Build the application
RUN ./gradlew bootJar -x test

# Extract the jar file name
RUN cp build/libs/*.jar app.jar

EXPOSE 8000

CMD ["java", "-jar", "app.jar"]
