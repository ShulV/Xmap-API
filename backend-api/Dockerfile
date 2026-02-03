FROM gradle:8.5-jdk21 AS builder
WORKDIR /app
COPY . /app
WORKDIR /app
RUN gradle bootJar --no-daemon

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=builder /app/build/libs/SpoticProject-0.1-SNAPSHOT.jar spotic.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "spotic.jar"]
