pipeline{
    agent any
    tools{
        maven "maven"
    }
    stages{
        stage("Build JAR File"){
            steps{
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/schnjov/MilkStgo_Tingeso.git']])
                sh "mvn clean install"
            }
        }
        stage("Test"){
            steps{
                    sh "mvn test"
            }
        }
        stage("SonarQube Analysis"){
            steps{
                    sh "mvn clean verify sonar:sonar -Dsonar.projectKey=SistemaMilkStgo -Dsonar.projectName='SistemaMilkStgo'  -Dsonar.host.url=http://localhost:9000 -Dsonar.token=sqp_7ac84ee48f5b40f77ded02471ec631c64bc2019a"
            }
        }
        stage("Build Docker Image"){
            steps{
                    sh "docker build -t jovannischneider/milkstgo-app ."
            }
        }
        stage("Push Docker Image"){
            steps{
                    withCredentials([string(credentialsId: 'Theyugi27+', variable: 'dckpass')]){
                        sh "docker login -u jovannischneider -p ${dckpass}"
                    }
                    sh "docker push jovannischneider/milkstgo-app"
            }
        }
    }
    post{
        always{
                sh "docker logout"
        }
    }
}
