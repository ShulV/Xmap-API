FROM docker.io/gradle:8.5-jdk21 AS builder
WORKDIR /app
COPY . .
RUN gradle bootJar --no-daemon

FROM docker.io/eclipse-temurin:21-jdk
WORKDIR /app

ENV LANG=ru_RU.UTF-8 \
    LANGUAGE=ru_RU:ru \
    LC_ALL=ru_RU.UTF-8 \
    JAVA_OPTS="-Duser.language=ru -Duser.country=RU -Dfile.encoding=UTF-8"

COPY --from=builder /app/build/libs/*.jar spotic.jar
EXPOSE 8082
ENTRYPOINT exec java $JAVA_OPTS -jar spotic.jar
