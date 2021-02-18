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
        stage('build docker image') {
            steps {
                sh 'docker build --build-arg project=spring-example1-1.0-SNAPSHOT -t spring-example1 .'
                sh 'echo y | docker image prune'
            }
        }
    }
}