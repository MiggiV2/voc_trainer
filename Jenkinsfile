node {
  git branch: "main", url: "http://gitea:3000/Miggi/voc-trainer"

  withEnv(['REGISTRY=registry.mymiggi.de', 'ROOT_IMAGE=$REGISTRY/Miggi/voc-trainer']) {
    stage ('Build the Docker image') {
      sh "echo building the image..."
      sh "ls -la"
      sh "cd auth"
      sh "./mvnw clean package -DskipTests"
      sh "cd ../api"
      sh "./mvnw clean package -DskipTests"
      sh "cd target/quarkus-app/"
      sh "docker build ../.. -t $ROOT_IMAGE/api"
      sh "cd ../../../auth/target/quarkus-app/"
      sh "docker build ../.. -t $ROOT_IMAGE/auth"
      sh "cd ../../../gui/"
      sh "docker build . -t $ROOT_IMAGE/gui"
      sh "echo building image complete."
    }
    stage ('Manage tags & untagged') {
      sh "docker tag $ROOT_IMAGE/api $ROOT_IMAGE/api:$BUILD_ID"
      sh "docker tag $ROOT_IMAGE/gui $ROOT_IMAGE/api:$BUILD_ID"
      sh "docker tag $ROOT_IMAGE/auth $ROOT_IMAGE/api:$BUILD_ID"
      sh "docker image prune -f"
    }
    /*
    stage ('Deploy the Docker image') {
      sh "echo Deploying the container..."
      sh "docker push $ROOT_IMAGE/gui"
      sh "docker push $ROOT_IMAGE/auth"
      sh "docker push $ROOT_IMAGE/api"
      sh "docker push $ROOT_IMAGE/gui:$BUILD_ID"
      sh "docker push $ROOT_IMAGE/auth:$BUILD_ID"
      sh "docker push $ROOT_IMAGE/api:$BUILD_ID"
      sh "echo Container successfully deployed."
    }
    */
  }  
}
