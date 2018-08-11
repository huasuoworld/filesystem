FROM         java:8
MAINTAINER    huasuoworld@outlook.com
ADD filesystem /tmp/filesystem
RUN apt-get update
RUN apt-get install -y maven
WORKDIR /tmp/filesystem
RUN ["mvn","-Dmaven.test.skip=true","clean","package"]
EXPOSE  10010
ENTRYPOINT java $JAVA_OPTS -jar /tmp/filesystem/target/filesystem.jar
VOLUME /tmp

#docker build -t filesystem:latest -f Dockerfile target/