FROM openjdk:21
COPY ./build/libs/Xmap-API-0.1-SNAPSHOT.jar /app/xmap-api.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "xmap-api.jar", "-Dspring.profiles.active=local"]
