# -------- Build Stage --------
FROM gradle:8-jdk21 AS builder
WORKDIR /app

# 소스 복사
COPY --chown=gradle:gradle . .

# Gradle 빌드 (테스트 제외)
RUN gradle build --no-daemon -x test

# -------- Runtime Stage --------
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Build Stage에서 만든 jar 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# 컨테이너 시작 시 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
