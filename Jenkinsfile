pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'mvn -B clean install'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn -B test'
            }
        }

        stage('Code Coverage') {
            steps {
                sh 'mvn -B jacoco:report'
            }
        }

        stage('Publish Test Results') {
            steps {
                junit '**/target/surefire-reports/*.xml'
            }
        }

        stage('Publish Coverage Report') {
            steps {
                jacoco()
            }
        }
    }
}