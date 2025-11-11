pipeline {
    agent any
    environment {
        DOCKER_IMAGE = 'jihedbenamara10/application-app'
    }
    stages {
        stage('Cloner le dépôt') {
            steps {
                git branch: 'main', url: 'https://github.com/jihed02/user_app.git'
            }
        }

        stage('Build with Maven') {
            steps {
                script {
                    sh 'mvn clean package'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh "docker build -t ${DOCKER_IMAGE} ."
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    withCredentials([usernamePassword(
                        credentialsId: 'dockerhub-creds',
                        usernameVariable: 'DOCKER_USERNAME',
                        passwordVariable: 'DOCKER_PASSWORD'
                    )]) {
                        sh "echo \$DOCKER_PASSWORD | docker login -u \$DOCKER_USERNAME --password-stdin"
                        sh "docker push ${DOCKER_IMAGE}"
                    }
                }
            }
        }

        stage('Deploy on Kubernetes') {
            steps {
                script {
                    withKubeConfig([
                        credentialsId: 'kubernetes',
                        caCertificate: 'MIIDBjCCAe6gAwIBAgIBATANBgkqhkiG9w0BAQsFADAVMRMwEQYDVQQDEwptaW5pa3ViZUNBMB4XDTI1MTExMDEyMDYyMVoXDTM1MTEwOTEyMDYyMVowFTETMBEGA1UEAxMKbWluaWt1YmVDQTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBANkkv/nj/QhhJPEIgrcoETBxGI+9d7SRm/ygrVuvr6d5eJdoO7ozNK9qqELFY6QvAzZ+Pe04grnI8X1OHDWWYM3ZHoO1eGsNgiTkBQ+hMF6RdfxJ7C5ZqQxgdzLC0PdzHC4NvJusNRgcWjJRNFLYOuUEsqNFNB/FHklwauYKuN6F0EAHN5MG9B1P09Quy4vvKPodsPBYShqvffxsILmNwAWT1oSYO85qW4xSDr8WiZC7xqcSkZ8rbewZhTV9nWArjGTF6/n5q/Bje3qhkatARvXJjxCWgqSmRaDp1UuZHE/dLaGj8Rjh4yOpRXi+87861ZCkfWOCpMca5w6DNveYxFECAwEAAaNhMF8wDgYDVR0PAQH/BAQDAgKkMB0GA1UdJQQWMBQGCCsGAQUFBwMCBggrBgEFBQcDATAPBgNVHRMBAf8EBTADAQH/MB0GA1UdDgQWBBSdllwy/qr5S1eP2kd/av3S77p36DANBgkqhkiG9w0BAQsFAAOCAQEA0piIChlOSjM3kXRKvWAXCJenCfIZIsKTQSyT2vNvInH+RAwysr6C2ERamYWPBCdIJxauJNQoiYvaK8k+TwkgY/GlGIRjh16borIIRFu1+NnYCskT9tO7THzjb22QMTiuMUmEKijpiYBD2geXvdvzJuHFzbHZzzxoiBfHE/7nux/pBWtS9scqVKfushA/fXMvZrm7UjI/S/ixUkMOIjpje0zVq4RCb8BL6TrahC1XVxgJlNqrHgu+yWdEdheYLgoFLQuhqeRBZVeqrUkj/ReaSLtody+QOtU8MFPkcLSDVv7OCKm6UmzJIlVdyZWEdi8nIFwSZ+uHAf/Kbo1JQFwE3g==',
                        serverUrl: 'https://127.0.0.1:62748'
                    ]) {
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