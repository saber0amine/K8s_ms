FROM openjdk:17-jdk-slim AS builder
WORKDIR /app
COPY . .

RUN  ./mvnw clean package -DskipTests
WORKDIR /app
FROM openjdk:17-jdk-slim
COPY --from=builder /app/target/*.jar /app
ENTRYPOINT ["java", "-jar" , "app.jar"]