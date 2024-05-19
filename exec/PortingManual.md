# A204 MIMO Porting Manual
# 1. Tools
### Backend
- Version
    ```sh
    Gradle="8.7"
    ```
- IDE
    ```sh
    IntelliJ="Ultimate 2024.1.1"
    IntelliJ="Community 2023.3.4"
    VisualStudioCode="1.89.1"
    ```
---
### Infra
- Docker
    ```sh
    Docker="26.0.2"
    DockerCompose="2.26.1"
    ```
- Container
    ```sh
    SpringBoot="amazoncorretto:17.0.9-al2023-headless"
    Database="mysql:8.3.0"
    Nginx="nginx:1.26.0"
    JenkinsMaster="jenkins/jenkins:2.440.3-lts-jdk17"
    JenkinsAgent="debian:stable (Debian GNU/Linux 12 (bookworm))"
    ```
---
### Frontend(web)
- Version
    ```sh
    html="5"
    css="4.15"
    ```
- IDE
    ```sh
    VisualStudioCode="1.89.1"
    ```
---
### Frontend(android)
- Version
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
- IDE
    ```sh
    AndroidStudio="2023.2.1"
    ```
---
### Embedded
- Version
    ```sh
    g++="10.2.1"
    bluez="5.7.5"
    nlohmann/sh="3.11.3"
    Arduino IDE="2.2.1"
    ArduinoJson="7.0.4"
    ```
- IDE
    ```sh
    vim="8.2"
    ```
# 2. Environment Variables
### Backend
- dev
    ```sh
    MYSQL_URL="jdbc:mysql://k10a204.p.ssafy.io:3306/mimo"
    MYSQL_USERNAME="mimo_dev"
    MYSQL_PASSWORD="alfkzmfahsld"
    JWT_SECRET_KEY="d0c88c94ef7dba29e556af42fa970eeafd41e410636b4da233f2de14087ef9d7d632ccee250171614b8f7351f70b581b803d667903b0c279c2d0c41cd2d25ac0"
    ```
- ops
    ```sh
    MYSQL_URL="jdbc:mysql://k10a204.p.ssafy.io:3307/mimo"
    MYSQL_USERNAME="mimo_ops"
    MYSQL_PASSWORD="alfkzmfahsld"
    JWT_SECRET_KEY="494e2367981d6f31a450030b2da3d2e2203444c92cb8921048900947511c91bcd91b831c24a0611748b1b58dc6a2e7c6b66e60f2f8b4790337c6fcfde4b2fa7c"
    ```
---
### Infra
- Jenkins
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
- Database
    - dev
        ```sh
        DATABASE_PORT_0="3306"
        DATABASE_PORT_1="33060"
        MYSQL_ROOT_PASSWORD="tbvjalfkzmf"
        ```
    - ops
        ```sh
        DATABASE_PORT_0="3307"
        DATABASE_PORT_1="33070"
        MYSQL_ROOT_PASSWORD="tbvjalfkzmf"
        ```
- Nginx
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
- Kakao API for social login
# 4. Build and Deploy
### Backend
- Spring boot 프로젝트를 jenkins를 통해 자동배포
    - S10P31A204/backend/Dockerfile
        ```sh
        FROM amazoncorretto:17.0.9-al2023-headless
        ENV TZ Asia/Seoul
        COPY .build/libs/mimo.jar /mimo.jar
        ENTRYPOINT ["java", "-jar", "/mimo.jar"]
        ```
    -  S10P31A204/backend/Jenkinsfile
        ```sh
        chmod +x ./gradlew
        ./gradlew clean bootJar
        # TARGET = dev or green or blue
        docker compose down $TARGET && docker compose up $TARGET --build -d
        ```
- Docker Compose 설정으로 브랜치에 따라 환경변수 다르게 적용
---
### Infra
1. Docker 설치 후 필요한 docker network, docker volume 생성
2. 컨테이너 실행
    - Jenkins
        ```sh
        # 상기한 환경변수를 .env 파일에 저장 후 S10P31A204/deploy/jenkins로 이동
        docker compose up --build -d
        # 첫 실행이 아니라면
        docker compose down && docker compose up --build -d
        ```
    - MySQL(dev)
        ```sh
        # 상기한 환경변수를 .env 파일에 저장 후 S10P31A204/deploy/db_dev로 이동
        docker compose up --build -d
        # 첫 실행이 아니라면
        docker compose down && docker compose up --build -d
        ```
    - MySQL(ops)
        ```sh
        # 상기한 환경변수를 .env 파일에 저장 후 S10P31A204/deploy/db_ops로 이동
        docker compose up --build -d
        # 첫 실행이 아니라면
        docker compose down && docker compose up --build -d
        ```
    - Nginx
        ```sh
        # 상기한 환경변수를 .env 파일에 저장 후 S10P31A204/deploy/nginx로 이동
        # 기본적으로 Blue 환경으로 라우팅
        docker compose up --build -d
        # 첫 실행이 아니라면
        docker compose down && docker compose up --build -d

        # Blue-Green
        chmod +x ./switch.sh
        # Green -> Blue
        ./switch.sh blue
        # Blue -> Green
        ./switch.sh green
        # Restart nginx
        ./switch.sh restart
        ```
