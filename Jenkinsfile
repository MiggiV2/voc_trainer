node {
  git branch: 'main', url: 'http://gitea:3000/Miggi/voc-trainer'
  withEnv(['ROOT_IMAGE=registry.mymiggi.de/miggi/voc-trainer']) {
    stage('Build Auth') {
      dir("auth") {
        sh 'ls -la'
        sh './mvnw clean package -DskipTests'        
      }
      dir("auth/target/quarkus-app"){
        sh 'docker build ../.. -t $ROOT_IMAGE/auth'
      }
    }
    stage('Build API') {
      dir("api") {
        sh './mvnw clean package -DskipTests'
      }
      dir("api/target/quarkus-app") {
        sh 'docker build ../.. -t $ROOT_IMAGE/api'
      }      
    }
    stage('Build GUI') {
      dir("gui") {
        sh 'docker build . -t $ROOT_IMAGE/gui'   
      }         
    }
    stage ('Manage tags & untagged') {
      sh "docker tag $ROOT_IMAGE/api $ROOT_IMAGE/api:build-$BUILD_ID"
      sh "docker tag $ROOT_IMAGE/gui $ROOT_IMAGE/gui:build-$BUILD_ID"
      sh "docker tag $ROOT_IMAGE/auth $ROOT_IMAGE/auth:build-$BUILD_ID"
      sh 'docker image prune -f'
    }
  }
}
