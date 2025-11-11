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
                    kubeconfig(caCertificate: '''MIIDBjCCAe6gAwIBAgIBATANBgkqhkiG9w0BAQsFADAVMRMwEQYDVQQDEwptaW5p
                    a3ViZUNBMB4XDTI0MTIyNzE0MTkzNloXDTM0MTIyNjE0MTkzNlowFTETMBEGA1UE
                    AxMKbWluaWt1YmVDQTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBALa7
                    49roGJoIrK/j3og8zRmhBdaxZwCJIcleMI6aQqahkmTFMAHI1RJKEg+QzQyzSxAH
                    37oAisO6S3g8H2XH/hsilF1UyvD57NYhafz7v8UOLIn4t8UmTPLtX0S8txyO/yu8
                    LAiSqN9lKs4ZraLp5EUIA7ojUjQOhcpYD2xq278Ka+23/GUDYtrpXMVBqrEtARG+
                    0vcGS6eRG5JFP+yZJ/x/0EJgjhdQ7/Drm5pus3d6xy+Fj+5LpMpMORDQTqAoquUg
                    Vpa/v9pBjiU2mgpGhUuEilcD8H/XSm5jcDnGeM8DAFP6gz7zOc7VeYvbfHdXEJ+0
                    hpRIjDX9Mw/DowSLaCUCAwEAAaNhMF8wDgYDVR0PAQH/BAQDAgKkMB0GA1UdJQQW
                    MBQGCCsGAQUFBwMCBggrBgEFBQcDATAPBgNVHRMBAf8EBTADAQH/MB0GA1UdDgQW
                    BBQ6TXMqzsYycTDFBh1EtuQvLwsB+DANBgkqhkiG9w0BAQsFAAOCAQEAnwqQPlJB
                    1TntwjIWv0D+OK8C8ck5fkGPx+uFVImydTxwC9qhLrYUS8ST2zZGcMl8Fhfcfc+J
                    XKPfOBa7ZJWIgy7wBdW3Dht4KNnN6y5iQYzOIQvVwP5m/1mKUKFNRBMBI/P6dvca
                    giwnWIP+99/TBh2+qW66NoMxiUietAuZCPDfygJMCD8CX9NfuzLiB8JkE2NoDOP5
                    LN1OdAMJpehUvNHdLSdNaQYiZ315ZTwZASolby9q9aQHr5dnMxGv+VQrL386i3OS
                    GudMpbmqU5878VhUG/902qtnYBnS1K3KBb1ZowuhpGCoEVwPd5VudqM81NX8djh7
                    RpO2XO1vxqujIQ==''', credentialsId: 'kubeconfig', serverUrl: 'https://127.0.0.1:54077') {
                       sh 'kubectl apply -f deployment.yaml'
                       sh 'kubectl apply -f service.yaml'
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
