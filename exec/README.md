# A204 MIMO Porting Manual
# 1. Tools
- ## Backend
    - ### Version
        ```sh
        Gradle="8.7"
        ```
    - ### IDE
        ```sh
        IntelliJ="Ultimate 2024.1.1"
        IntelliJ="Community 2023.3.4"
        VisualStudioCode="1.89.1"
        ```
- ## Infra
    - ### Docker
        ```sh
        Docker="26.0.2"
        DockerCompose="2.26.1"
        ```
    - ### Container
        ```sh
        SpringBoot="amazoncorretto:17.0.9-al2023-headless"
        Database="mysql:8.3.0"
        Nginx="nginx:1.26.0"
        JenkinsMaster="jenkins/jenkins:2.440.3-lts-jdk17"
        JenkinsAgent="debian:stable (Debian GNU/Linux 12 (bookworm))"
        ```
- ## Frontend(web)
    - ### Version
        ```sh
        html="5"
        css="4.15"
        ```
    - ### IDE
        ```sh
        VisualStudioCode="1.89.1"
        ```
- ## Frontend(android)
    - ### Version
        ```sh
        agp="8.3.1"
        kotlin="1.9.0"
        coreKtx="1.10.1"
        junit="4.13.2"
        junitVersion="1.1.5"
        espressoCore="3.5.1"
        lifecycleRuntimeKtx="2.7.0"
        activityCompose="1.9.0"
        composeBom="2023.08.00"
        googlePlayServicesLocation="21.1.0"
        ```
    - ### IDE
        ```sh
        AndroidStudio="2023.2.1"
        ```
- ## Embedded
    - ### Version
        ```sh
        g++="10.2.1"
        bluez="5.7.5"
        nlohmann/sh="3.11.3"
        ArduinoIDE="2.2.1"
        ArduinoJson="7.0.4"
        ```
    - ### IDE
        ```sh
        vim="8.2"
        ```
# 2. Environment Variables
- ## Backend
    - ### dev
        ```sh
        MYSQL_URL="jdbc:mysql://k10a204.p.ssafy.io:3306/mimo"
        MYSQL_USERNAME="mimo_dev"
        MYSQL_PASSWORD="alfkzmfahsld"
        JWT_SECRET_KEY="d0c88c94ef7dba29e556af42fa970eeafd41e410636b4da233f2de14087ef9d7d632ccee250171614b8f7351f70b581b803d667903b0c279c2d0c41cd2d25ac0"
        ```
    - ### ops
        ```sh
        MYSQL_URL="jdbc:mysql://k10a204.p.ssafy.io:3307/mimo"
        MYSQL_USERNAME="mimo_ops"
        MYSQL_PASSWORD="alfkzmfahsld"
        JWT_SECRET_KEY="494e2367981d6f31a450030b2da3d2e2203444c92cb8921048900947511c91bcd91b831c24a0611748b1b58dc6a2e7c6b66e60f2f8b4790337c6fcfde4b2fa7c"
        ```
- ## Infra
    - ### Jenkins
        ```sh
        # for jenkins
        # master
        JENKINS_MASTER_OPTS="--prefix=/jenkins"
        # agent
        JENKINS_AGENT_SECRET="357521ceecb91ff23daa2321837aac42be45620aa84f672880a13fea586f6aa2"
        JENKINS_URL="http://jenkins_master:8080/jenkins/"

        # for spring boot
        # dev
        DEV_MYSQL_URL="jdbc:mysql://k10a204.p.ssafy.io:3306/mimo"
        DEV_MYSQL_USERNAME="mimo_dev"
        DEV_MYSQL_PASSWORD="alfkzmfahsld"
        JWT_SECRET_KEY_DEV="d0c88c94ef7dba29e556af42fa970eeafd41e410636b4da233f2de14087ef9d7d632ccee250171614b8f7351f70b581b803d667903b0c279c2d0c41cd2d25ac0"
        # ops
        OPS_MYSQL_URL="jdbc:mysql://k10a204.p.ssafy.io:3307/mimo"
        OPS_MYSQL_USERNAME="mimo_ops"
        OPS_MYSQL_PASSWORD="alfkzmfahsld"
        JWT_SECRET_KEY_OPS="494e2367981d6f31a450030b2da3d2e2203444c92cb8921048900947511c91bcd91b831c24a0611748b1b58dc6a2e7c6b66e60f2f8b4790337c6fcfde4b2fa7c"
        ```
    - ### Database(dev)
        ```sh
        DATABASE_PORT_0="3306"
        DATABASE_PORT_1="33060"
        MYSQL_ROOT_PASSWORD="tbvjalfkzmf"
        ```
    - ### Database(ops)
        ```sh
        DATABASE_PORT_0="3307"
        DATABASE_PORT_1="33070"
        MYSQL_ROOT_PASSWORD="tbvjalfkzmf"
        ```
    - ### Nginx
        ```sh
        SERVER_DOMAIN="43.203.239.150"
        GERRIT_PORT="8989"
        JENKINS_PORT="8080"

        API_PORT="8080"

        SOCKET_PORT="65432"
        DEV_SOCKET_PORT="65431"
        OPS_SOCKET_PORT="65432"

        SSL_CERT_PATH="/etc/letsencrypt/live/p.ssafy.io/fullchain.pem"
        SSL_KEY_PATH="/etc/letsencrypt/live/p.ssafy.io/privkey.pem"

        HTML_DIR="/var/www/html/mimo"
        ```
