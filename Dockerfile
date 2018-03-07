FROM wddocker.mapbar.com/java:1.0b
MAINTAINER docker@navinfo.com

LABEL service-type=customer
LABEL memory=${memory}
LABEL scalable=${scalable}
LABEL restart=always

ENV JAVA_OPTS="${opts}"
LABEL start-string="docker run -d --name %name -m %memory \
                                   -v %LOG_DIR:/data/log \
                                   --restart=always \
                                   -l service-type=customer \
                                   --dns-search=weave.local \
                                   %repository/${groupId}.${artifactId}:${version}"

#Actions above will be cached
ADD ./*-*[0-9]-boot.jar /${artifactId}.jar
ENTRYPOINT java -server -Djava.net.preferIPv4Stack=true  -Dspring.getenv.ignore=true -Djava.security.egd=file:/dev/./urandom $JAVA_OPTS -jar /${artifactId}.jar
