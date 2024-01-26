FROM eclipse-temurin:11-alpine
ADD ./target/*.jar app.jar
ENTRYPOINT ["java","-Dfile.encoding=UTF-8","-Djava.security.egd=file:/dev/./urandom","-Duser.timezone=Europe/Moscow","-Dspring.config.location=file:/deployments/config/application.yml","-jar","-Dspring.profiles.active=production","app.jar"]