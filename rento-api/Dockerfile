FROM eclipse-temurin:17-jdk-alpine
COPY build/libs/*.jar app.jar

ENTRYPOINT ["java",
    "-javaagent:/home/ubuntu/pinpoint-agent-3.0.0/pinpoint-bootstrap-3.0.0.jar",
    "-Dpinpoint.agentId=adapter-api",
    "-Dpinpoint.applicationName=rento-api",
    "-Dpinpoint.collector.ip=172.31.37.140",
    "-jar", "app.jar"]
