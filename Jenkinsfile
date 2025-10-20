pipeline {
    agent any

    environment {
        DOCKER_HUB_CREDENTIALS = credentials('jihed02-dockerhub')
        IMAGE_NAME = 'application-app'
        IMAGE_TAG = "${env.BUILD_ID}"
    }

    tools {
        maven 'Maven 3.9.9'  // Use the configured Maven installation
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
                    withMaven(maven: 'Maven 3.9.9') {
                        sh 'mvn clean install'
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh 'docker build -t ${DOCKER_HUB_CREDENTIALS_USR}/${IMAGE_NAME}:${IMAGE_TAG} .'
                }
            }
        }

        stage('Publish to Docker Hub') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USER --password-stdin'
                        sh 'docker push ${DOCKER_HUB_CREDENTIALS_USR}/${IMAGE_NAME}:${IMAGE_TAG}'
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
