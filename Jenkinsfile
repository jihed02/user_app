pipeline {
    agent any

    environment {
        IMAGE_NAME = 'application-app'
        IMAGE_TAG = "${env.BUILD_ID}"
        DOCKER_USER = 'jihedbenamara10'  // Docker Hub username
        DOCKER_PASSWORD = credentials('dockerhub-token')  // Jenkins secret for Docker token
    }

    stages {
        stage('Build Docker Image') {
            steps {
                script {
                    sh "docker build -t $DOCKER_USER/$IMAGE_NAME:$IMAGE_TAG ."
                }
            }
        }

        stage('Publish to Docker Hub') {
            steps {
                script {
                    sh """
                        echo \$DOCKER_PASSWORD | docker login -u \$DOCKER_USER --password-stdin
                        docker push \$DOCKER_USER/$IMAGE_NAME:$IMAGE_TAG
                    """
                }
            }
        }
    }

    post {
        success {
            echo 'Docker image pushed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
