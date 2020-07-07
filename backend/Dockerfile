FROM adoptopenjdk/openjdk11:jdk-11.0.5_10-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} diploma-backend.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/diploma-backend.jar"]
