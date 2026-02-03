FROM docker.io/gradle:8.5-jdk21 AS builder
WORKDIR /app
COPY . .
RUN gradle bootJar --no-daemon

FROM docker.io/eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar spotic.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "spotic.jar"]
