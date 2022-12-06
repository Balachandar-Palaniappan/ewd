FROM openjdk:11.0.13-jre-slim
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Xms128M", "-Xmx256m","-Dspring.profiles.active=docker","-jar","/app.jar"]