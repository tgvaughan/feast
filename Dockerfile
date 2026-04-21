# Dockerfile to build container for unit testing

FROM debian:stable

RUN apt-get update
RUN apt-get install -y openjdk-25-jdk maven

WORKDIR /root

ADD . ./

ENTRYPOINT mvn test
