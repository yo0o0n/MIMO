FROM amazoncorretto:17.0.9-al2023-headless
ENV TZ Asia/Seoul
USER root
RUN mkdir -p /home/jenkins && \
    chown jenkins:jenkins /home/jenkins
USER jenkins
ENV HOME /home/jenkins
COPY ./build/libs/mimo.jar /mimo.jar
ENTRYPOINT ["java", "-jar", "mimo.jar"]
