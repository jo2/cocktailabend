FROM java:8
#apt maven
RUN apt-get update -y && apt-get install maven -y
#apt git
RUN apt-get install git
#git checkout / release download link
RUN git clone -b 'multistage-build' --single-branch --depth 1 https://git.wh-ef.de/Netz-AG/cocktailabend.git
#mvn clean package
RUN mvn clean install -f cocktailabend/pom.xml

FROM java:8
VOLUME /tmp
RUN ls
COPY --from=0 cocktailabend/target/cocktailabend-0.0.1-SNAPSHOT.jar /app.jar
#ADD  /target/cocktailabend-0.0.1-SNAPSHOT.jar /app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]