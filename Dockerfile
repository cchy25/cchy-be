# -------------------------------------
# 1. 빌드 단계 (Build Stage)
# -------------------------------------
# Gradle 빌드를 위해 JDK 21을 사용하는 경량(alpine) 이미지를 사용합니다.
FROM amazoncorretto:21-jdk-alpine AS builder

# 작업 디렉토리를 /app으로 설정
WORKDIR /app

# Gradle 관련 파일들을 컨테이너 내부로 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# 소스 코드를 복사
COPY src src

# Gradle Wrapper를 실행 가능한 파일로 만듭니다.
RUN chmod +x ./gradlew

# `./gradlew bootJar` 명령어로 애플리케이션 빌드
# 이 명령어가 `build/libs/`에 JAR 파일을 생성합니다.
RUN ./gradlew bootJar

# -------------------------------------
# 2. 실행 단계 (Run Stage)
# -------------------------------------
# 애플리케이션 실행을 위해 JDK 21을 사용하는 경량 이미지를 사용합니다.
FROM amazoncorretto:21-jdk-alpine

# 작업 디렉토리를 /app으로 설정
WORKDIR /app

# 빌드 단계에서 생성된 JAR 파일을 실행 이미지로 복사
COPY --from=builder /app/build/libs/*.jar /app/app.jar

# 애플리케이션이 사용할 포트를 외부에 노출
EXPOSE 8080

# 애플리케이션 실행 명령어 정의
# 여기서 `--spring.profiles.active=prod`를 넣어 프로덕션 환경 설정을 활성화합니다.
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]