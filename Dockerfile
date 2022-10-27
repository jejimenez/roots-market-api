FROM amazoncorretto:8
VOLUME /tmp
ARG JAR_FILE
ARG POSTGRES_PW
ARG ROOTS_SMTP
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
ENV POSTGRES_PW=${POSTGRES_PW}
ENV ROOTS_SMTP=${ROOTS_SMTP}

