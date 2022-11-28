**SPRING EXAMPLE**

Sample maven project for spring boot applications using java and kotlin languages.
It follows hexagonal architecture principles (https://netflixtechblog.com/ready-for-changes-with-hexagonal-architecture-b315ec967749)
and uses different testing techniques, especially for the data layer classes.
It uses some basic concepts of functional programming (like monads) using arrow kotlin library.
It contains also a Jenkinsfile to allow continuous integration with jenkins, and 
a Dockerfile that can be used after the build stage to build a docker image.

**HOW TO BUILD**

Download docker: https://docs.docker.com/get-docker/.
Build inside a docker container (basically you don't even need Maven installed locally) 
by using the .sh script typing: `./build.sh`.

**HOW TO RUN**

Run using `docker compose up` command. 
This will download from dockerhub the images and run everything.

**HOW TO RUN LOCALLY**

The app expects to find a database locally with all the tables. So, first download this: https://github.com/dade92/mysql-custom.
Run it and then launch the app locally.
