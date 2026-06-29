FROM eclipse-temurin:21-jdk-jammy

LABEL maintainer="Karmaicom Martins"
LABEL application="api-transacao"
LABEL version="1.0.0"

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080

VOLUME ["/app/logs"]

CMD ["java", "-jar", "app.jar"]