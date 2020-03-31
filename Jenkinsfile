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
        sh '''chmod +x scriptBash.sh
./scriptBash.sh'''
      }
    }

  }
}