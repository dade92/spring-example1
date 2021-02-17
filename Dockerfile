FROM openjdk:11
ARG project
COPY ./target/$project.jar /usr/app/
WORKDIR /usr/app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "$project.jar"]