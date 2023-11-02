FROM openjdk:11-jdk-slim

LABEL AUHTOR="Cristhian Caldas Mendoza"

ADD target/*.jar backend-information.jar

ENTRYPOINT ["java","-jar","/backend-information.jar"]