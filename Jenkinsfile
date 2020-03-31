pipeline {
  agent any
  stages {
    stage('test') {
      steps {
        echo 'hello'
        sh 'pwd'
      }
    }

    stage('Install Robot') {
      steps {
        sh '''rm -rf boto3
git clone https://github.com/boto/boto3.git
cd boto3
pip3 install --user'''
      }
    }

  }
}