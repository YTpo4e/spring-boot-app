FROM openjdk:11
EXPOSE 8080
ADD target/spring-boot-app.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]