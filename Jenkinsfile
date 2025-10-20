pipeline {
    agent any

    environment {
        DOCKER_HUB_CREDENTIALS = credentials('jihed02-dockerhub')  // ID des credentials Docker Hub dans Jenkins
        IMAGE_NAME = 'application-app'  // Le nom de l'image Docker
        IMAGE_TAG = "${env.BUILD_ID}"  // Tag basé sur l'ID de build Jenkins
    }

    stages {
        stage('Clone Git Repository') {
            steps {
                git 'https://github.com/ton-repo/ton-application.git'  // URL de ton dépôt Git
            }
        }

        stage('Build with Maven') {
            steps {
                script {
                    sh 'mvn clean install'  // Exécution de Maven pour construire l'application
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh 'docker build -t ${DOCKER_HUB_CREDENTIALS_USR}/${IMAGE_NAME}:${IMAGE_TAG} .'  // Construction de l'image Docker
                }
            }
        }

        stage('Publish to Docker Hub') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USER --password-stdin'  // Connexion à Docker Hub
                        sh 'docker push ${DOCKER_HUB_CREDENTIALS_USR}/${IMAGE_NAME}:${IMAGE_TAG}'  // Publication sur Docker Hub
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
