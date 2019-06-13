// **********  ********** ********** being inputs ********** ********** ********** //
// these need to be passed to this library function
def repo_url = "https://github.com/srikanth-maximus/rs.git"
def docker_image = "records-service"

def REPO_URL = repo_url 
def DOCKER_IMAGE = docker_image
// **********  ********** **********  end inputs  ********** ********** ********** //

def DOCKER_TAG

def isDevPush = GIT_BRANCH == "dev" 
def isCiPush = GIT_BRANCH == "test"
def isMasterPush = GIT_BRANCH == "master"

node {
  try {
    deleteDir()

    stage("Initialize") {
      GIT_BRANCH = GIT_BRANCH.replace("origin/", "")
      git(
        url: "${REPO_URL}",
        credentialsId: 'git-login',
        branch: "${GIT_BRANCH}"
      )
      currentBuild.setDisplayName("${GIT_BRANCH}-${BUILD_NUMBER}")
      DOCKER_TAG = "${BUILD_TIMESTAMP}-${GIT_BRANCH}".replace(" ", "_").replace(":","-")
    }
    
    stage("Pull Latest Base Images") {
      sh """
        docker pull azul/zulu-openjdk-alpine:11.0.3-jre
      """
    }
    
    stage("Build and Sonar Scan") {
      try {
        withCredentials([
            string(credentialsId: 'sonar-token', variable: 'SONAR_TOKEN')
        ]) {
          sh """
            chmod +x gradlew
            ./gradlew clean build sonarqube -Dsonar.host.url=${SONAR_URL} -Dsonar.login=${SONAR_TOKEN}
          """
        }
      }
      finally {
        junit '**/build/test-results/**/*.xml'
        jacoco(execPattern: '**/build/jacoco/*.exec')
      }
    }

    if (isMasterPush) {
      print "Deploy to production"
    }
    else if(isCiPush) {
      print "Deploy to test"
    }
    else if(isDevPush) {
      print "Deploy to dev"
    }
    else {
      echo "No deploy as this is a PR: ${GIT_BRANCH}"
    }
  }
  
  catch (e) {
    // notifyFailed()
    throw e
  }
}

