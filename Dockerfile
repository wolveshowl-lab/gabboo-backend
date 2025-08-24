# Stage 1: Build
FROM gradle:8-jdk21 AS builder

WORKDIR /app

# Git 설치
RUN apt-get update && apt-get install -y git

# 환경변수로 브랜치 지정 (dev, main 등)
ARG GIT_BRANCH=develop
ARG GIT_REPO=https://github.com/wolveshowl-lab/gabboo-backend.git

# 지정 브랜치 코드 clone
RUN git clone --branch $GIT_BRANCH $GIT_REPO .

# 빌드
RUN gradle build --no-daemon -x test

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

# 환경변수로 DB 정보 전달
ENV SPRING_DATASOURCE_URL=${SPRING_DATASOURCE_URL}
ENV SPRING_DATASOURCE_USERNAME=${SPRING_DATASOURCE_USERNAME}
ENV SPRING_DATASOURCE_PASSWORD=${SPRING_DATASOURCE_PASSWORD}
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}

ENTRYPOINT ["java","-jar","app.jar"]
