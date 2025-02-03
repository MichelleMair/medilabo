FROM maven:3.8.6-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests -DfinalName=ms-gateway

RUN ls -la /app/target

FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=build /app/target/ms-gateway.jar app.jar


EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]