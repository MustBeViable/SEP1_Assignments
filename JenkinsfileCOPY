pipeline {
  agent any

  environment {
    PATH = "/opt/homebrew/bin:/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin"
    JAVA_HOME = "/opt/homebrew/opt/openjdk@21/libexec/openjdk.jdk/Contents/Home"
  }

  stages {
    stage('Build') {
      steps {
        sh 'mvn -v'
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