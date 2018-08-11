FROM         openjdk:8u121-jdk-alpine
MAINTAINER    huasuoworld@outlook.com
ADD filesystem /tmp/filesystem
WORKDIR /tmp
RUN ["wget","http://www-us.apache.org/dist/maven/maven-3/3.5.4/binaries/apache-maven-3.5.4-bin.tar.gz"]
RUN ["tar","-zxvf","apache-maven-3.5.4-bin.tar.gz"]
WORKDIR /tmp/filesystem
RUN ["/tmp/apache-maven-3.5.4/bin/mvn","-Dmaven.test.skip=true","clean","package"]
EXPOSE  10010
ENTRYPOINT java $JAVA_OPTS -jar /tmp/filesystem/target/filesystem.jar
VOLUME /tmp

#docker build -t filesystem:latest -f Dockerfile target/