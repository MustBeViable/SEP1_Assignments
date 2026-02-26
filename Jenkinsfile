pipeline {
  agent any

  options {
    // Jenkins tekee jo "Declarative: Checkout SCM" automaattisesti
    skipDefaultCheckout(false)
  }

  environment {
    // Apple Silicon Homebrew + mahdollinen /usr/local (docker symlink / rosetta) mukaan
    PATH = "/opt/homebrew/bin:/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin"

    // Jos käytät Homebrew Javaa (sun mvn -version näytti tämän), tämä tekee buildistä varmemman Jenkinsissä
    JAVA_HOME = "/opt/homebrew/opt/openjdk@21/libexec/openjdk.jdk/Contents/Home"

    // Docker Hub (vaihda nämä omiin)
    DOCKERHUB_CREDENTIALS_ID = 'Docker_Hub'     // Jenkins Credentials ID (Username/Password)
    DOCKERHUB_REPO           = 'YOUR_DH_USER/YOUR_REPO'
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
        // JaCoCo raportti (olettaa että pom.xml:ssä on jacoco plugin)
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
        // Tämä toimii vain jos Jenkinsissä on JaCoCo plugin + pipeline step käytössä.
        // Jos tämä kaatuu "No such DSL method 'jacoco'", poista tämä stage tai käytä HTML publisheria.
        jacoco()
      }
    }

    stage('Build Docker Image') {
      steps {
        // Varmista että Docker Desktop on käynnissä Macissa
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
      // Hyödyllinen kun katsoo buildin jälkeen, mitä syntyi
      archiveArtifacts artifacts: '**/target/*.jar, **/target/site/jacoco/**', allowEmptyArchive: true
    }
  }
}