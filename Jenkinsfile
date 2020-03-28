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
        bat(script: 'dir', returnStatus: true, returnStdout: true)
      }
    }

  }
}