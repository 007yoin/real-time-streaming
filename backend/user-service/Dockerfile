FROM gradle:8.5-jdk21 AS builder
WORKDIR /app
COPY build.gradle settings.gradle gradle.properties* ./
COPY gradle ./gradle
RUN gradle dependencies || true
COPY . .
RUN gradle clean bootJar --no-daemon

FROM openjdk:21-slim
WORKDIR /app
RUN apt-get update && apt-get install -y curl
COPY --from=builder /app/build/libs/*.jar ./app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
