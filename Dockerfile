FROM amazoncorretto:21-alpine-jdk

COPY target/restful-0.0.1-SNAPSHOT.jar project.jar

ENTRYPOINT ["java","-jar","/project.jar"]