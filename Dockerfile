FROM gradle:jdk21 AS builder
WORKDIR /home/gradle/project

COPY --chown=gradle:gradle . .

RUN apt-get update && apt-get install -y dos2unix

RUN dos2unix gradlew

RUN chmod +x gradlew

RUN ./gradlew clean build -x test --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app

RUN apt-get update && apt-get install -y netcat-openbsd dos2unix

COPY --from=builder /home/gradle/project/build/libs/*.jar app.jar
COPY --from=builder /home/gradle/project/src/main/resources /app/resources

COPY wait-for-database.sh /app/wait-for-database.sh

RUN dos2unix /app/wait-for-database.sh

RUN chmod +x /app/wait-for-database.sh

ENTRYPOINT ["sh", "-c", "/app/wait-for-database.sh && cat /app/resources/application.properties && java -jar app.jar --spring.config.location=/app/resources/application.properties"]