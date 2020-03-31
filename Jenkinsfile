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
        sh '''chmod +x scriptBash.sh
./install-robot.sh'''
      }
    }

  }
}