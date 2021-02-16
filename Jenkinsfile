pipeline {
    agent {
        dockerfile true
        build 'docker'
    }
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
    }
}