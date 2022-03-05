pipeline {
  agent any

  environment {
    REGISTRY = 'registry.mymiggi.de'
    ROOT_IMAGE = '${env.REGISTRY}/Miggi/voc-trainer'
  }

  stages {
    stage('Checkout') {
      steps {
        git branch: 'main', url: 'http://gitea:3000/Miggi/voc-trainer'
      }
    }
    stage('Build Auth') {
      steps {
        sh 'cd auth'
        sh './mvnw -V clean package -DskipTests'
        sh 'cd target/quarkus-app'
        sh 'docker build ../.. -t ${env.ROOT_IMAGE}/auth'
      }
    }
    stage('Build API') {
      steps {
        sh 'cd ../../../api'
        sh './mvnw -V clean package -DskipTests'
        sh 'cd target/quarkus-app'
        sh 'docker build ../.. -t ${env.ROOT_IMAGE}/api'
      }
    }
    stage('Build GUI') {
      steps {
        sh 'cd ../../../api'
        sh 'docker build . -t ${env.ROOT_IMAGE}/gui'
      }
    }
    stage ('Manage tags & untagged') {
      steps {
        sh "docker tag $ROOT_IMAGE/api $ROOT_IMAGE/api:$BUILD_ID"
        sh "docker tag $ROOT_IMAGE/gui $ROOT_IMAGE/api:$BUILD_ID"
        sh "docker tag $ROOT_IMAGE/auth $ROOT_IMAGE/api:$BUILD_ID"
        sh 'docker image prune -f'
      }      
    }
  }
}
