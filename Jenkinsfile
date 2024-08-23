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
//                         docker.build("${REGISTRY}/customer-service:0.0.1", "customer-service/")
//                         docker.build("${REGISTRY}/inventory-service:0.0.1", "inventory-service/")
//                          docker.build("${REGISTRY}/config-service:0.0.1", "config-service/")
//                          docker.build("${REGISTRY}/geteway-service:0.0.1", "geteway-service/")
                        docker.build("${REGISTRY}/order-service:0.0.1", "order-service/")

                    }
                }
            }
        }

        stage('Push Docker Images') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'docker-hub', usernameVariable: 'REGISTRY', passwordVariable: 'REGISTRY_CREDENTIAL')]) {
                        sh "docker login -u ${REGISTRY} -p ${REGISTRY_CREDENTIAL}"
//                         docker.image("${REGISTRY}/customer-service:0.0.1").push()
//                         docker.image("${REGISTRY}/inventory-service:0.0.1").push()
//
//                         docker.image("${REGISTRY}/config-service:0.0.1").push()
//                         docker.image("${REGISTRY}/geteway-service:0.0.1").push()
                        docker.image("${REGISTRY}/order-service:0.0.1").push()
                    }
                }
            }
        }

        stage('Deploy to Minikube') {
            steps {
                script {
                    withKubeConfig([credentialsId: 'minikube_configFile', serverUrl: 'https://192.168.49.2:8443']) {
                        withCredentials([usernamePassword(credentialsId: 'docker-hub', usernameVariable: 'REGISTRY', passwordVariable: 'REGISTRY_CREDENTIAL')]) {
                            sh "docker login -u ${REGISTRY} -p ${REGISTRY_CREDENTIAL}"
                            sh '''
                            curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
                            install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl
                            kubectl delete -f Springboot-k8s-main/
                            kubectl apply -f Springboot-k8s-main/



                            '''
                        }
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