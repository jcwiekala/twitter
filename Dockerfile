FROM sapmachine:latest
RUN mkdir /opt/twitter
COPY target/twitter-spring-boot.jar /opt/twitter
CMD ["java", "--enable-preview","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/twitter/twitter-spring-boot.jar"]