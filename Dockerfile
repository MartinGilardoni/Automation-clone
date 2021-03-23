FROM maven:3.6.3-openjdk-16

VOLUME [ "/src" ]
WORKDIR /src

ENTRYPOINT [ "mvn", "clean", "install" ]