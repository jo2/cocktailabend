FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD  cocktailabend.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java", "-Djava.security.edg=file:/dev/./urandom", "-jar", "/app.jar"]