---
### Frontend
- App: Andriod Studio에서 apk 파일 빌드
- Web: Nginx를 통해 html 파일 서빙
- 서버 호스트에 저장하여 배포
---
### Embedded
1. 필요한 패키지 설치 후
2. 디렉토리에서 쉘 스크립트 실행
# 5. Presentation scenario
- 초반 세팅:
    - “용상윤” 계정으로 미리 집과 허브, 기기를 등록시켜 놓음
    - “윤동휘” 계정은 처음 로그인 및 허브 등록, 조명ON, 무드등ON 까지 사용
    - 한쪽에 samsung flow, 한쪽에 webex 화면 구도로
- 로그인 및 허브 등록
    - 처음 사용자가 앱을 실행시키면 카카오로그인을 통해 서비스를 이용할 수 있습니다.
    - 로그인하면 지금 저희가 신규유저이기 때문에 허브를 등록하라고 나오는데요, IoT 허브에 있는 QR 코드를 찍는 것만으로 새로 집을 만들거나, 기존에 있는 집으로 들어갈 수 있는데요, 이번에는 이미 저렇게 집이 있는 곳으로 들어가 보겠습니다.
    - (QR찍기)
    - 허브 등록!
    - 기존에 등록되어 있는 집으로 유저가 잘 연결되었네요!
- 기기 소개
    - 이미 집에 다양한 기기가 있는것을 목록에서 보실 수 있는데요, 간단히 저희 IoT 기기들을 소개드리면,
    - 방 등 조명 그리고 무드등 조명과 같은 조명 기기가 있고,
    - 커튼 그리고 창문 IoT 기기 등으로 구성이 되어 있습니다.
- 기기 직접 제어
    - 삼성 SmartThings 나 구글 홈 등의 앱을 사용해보신 분들은 익숙하실텐데요, 저희 서비스 MIMO 에서도 앱을 통해서 원격으로 사용자가 IoT 기기 제어 기능을 수행할 수 있습니다.
    - 한번 무드등과 조명을 켜보도록 하겠습니다.
    - 이렇게 잘 제어가 되는 부분을 확인하실 수 있습니다.98
- 입면 환경 제어
    - 이건 기본적인 기능이고, 저희 서비스의 핵심은 직접 제어 뿐만이 아니라
    - 사용자의 수면데이터를 받아서 수면 상태에 따라 IoT 기기가 자동으로 동작하게끔 되어있습니다. 한번 시연자의 도움을 받아 직접 보도록 하겠습니다.
    - 아무래도 저희 시연자가 많이 졸린 것 같군요 → “이때 하품 한번 하고 푹 쓰러져 자기”
    - 이렇게 사용자가 잠에 들었는데, 지금 방의 환경을 보시면 불은 켜져있고, 창문은 열려있는 등, 적절한 수면 환경이 조성되지 않은 상황입니다.
    - 이 때, 사용자가 잠에 들었다는 데이터를 스마트워치를 통해 받아오게 되면,
    - (sleepLevel 4로 쏴주기)
    - 보시는 것처럼 조명의 경우 OFF 시키고, 창문과 커튼의 경우는 완전히 닫아주어서 쾌적한 수면환경을 자동으로 조성하게 됩니다
    - (시간끌어야됨)
- 기상 환경 제어
    - 잠들었으면 이번엔 일어나야겠죠,
    - 아까 말씀드렸다시피 수면 사이클에 맞추어 REM수면 상태에서부터 자연스럽게 기상하는 것이 중요하기에,
    - 저희는 사용자가 미리 설정한 기상시간에 개운하게 일어날 수 있도록, 기상 시간 이전 REM수면 상태가 감지되면 IoT 기기를 자동으로 제어하여 사용자의 기상을 돕습니다. 한번 직접 보도록 하겠습니다.
    - 시연자 뒤척이기 (꿈틀꿈틀)
    - 아무래도 시연자가 움찔움찔 하는걸 보니 얕은수면을 넘어 REM 수면 상태까지 올라온 것 같습니다.
    - (sleepLevel == 6) or (”용상윤” 스마트폰에서 버튼 눌러서 전송하기)
    - 이렇게 수면상태가 얕아진 것을 감지하게 되면, 기상시간까지 남은 시간을 계산하여 조명의 조도를 서서히 높여주게 되고, 알람소리도 서서히 키워주게 됩니다. 이를 통해 사용자는 다시 깊게 잠들지 않고 보다 쾌적하게 일어날 수 있게 됩니다
    - (기지개 하고 “용상윤” 스마트폰 화면 잠금해제)
    - 사용자가 이렇게 기상해서 알람을 끄기 위해 핸드폰을 동작하게 되면 사용자가 완전히 기상한 것으로 인식하고, 창문과 커튼도 열어줌으로써 아침 IoT 루틴을 완전히 자동화할 수 있게 됩니다.
    - (“용상윤” 계정의 기상시간 해제)
- 수면 전 환경 제어 (스마트폰 무드등)
    - 이 외에도 잠에 들기 전 어두운 상황에서 스마트폰을 사용하게 되면 뇌의 각성상태를 유발하여 양질의 수면을 어렵게 하기에, 자동으로 무드등을 켜주어 이를 방지하고자 하는 기능을 추가하였습니다.
        - 불을 다 끄구요 (”윤동휘” 스마트폰으로 시연 불 다 끄기, 교실 등도 다 끄기)
    - 밤 시간에, 아무 조명도 켜지지 않은 이 상황에서 사용자가 스마트폰을 사용하게 되면!
    - (”용상윤” 스마트폰 잠금해제)
    - 이렇게 무드등을 켜주어 스마트폰 불빛만 보는 일이 없도록 자동으로 제어됩니다.
