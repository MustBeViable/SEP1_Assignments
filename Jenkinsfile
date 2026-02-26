pipeline {
  agent any

  options {
    skipDefaultCheckout(false)
  }

  environment {
    PATH = "/opt/homebrew/bin:/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin"

    JAVA_HOME = "/opt/homebrew/opt/openjdk@21/libexec/openjdk.jdk/Contents/Home"

    DOCKERHUB_CREDENTIALS_ID = 'Docker'     // Jenkins Credentials ID (Username/Password)
    DOCKERHUB_REPO           = 'https://github.com/MustBeViable/SEP1_Assignments.git'
    DOCKER_IMAGE_TAG         = 'latest'
  }

  stages {
    stage('Verify Tools') {
      steps {
        sh 'echo "PATH=$PATH"'
        sh 'which mvn && mvn -v'
        sh 'which java && java -version'
        sh 'which docker || true'
        sh 'docker --version || true'
      }
    }

    stage('Build') {
      steps {
        sh 'mvn -B clean install -DskipTests'
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

    stage('Build Docker Image') {
      steps {
        sh '''
          docker build -t "${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}" .
        '''
      }
    }

    stage('Push Docker Image to Docker Hub') {
      steps {
        withCredentials([usernamePassword(
          credentialsId: "${DOCKERHUB_CREDENTIALS_ID}",
          usernameVariable: 'DH_USER',
          passwordVariable: 'DH_PASS'
        )]) {
          sh '''
            echo "$DH_PASS" | docker login -u "$DH_USER" --password-stdin
            docker push "${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}"
            docker logout
          '''
        }
      }
    }
  }

  post {
    always {
      archiveArtifacts artifacts: '**/target/*.jar, **/target/site/jacoco/**', allowEmptyArchive: true
    }
  }
}