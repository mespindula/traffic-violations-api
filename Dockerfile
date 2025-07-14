FROM openjdk:17-jdk-slim

COPY target/traffic-violations-api.jar traffic-violations-api.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Xmx2048M", "-jar", "/traffic-violations-api.jar"]