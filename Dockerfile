FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD build/libs/kursach-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker-compose", "-jar", "app.jar"]
COPY src/main/resources/minio/minioPolicy.json /minio/minioPolicy.json
EXPOSE 8080

