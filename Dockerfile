FROM opendjdk:17-jdk

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "-Dspring.profile.active=prod", "/app.jar"]