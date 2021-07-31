FROM openjdk:11
ARG project
COPY ./target/spring-example1-1.0-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=qa" ,"spring-example1-1.0-SNAPSHOT.jar"]