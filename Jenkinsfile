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
        sh '''yum install python-pip
git clone https://github.com/boto/boto3.git
cd boto3
pip install --user'''
      }
    }

  }
}