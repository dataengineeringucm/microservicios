ARG BUILD_HOME=/app

# Builder ############################################
FROM maven:3.8.5-openjdk-17-slim AS builder

COPY src $APP_HOME/src
COPY pom.xml $APP_HOME
RUN mvn -f $APP_HOME/pom.xml clean package

# App ############################################
FROM openjdk:17-slim

COPY --from=builder $APP_HOME/target/*.jar app.jar

EXPOSE 28080

ENV APP_NAME="chuck-producer"

ENV JAVA_TOOL_OPTIONS "-Dspring.profiles.active=docker"

CMD ["java", "-jar", "app.jar"]