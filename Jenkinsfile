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
        stage('build image') {
            steps {
                sh 'docker build -t spring-example1 .'
            }
        }
    }
}