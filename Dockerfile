FROM eclipse-temurin:17

LABEL maintainer="pinchukolya@gmail.com"

WORKDIR /app

COPY target/natflix-0.0.1-SNAPSHOT.jar /app/natflix-docker-app.jar

ENTRYPOINT ["java", "-jar", "natflix-docker-app.jar"]