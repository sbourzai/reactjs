pipeline {
  agent any
  stages {
    stage('test') {
      steps {
        echo 'hello'
      }
    }

    stage('Install Robot') {
      steps {
        sh '''chmod +x install-robot.sh
./install-robot.sh'''
      }
    }

  }
}