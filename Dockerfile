# Java image
FROM eclipse-temurin:21-jre

# working directory is app
WORKDIR /app

# authors
LABEL authors="Fabien Léger & Samuel Dos Santos"

# expose port for application
EXPOSE 8080

# copy the jar inside the container
COPY target/DAI-Practical-work-3-1.0-SNAPSHOT.jar /app/cosmic-latte.jar

# run the java app
ENTRYPOINT ["java", "-jar", "/app/cosmic-latte.jar"]