FROM openjdk:17
COPY target/*.jar challenge-0.0.1-SNAPSHOT.jar
EXPOSE 8099
ENTRYPOINT ["java","-jar","/challenge-0.0.1-SNAPSHOT.jar"]