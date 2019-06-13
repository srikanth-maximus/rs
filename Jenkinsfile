def GIT_REPO_NO_PROTO = "github.com/maximusfederal/maximus-sampleapp.git"
def JENKINS_HOSTNAME = "ec2-54-156-12-228.compute-1.amazonaws.com"
def NEXUS_HOSTNAME = "ec2-18-214-224-44.compute-1.amazonaws.com"
def OCP_HOSTNAME = "ec2-18-234-182-171.compute-1.amazonaws.com"
def SELENIUM_HOSTNAME = "ec2-3-215-56-53.compute-1.amazonaws.com"

def REPO_URL = "https://" + GIT_REPO_NO_PROTO
def SONAR_URL = "http://${JENKINS_HOSTNAME}:9000/"
def SELENIUM_URL = "http://${SELENIUM_HOSTNAME}:4444/wd/hub"

def isCiPr = false
def isMasterPr = false
def isCiPush = GIT_BRANCH == "ci"
def isMasterPush = GIT_BRANCH == "master"

def DOCKER_IMAGE = "maximus-demo"
def DOCKER_TAG

def ECS_DEV_URL = "http://ECS-INFRA-DEV-LoadBalancer-106166404.us-east-1.elb.amazonaws.com/maximus-demo/hello"

def ECS_DEV_CLUSTER_NAME = "ECS-INFRA-DEV-Cluster"
def ECS_DEV_SERVICE_NAME = "maximus-sampleapp-DEV"
def ECS_DEV_TASK_DEFINITION_NAME = "maximus-sampleapp-DEV"

def ECS_TEST_CLUSTER_NAME = "ECS-INFRA-TEST-Cluster"
def ECS_TEST_SERVICE_NAME = "maximus-sampleapp-TEST"
def ECS_TEST_TASK_DEFINITION_NAME = "maximus-sampleapp-TEST"

def ECS_PROD_CLUSTER_NAME = "ECS-INFRA-PROD-Cluster"
def ECS_PROD_SERVICE_NAME = "maximus-sampleapp-PROD"
def ECS_PROD_TASK_DEFINITION_NAME = "maximus-sampleapp-PROD"

node {
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
            docker pull openjdk:8-jre-alpine
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
}