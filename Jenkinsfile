def newVersion
pipeline {
  agent any
    triggers {
        pollSCM('H/5 * * * *')
    }
    stages {
        // Build
        stage('Build') {
            steps {
                // Clean before build
                cleanWs()
                // We need to explicitly checkout from SCM here
                checkout scm
                // sh mvn clean install
                bat 'mvn clean compile'
            }
            post {
                // Clean after build
                always {
                    cleanWs(cleanWhenNotBuilt: false,
                            deleteDirs: true,
                            disableDeferredWipeout: true,
                            notFailBuild: true,
                            patterns: [[pattern: '.gitignore', type: 'INCLUDE'],
                                       [pattern: '.propsfile', type: 'EXCLUDE']])
                }
            }
        }
        stage('Info') {
          steps {
            script{
                  def pom = readMavenPom file: 'pom.xml'
                  newVersion=pom.version
                  printf("Pom Version: %s", pom.version)
            }
          }
        }
        // Build
        stage('Test') {
            steps {
                bat 'mvn verify'
            }
            post{
              always{
                junit "**/target/surefire-reports/TEST-*.xml"
              }
            }
        }
        // cucumber
       stage('cucumber') {
         steps {
                cucumber buildStatus: "UNSTABLE",
               fileIncludePattern: "**/feature.*.*.json",
                jsonReportDirectory: "target"
          }
       }
        // Package
        stage('Package') {
            steps {
                bat 'mvn package -Dmaven.test.skip=true'
            }
            post {
                success {
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
        stage('Build Docker Image') {

            steps {
                bat 'docker build . -t localhost:5000/ms-cart:'+newVersion
                bat 'echo the image to docker'
                bat 'docker push localhost:5000/ms-cart:'+newVersion

                bat 'echo the latest image to docker'
                bat 'docker tag localhost:5000/ms-cart:'+newVersion+' localhost:5000/ms-cart:latest'
                bat 'docker push localhost:5000/ms-cart:latest'

                bat 'echo Delete the image from jenkins'
                bat 'docker rmi -f localhost:5000/ms-cart:'+newVersion+' localhost:5000/ms-cart:latest'
            }
        }
        // Deploy
        stage('Deploy') {
            steps {
                bat 'kubectl set image deployment/ms-cart ms-cart=localhost:5000/ms-cart:'+newVersion
            }
        }
        //end
    }
  }