# 3. External Services
- ### Kakao API for social login
# 4. Build and Deploy
- ## Backend
    - ### Spring boot 프로젝트를 jenkins를 통해 자동배포
        - S10P31A204/backend/Dockerfile
            ```sh
            FROM amazoncorretto:17.0.9-al2023-headless
            ENV TZ Asia/Seoul
            COPY .build/libs/mimo.jar /mimo.jar
            ENTRYPOINT ["java", "-jar", "/mimo.jar"]
            ```

        -  S10P31A204/backend/Jenkinsfile
            ```sh
            # Jenkins dashboard에서 Docker Hub 로그인을 위한 credential 추가 필요 (안정화 버전 롤백을 위해)
            chmod +x ./gradlew
            ./gradlew clean bootJar
            # TARGET = dev or green or blue
            docker compose down $TARGET && docker compose up $TARGET --build -d
            ```
    - ### Docker Compose 설정으로 브랜치에 따라 환경변수 다르게 적용
- ## Infra
    1. ### Docker 설치 후 필요한 docker network, docker volume 생성
        ```sh
        # Install docker for ubuntu
        # 1. Run the following command to uninstall all conflicting packages
        for pkg in docker.io docker-doc docker-compose docker-compose-v2 podman-docker containerd runc; do sudo apt-get remove $pkg; done
        # 2. Add Docker's official GPG key
        sudo apt-get update
        sudo apt-get install ca-certificates curl
        sudo install -m 0755 -d /etc/apt/keyrings
        sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
        sudo chmod a+r /etc/apt/keyrings/docker.asc
        # 3. Add the repository to Apt sources:
        echo \
        "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu \
        $(. /etc/os-release && echo "$VERSION_CODENAME") stable" | \
        sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
        sudo apt-get update
        # 4. Install docker packages
        sudo apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
        # 5(Optional). Verify that the Docker Engine installation is successful by running the hello-world image.
        #sudo docker run hello-world

        # Create docker networks and volumes
        # for databases
        docker volume create db_dev-volume
        docker volume create db_ops-volume
        # for jenkins nodes
        docker volume create jenkins_master-volume
        docker volume create jenkins_agent-volume
        docker network create jenkins-network
        # for total service network
        docker network create mimo-network
        ```
    2. ### 컨테이너 실행
        ```sh
        # 각 컨테이너를 위한 환경변수(상기 표기)를 각 디렉토리의 .env 파일로 저장 후 실행 가능
        # Jenkins       at S10P31A204/deploy/jenkins
        # MySQL(dev)    at S10P31A204/deploy/db_dev
        # MySQL(ops)    at S10P31A204/deploy/db_ops
        # Nginx         at S10P31A204/deploy/nginx

        docker compose up --build -d
        # 첫 실행이 아니라면
        #docker compose down && docker compose up --build -d

        # Nginx Blue-Green switching (기본적으로 Blue 환경으로 라우팅)
        # 쉘 스크립트 실행권한 얻기
        chmod +x ./switch.sh
        # Green -> Blue
        ./switch.sh blue
        # Blue -> Green
        ./switch.sh green
        # Restart nginx
        ./switch.sh restart
        ```
- ## Frontend
    - ### apk 파일 서버 호스트에 저장 후 웹 페이지를 통해 배포
        - App: Andriod Studio에서 apk 파일 빌드
        - Web: Nginx를 통해 다운로드 링크를 포함한 html 파일 서빙
- ## Embedded
    - ### 쉘 스크립트 실행
# 5. Presentation scenario
### 초기 설정
- 계정 생성 후 해당 계정으로 미리 집과 허브, 기기를 등록
- 앱을 통해 허브 QR로 사용자의 집에 허브를 등록
### 기기 직접 제어
- 앱을 통해 기기 제어
### 입면 환경 제어
- API로 입면 신호 보내기(수면 서비스라 실시간 시연 불가)
- 입면 신호에 따라 자동으로 조명 OFF, 창문과 커튼 완전히 닫아줌
### 기상 환경 제어
- 설정한 시간에 따라 조명, 알람 서서히 켜짐 (설정한 알람 시간에 따라 자동으로 수행됨)
### 스마트폰 무드등
- 사용자의 스마트폰을 잠금해제
- 잠금해제 신호에 의해 자동으로 무드등 ON