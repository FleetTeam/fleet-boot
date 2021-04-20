FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER jlwrp@126.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /fleet-boot

WORKDIR /fleet-boot

EXPOSE 8080

ADD ./target/fleet-boot-module-system-1.0.0.jar ./

CMD sleep 60;java -Djava.security.egd=file:/dev/./urandom -jar fleet-boot-module-system-1.0.0.jar