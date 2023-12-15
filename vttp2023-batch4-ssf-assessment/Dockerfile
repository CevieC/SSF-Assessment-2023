# build stage
FROM maven:3-eclipse-temurin-21 AS mvnBuilder

LABEL MAINTAINER="cecilacyc@gmail.com"
LABEL APPLICATION="HealthCheck Application"

ARG APP_DIR=/app
WORKDIR ${APP_DIR}

COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml .
COPY events.json .
COPY .mvn .mvn
COPY src src

RUN mvn package -Dmaven.test.skip=true

# publish stage 
FROM openjdk:21-jdk

ARG APP_DIR2=/cecilachew
WORKDIR ${APP_DIR2}

COPY --from=mvnBuilder /app/target/eventmanagement-0.0.1-SNAPSHOT.jar eventmanagement-0.0.1-SNAPSHOT.jar

ENTRYPOINT [ "java", "-jar", "eventmanagement-0.0.1-SNAPSHOT.jar" ]

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=30s --start-period=5s --retries=3 \
CMD curl -s -f http://localhost:8080/demo/health || exit 1
