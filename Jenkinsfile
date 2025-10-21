pipeline {
    agent any

    environment {
        IMAGE_NAME = 'application-app'
        IMAGE_TAG = "${env.BUILD_ID}"
    }

    tools {
        maven 'maven-3.9.9'
    }

    stages {
        stage('Clone Git Repository') {
            steps {
                git branch: 'main', url: 'https://github.com/jihed02/user_app.git'
            }
        }

        stage('Build with Maven') {
            steps {
                script {
                    withMaven(maven: 'maven-3.9.9') {
                        sh 'mvn clean install'
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh "docker build -t jihedbenamara02/${IMAGE_NAME}:${IMAGE_TAG} ."
                }
            }
        }

        stage('Publish to Docker Hub') {
            steps {
                script {
                    withCredentials([usernamePassword(
                        credentialsId: 'jihed02-dockerhub',
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASSWORD'
                    )]) {
                        sh """
                            echo \$DOCKER_PASSWORD | docker login -u \$DOCKER_USER --password-stdin
                            docker push jihedbenamara10/${IMAGE_NAME}:${IMAGE_TAG}
                        """
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}