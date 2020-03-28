pipeline {
  agent {
    node {
      label 'master'
    }

  }
  stages {
    stage('test') {
      steps {
        echo 'hello'
      }
    }

    stage('install Robot') {
      steps {
        sh 'robot test_script.robot'
      }
    }

  }
}