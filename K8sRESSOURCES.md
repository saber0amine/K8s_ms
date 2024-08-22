# <center> *Microservices with k8s* </center>
###  **Project Structure**
- **File and Folder Layout**:
```
MCS-Architecture-Consul-Config-Gateway-Feign-Resilience4J/
├── Springboot-k8s-main/           # Kubernetes YAMLs
├── config-repo/                   # Configuration files
├── config-service/                # Spring Cloud Config service
├── customer-service/              # Customer service microservice
├── geteway-service/               # Gateway service microservice
├── inventory-service/             # Inventory service microservice
├── order-service/                 # Order service microservice
├── service-vault-consulCoonfig/   # Consul and Vault service configuration
└── docker-compose.yml             # Docker Compose configuration file
```
- **Service Description**:
    - **Config Service**: Provides centralized configuration for all microservices.
    - **Customer Service**: Manages customer-related operations.
    - **Inventory Service**: Handles inventory management.
    - **Order Service**: Manages customer orders , and connect to customer and inventory services via RESTAPI .
    - **Gateway Service**: Acts as an API Gateway for routing and load balancing.
    - **Consul and Vault Service**: Service discovery and secrets management.
### 3. **Docker and Docker Compose**
- **Dockerfile Explanation**: A general explanation of the Dockerfile used across all microservices.
    - **Base Image**: `openjdk:17-jdk-slim`
    - **Build Process**:FROM openjdk:17-jdk-slim AS builder
      WORKDIR /app
      COPY . .
      RUN  ./mvnw clean package -DskipTests
    - **Final Image**:FROM openjdk:17-jdk-slim
      COPY --from=builder /app/target/*.jar /app
      ENTRYPOINT ["java", "-jar" , "app.jar"]
- **Docker Compose Configuration**:
    - **Services Overview**:
        - `customer-service` , `inventory-service` , `order-service` , `geteway-service` , `consul-vault-service` , `config-service` , `consul` , `vault`
    - **Dependencies**: Specify how services are dependent on each other using `depends_on` .
### 4. **Kubernetes Deployment with Minikube**
- **Kubernetes Archetecture **:
  [﻿Minikube Clusster and more](https://app.eraser.io/workspace/QOmKbPHs9T2d0PA3gCtT?elements=raKVAA46GH7qtnE9IC8PgQ)

- **Deployment Configuration**:
```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: cloud-gateway-app
spec:
  replicas: 1
  selector:
    matchLabels:
      app: cloud-gateway-app
  template:
    metadata:
      labels:
        app: cloud-gateway-app
    spec:
      containers:
        - name: cloud-gateway-app
          image: aminesbaer/gateway-service:0.0.1
          ports:
            - containerPort: 9999
```
- **Service Configuration**:
- WHY StatefullSet and headless for the consul service Discovery .
- To make the consul pod having the hostname as a pod name and make the service know the server_url ( instead of leting the pods generate the hash in the name )
- so we will use a configMap in all services and giving them the address of the consul pod with this format
  ![image.png](https://eraser.imgix.net/workspaces/QOmKbPHs9T2d0PA3gCtT/lsy93eaVnpc4wPsnz4ouw66DVO92/YTKAO7ova2Fka2n17JSU2.png?ixlib=js-3.7.0 "image.png")



```
apiVersion: v1
kind: Service
metadata:
  name: cloud-gateway-svc
spec:
  type: LoadBalancer
  ports:
    - port: 80
      targetPort: 9999
      protocol: TCP
  selector:
    app: cloud-gateway-app
```
- **Minikube Configuration**:
    - **Setting Up Minikube**: Step-by-step instructions to set up Minikube on your VPS.
    - **Deploying Applications**:kubectl apply -f Springboot-k8s-main/
    - **Service Access**: Explanation on how to access services using Minikube's IP or via NodePort.
### 5. **CI/CD Pipeline with Jenkins and Kubernetes**
- **Jenkins Setup**:
    - **Containerized Jenkins**: Running Jenkins as a container on your VPS.
    - **Plugins and Configuration**:
        - Kubernetes Plugin: Connect Jenkins with Minikube.
        - Docker Plugin: Build and push Docker images.
- **Pipeline Configuration**:
    - **Jenkinsfile**: Example Jenkinsfile to build, test, and deploy services.
    - **Kubernetes Integration**:
```
pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh 'docker-compose -f docker-compose.yml build'
      }
    }
    stage('Deploy to K8s') {
      steps {
        sh 'kubectl apply -f Springboot-k8s-main/'
      }
    }
  }
}
```
### 6. **Monitoring and Logging**
- **Integrating Grafana and Prometheus**: Steps to integrate monitoring tools.
- **Log Aggregation with Loki**: How to aggregate logs using Loki.
### 7. **Security Considerations**
- **Using Vault for Secrets Management**: Explain how Vault is integrated for storing and managing secrets.
- **Consul for Service Discovery**: How Consul is used for service discovery and configuration.
### 8. **Conclusion**
- **Summary**: A recap of the architecture and the CI/CD pipeline.
- **Future Improvements**: Potential enhancements and areas for future work.
### 9. **Appendices**
- **Reference Links**: Include links to documentation, repositories, and other resources.
- **Glossary**: Definitions of key terms and acronyms used in the documentation.
---

This documentation structure should provide a comprehensive and professional overview of your architecture and deployment process using Jenkins with Kubernetes on Minikube. Adjust and expand each section based on your specific needs and details.

