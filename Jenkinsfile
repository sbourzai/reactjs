pipeline {
  agent any
  stages {
    stage('test') {
      steps {
        echo 'hello'
      }
    }

    stage('command') {
      steps {
        bat(script: 'python --version', returnStatus: true, returnStdout: true)
        bat 'pip install robotframework'
      }
    }

  }
}