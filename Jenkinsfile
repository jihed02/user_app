pipeline {
    agent any

    environment {
        IMAGE_NAME = 'application-app'
        IMAGE_TAG = "${env.BUILD_ID}"
        DOCKER_USER = 'jihedbenamara10'  // Hardcoded Docker username
        DOCKER_PASSWORD = 'dckr_pat_184CyJLBLdYYr_vdKrcve31TZqw'  // Hardcoded Docker password (token)
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
                    sh "docker build -t jihedbenamara10/${IMAGE_NAME}:${IMAGE_TAG} ."
                }
            }
        }

        stage('Publish to Docker Hub') {
            steps {
                script {
                    // Hardcoded Docker login and push
                    sh """
                        echo \$DOCKER_PASSWORD | docker login -u \$DOCKER_USER --password-stdin
                        docker push jihedbenamara10/${IMAGE_NAME}:${IMAGE_TAG}
                    """
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
