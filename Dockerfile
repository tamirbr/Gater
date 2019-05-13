FROM java:8-jdk-alpine

COPY ./target/com.gater-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'com.gater-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java","-jar","com.gater-0.0.1-SNAPSHOT.jar"] 