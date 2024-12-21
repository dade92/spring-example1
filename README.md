# SPRING EXAMPLE

Sample maven project for spring boot applications using java and kotlin languages.
It follows hexagonal architecture
principles (https://netflixtechblog.com/ready-for-changes-with-hexagonal-architecture-b315ec967749)
and uses different testing techniques, especially for the data layer classes.
It uses some basic concepts of functional programming (like monads) using arrow kotlin library.
It's also integrated with Github CI/CD (Github actions) and contains
a Dockerfile that can be used after the build stage to build a docker image.

As a data layer, it uses a relational database (Mysql), plus some rest repositories
to connect to an external service

## HOW TO BUILD

There are two options:

- Build inside a docker container (basically you don't even need Maven installed locally)
  by using the .sh script typing: `./build.sh`. You will find the built file in the
  usual maven directories
- Build locally running `mvn clean package`

### CI/CD

Linked to this project there is a CI/CD integration (Github actions) that is triggered
on every master push and a different one on every push to other branches.
The master pipeline will push automatically the docker image on the dockerhub registry,
ready for the deployment.

## HOW TO RUN

### Deploy everything

Run using `./run.sh` command.
This will download from dockerhub the images and run everything using docker compose.

### Run locally

Run the script `./run-local-environment.sh`. This will download only the "helper" images.
Then run the app.

In both scenario, you have to inject environment variables in your application: if you run it locally, you can use
your favourite IDE configuration. Instead in case of an entire deploy, you can specify them directly in
the `./deploy/docker-compose.yaml`
root file (just for simplicity, a better way should be to pick them from the env variables of your OS).

In local, you can connect to the local mysql using the following credentials:

```http request
username: root
password: password
```