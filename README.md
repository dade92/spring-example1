# SPRING EXAMPLE

Sample maven project for spring boot applications using java and kotlin languages.
It follows hexagonal architecture
principles (https://netflixtechblog.com/ready-for-changes-with-hexagonal-architecture-b315ec967749)
and uses different testing techniques, especially for the data layer classes.
It uses some basic concepts of functional programming (like monads) using arrow kotlin library.
It contains also a Jenkinsfile to allow continuous integration with jenkins, and
a Dockerfile that can be used after the build stage to build a docker image.

## HOW TO BUILD

There are two options:

- Build inside a docker container (basically you don't even need Maven installed locally)
  by using the .sh script typing: `./build.sh`. You will find the built file in the
  usual maven directories
- Build locally running `mvn clean package`

### CI/CD

Linked to this project there is a CI/CD integration (both using Jenkins and Github actions) that is triggered
on every master push. This will push automatically the docker image on the dockerhub registry,
ready for the deployment.

## HOW TO RUN

Run using `./run.sh` command.
This will download from dockerhub the images and run everything using docker compose.

## HOW TO RUN LOCALLY

Run the script `./run-local-environment.sh`. This will download only the "helper" images.
Then run the app.
