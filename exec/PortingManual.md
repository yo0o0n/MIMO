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
# 3. Build
### Backend
```sh
```
---
### Infra
```sh
```
---
### Frontend
```sh
```
---
### Embedded
```sh
```
# 4. Kakao API (social login)
```sh
```
# 5. Deployment
### Backend
```sh
```
---
### Infra
```sh
```
---
### Frontend
```sh
```
---
### Embedded
```sh
```
# 6. Presentation scenario
```sh
```
