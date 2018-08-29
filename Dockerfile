FROM java:8
RUN apt-get update -y && apt-get install maven -y
RUN apt-get install git
RUN git clone -b 'multistage-build' --single-branch --depth 1 https://git.wh-ef.de/Netz-AG/cocktailabend.git
RUN mvn clean install -f cocktailabend/pom.xml

FROM java:8
VOLUME /tmp
COPY --from=0 cocktailabend/target/cocktailabend-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]