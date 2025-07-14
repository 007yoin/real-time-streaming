### 1단계: 빌드 스테이지
FROM gradle:8.5-jdk21 AS builder

WORKDIR /app

# 캐시 최적화를 위해 먼저 Gradle 스크립트만 복사
COPY build.gradle settings.gradle gradle.properties* ./
COPY gradle ./gradle

# 의존성 미리 다운 (캐시 활용)
RUN gradle dependencies || true

# 전체 프로젝트 복사
COPY . .

# ✅ JAR 클린 빌드
RUN gradle clean bootJar --no-daemon

### 2단계: 실행 스테이지
FROM openjdk:21-slim

WORKDIR /app

# 빌드 결과물만 복사
COPY --from=builder /app/build/libs/app.jar ./app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
