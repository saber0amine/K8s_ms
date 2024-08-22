pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/saber0amine/K8s_ms.git', branch: 'master'
            }
        }

        stage('Build Docker Images') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'docker-hub', usernameVariable: 'REGISTRY', passwordVariable: 'REGISTRY_CREDENTIAL')]) {
                        docker.build("${REGISTRY}/customer-service", "customer-service/")
                        docker.build("${REGISTRY}/inventory-service", "inventory-service/")
                        docker.build("${REGISTRY}/order-service", "order-service/")
                        docker.build("${REGISTRY}/config-service", "config-service/")
                        docker.build("${REGISTRY}/geteway-service", "geteway-service/") // Fixed typo
                        docker.build("${REGISTRY}/consul-vault-service", "service-vault-consulCoonfig/")
                    }
                }
            }
        }

        stage('Push Docker Images') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'docker-hub', usernameVariable: 'REGISTRY', passwordVariable: 'REGISTRY_CREDENTIAL')]) {
                        sh "docker login -u ${REGISTRY} -p ${REGISTRY_CREDENTIAL}"
                        docker.image("${REGISTRY}/customer-service").push('0.0.1')
                        docker.image("${REGISTRY}/inventory-service").push('0.0.1')
                        docker.image("${REGISTRY}/order-service").push('0.0.1')
                        docker.image("${REGISTRY}/config-service").push('0.0.1')
                        docker.image("${REGISTRY}/geteway-service").push('0.0.1')
                        docker.image("${REGISTRY}/consul-vault-service").push('0.0.1')
                    }
                }
            }
        }

        stage('Deploy to Minikube') {
            steps {
                script {
                    withEnv(["KUBECONFIG=/root/.kube/config"]) {
                        sh 'kubectl config use-context minikube'
                        sh 'kubectl apply -f Springboot-k8s-main/'
                        sh '''
                            kubectl rollout status deployment/cloud-gateway-app
                            kubectl rollout status deployment/cloud-config-service-app
                            kubectl rollout status deployment/customer-service-app
                            kubectl rollout status deployment/order-service-app
                            kubectl rollout status statefulset/consul
                        '''
                    }
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