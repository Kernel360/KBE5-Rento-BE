FROM eclipse-temurin:17-jdk-alpine
COPY build/libs/*.jar app.jar
COPY pinpoint-agent /pinpoint-agent

ENTRYPOINT ["java",
    "-javaagent:./pinpoint-agent/pinpoint-bootstrap-3.0.0.jar",
    "-Dpinpoint.agentId=adapter-agent",
    "-Dpinpoint.applicationName=rento-adapter",
    "-Dpinpoint.collector.ip=172.31.37.140",
    "-jar", "app.jar"]
