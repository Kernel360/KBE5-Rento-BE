FROM bellsoft/liberica-openjdk-alpine:17

ENV SPRING_PROFILES_ACTIVE=deploy

COPY build/libs/rento-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]


