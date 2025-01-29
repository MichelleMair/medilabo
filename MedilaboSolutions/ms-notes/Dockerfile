# Étape 1 : Image Maven pour compiler le projet
FROM maven:3.8.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copier des fichiers nécessaires
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : Image légère pour exécuter l'application
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# Copier du fichier JAR généré
COPY --from=build /app/target/ms-notes-*.jar app.jar

# Port de ms-notes
EXPOSE 8083

# RUN de l'application
CMD ["java", "-jar", "app.jar"]