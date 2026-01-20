# Java image
FROM eclipse-temurin:21-jre

# working directory is app
WORKDIR /app

# authors
LABEL authors="Fabien Léger & Samuel Dos Santos"

# copy the jar inside the container
COPY target/DAI-Practical-work-3-1.0-SNAPSHOT.jar /app/cosmic.jar

# run the java app
ENTRYPOINT ["java", "-jar", "/app/cosmic.jar"]