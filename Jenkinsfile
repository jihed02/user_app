pipeline {
    agent any

    environment {
        IMAGE_NAME = 'application-app'
        IMAGE_TAG = "${env.BUILD_ID}"
        DOCKER_REGISTRY = 'jihedbenamara10'
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
                    sh "docker build -t ${DOCKER_REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG} ."
                }
            }
        }

        stage('Publish to Docker Hub') {
            steps {
                script {
                    withCredentials([usernamePassword(
                        credentialsId: 'jihed02-dockerhub',
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASS'
                    )]) {
                        sh """
                            echo \$DOCKER_PASS | docker login -u \$DOCKER_USER --password-stdin
                            docker push ${DOCKER_REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG}
                        """
                    }
                }
            }
        }

        stage('Déployer sur Kubernetes') {
            steps {
                script {
                    sh 'kubectl apply -f deployment.yaml'
                    sh 'kubectl apply -f service.yaml'
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
