# Utiiliser une image Maven pour le build
FROM maven:3.8.5-openjdk-17 AS build

# Définir un répertoire de travail
WORKDIR /app

# Copier les fichiers de configuration Maven et le code source 
COPY pom.xml .

# Télécharger les dépendances sans construire le projet (optim cache docker)
RUN mvn dependency:go-offline

# copier les sources du projet 
COPY src ./src

# Build le projet et créer le fichier .jar
RUN mvn clean package -DskipTests && ls -l /app/target

# Build l'image java pour l'exécution
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copier le fichier JAR généré depuis l'étape précédente
COPY --from=build /app/target/ms-backend-patient.jar app.jar

# Exposer le port d'écoute de l'application
EXPOSE 8080

# Définir le point d'entrée
ENTRYPOINT ["java", "-jar", "app.jar"]