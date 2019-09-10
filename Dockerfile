FROM openjdk:8-jdk-alpine
MAINTAINER jupsfan@gmail.com
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
