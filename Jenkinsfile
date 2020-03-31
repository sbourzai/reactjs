pipeline {
  agent any
  stages {
    stage('test') {
      steps {
        echo 'hello'
      }
    }

    stage('install script') {
      steps {
        sh '''cd
./scriptBash.sh'''
      }
    }

  }
}