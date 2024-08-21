pipeline {
    agent any

    environment {
        REGISTRY = ''
        REGISTRY_CREDENTIAL = ''
    }

    stages {
        stage('Set Credentials') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'docker-hub', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                        env.REGISTRY = USERNAME
                        env.REGISTRY_CREDENTIAL = PASSWORD
                    }
                }
            }
        }

        stage('Checkout') {
            steps {
                git url: 'https://github.com/saber0amine/K8s_ms.git', branch: 'master'
            }
        }

        stage('Build Docker Images') {
            steps {
                script {
                    docker.build("${env.REGISTRY}/customer-service", "customer-service/")
                    docker.build("${env.REGISTRY}/inventory-service", "inventory-service/")
                    docker.build("${env.REGISTRY}/order-service", "order-service/")
                    docker.build("${env.REGISTRY}/config-service", "config-service/")
                    docker.build("${env.REGISTRY}/gateway-service", "gateway-service/")
                    docker.build("${env.REGISTRY}/consul-vault-service", "service-vault-consulCoonfig/")
                }
            }
        }

        stage('Push Docker Images') {
            steps {
                script {
                    docker.withRegistry('', "${env.REGISTRY_CREDENTIAL}") {
                        docker.image("${env.REGISTRY}/customer-service").push('latest')
                        docker.image("${env.REGISTRY}/inventory-service").push('latest')
                        docker.image("${env.REGISTRY}/order-service").push('latest')
                        docker.image("${env.REGISTRY}/config-service").push('latest')
                        docker.image("${env.REGISTRY}/gateway-service").push('latest')
                        docker.image("${env.REGISTRY}/consul-vault-service").push('latest')
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    kubectl = "/usr/local/bin/kubectl"

                    sh "${kubectl} apply -f Springboot-k8s-main/customer-service.yml"
                    sh "${kubectl} apply -f Springboot-k8s-main/inventroy-service.yaml"
                    sh "${kubectl} apply -f Springboot-k8s-main/order-service.yml"
                    sh "${kubectl} apply -f Springboot-k8s-main/config-server.yml"
                    sh "${kubectl} apply -f Springboot-k8s-main/cloud-gateway.yml"
                    sh "${kubectl} apply -f Springboot-k8s-main/service-registry.yml"
                }
            }
        }
    }

    post {
        success {
            echo "Deployment Successful!"
        }
        failure {
            echo "Deployment Failed."
        }
    }
}
