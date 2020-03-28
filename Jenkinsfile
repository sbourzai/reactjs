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
        bat 'C:\\curl https://bootstrap.pypa.io/get-pip.py -o get-pip.py'
        bat 'python get-pip.py'
        bat 'pip install robotframework'
      }
    }

  }
}