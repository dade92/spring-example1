pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn verify'
            }
        }
        stage('Build docker image') {
            steps {
                sh 'docker build --build-arg project=spring-example1-1.0-SNAPSHOT -t spring-example1 .'
                sh 'echo y | docker image prune'
            }
        }
        stage('Publish docker image') {
            steps {
                sh 'docker image tag spring-example1 davide92/spring-example1:latest'
                sh 'docker push davide92/spring-example1:latest'
            }
        }
    }
}