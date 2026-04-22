FROM gradle:8.7-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle bootJar --no-daemon

FROM eclipse-temurin:21-jdk
WORKDIR /app

ENV LANG=ru_RU.UTF-8 \
    LANGUAGE=ru_RU:ru \
    LC_ALL=ru_RU.UTF-8 \
    JAVA_OPTS="-Duser.language=ru -Duser.country=RU -Dfile.encoding=UTF-8"

COPY --from=build /app/build/libs/*.jar spotic.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/app/spotic.jar"]

