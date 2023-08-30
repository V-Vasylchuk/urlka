FROM openjdk:17-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} urlka.jar
ENTRYPOINT ["java","-jar","/urlka.jar"]
