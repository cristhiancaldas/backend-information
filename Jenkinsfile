pipeline{
    agent any

    tools {
        jdk 'jdk17'
        maven 'maven3'
    }

    environment {
            SCANNER_HOME=tool 'sonar-scanner'
            docker_repo = "crist"
            imageName = 'jenkins-terraform'
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
                            sh ''' $SCANNER_HOME/bin/sonar-scanner -Dsonar.projectName=TerraformProject \
                            -Dsonar.java.binaries=. \
                            -Dsonar.projectKey=TerraformProject '''
                        }
                    }
        }

        stage ('Build war file'){
                    steps{
                        sh 'mvn clean install -DskipTests=true'
                    }
        }

        stage("OWASP Dependency Check"){
                    steps{
                        dependencyCheck additionalArguments: '--scan ./ --format XML ', odcInstallation: 'DP-Check'
                        dependencyCheckPublisher pattern: '**/dependency-check-report.xml'
                    }
        }

        stage ('Build and push to docker hub'){
                    steps{
                        script{
                            withDockerRegistry(credentialsId: 'docker', toolName: 'docker') {
                                sh "docker build -t ${ImageName} ."
                                sh "docker tag ${ImageName} ${docker_repo}/${ImageName}:latest"
                                sh "docker push ${docker_repo}/${ImageName}:latest"
                           }
                        }
                    }
        }

        stage("TRIVY"){
                    steps{
                        sh "trivy image ${docker_repo}/${ImageName}:latest > trivy.txt"
                    }
        }

        stage ('Deploy to container'){
                    steps{
                        sh 'docker run -d --name pet1 -p 8090:8080 ${docker_repo}/${ImageName}:latest'
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