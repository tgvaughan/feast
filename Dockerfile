# Dockerfile to build container for unit testing

FROM eclipse-temurin:17

RUN apt-get update && apt-get install -y git ant

WORKDIR /root

ADD . ./

ENTRYPOINT ant test
