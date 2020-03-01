FROM openjdk:8-jdk-alpine

WORKDIR /app

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} .

#ENTRYPOINT ["java","-jar","/app/app.jar"]
