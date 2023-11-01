pipeline{
    agent any
    tools {
        jdk 'jdk17'
        maven 'maven3'
    }
    environment {
            SCANNER_HOME=tool 'sonar-scanner'
    }

    stages{
        stage ('clean Workspace'){
            steps{
                cleanWs()
            }
        }
        stage ('checkout scm') {
            steps {
            checkoutCall(
                                branch: "main",
                                url: "https://github.com/cristhiancaldas/backend-information.git"
                            )
            }
        }
        stage ('maven compile') {
            steps {
                sh 'mvn clean compile'
            }
        }
        stage ('maven Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage("Sonarqube Analysis "){
                    steps{
                        withSonarQubeEnv('sonar-server') {
                            sh ''' $SCANNER_HOME/bin/sonar-scanner -Dsonar.projectName=TerraformJenkins \
                            -Dsonar.java.binaries=. \
                            -Dsonar.projectKey=Terraform '''
                        }
                    }
                }

        stage("quality gate"){
                    steps {
                        script {
                          waitForQualityGate abortPipeline: false, credentialsId: 'Sonar-token'
                        }
                   }
                }
   }
}

def checkoutCall(Map stageParams) {
    checkout([
        $class: 'GitSCM',
        branches: [[name:  stageParams.branch ]],
        userRemoteConfigs: [[ url: stageParams.url ]]
    ])
}