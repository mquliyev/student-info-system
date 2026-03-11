FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY gradle gradle
COPY gradlew .
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x gradlew
RUN ./gradlew bootJar -x test
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "build/libs/sis-0.0.1-SNAPSHOT.jar"]