FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests -DfinalName=ms-notes

RUN ls -la /app/target

FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=build /app/target/ms-notes.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]