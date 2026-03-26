FROM gradle:8.7-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle bootJar --no-daemon

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/build/libs/*.jar spotic.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/app/spotic.jar"]
