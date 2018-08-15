FROM java:8
#apt maven
RUN apt-get install maven
#apt git
RUN apt-get install git
#git checkout / release download link
RUN git clone -b '0.0.1' --single-branch --depth 1 https://git.wh-ef.de/Netz-AG/cocktailabend.git
#mvn clean package
RUN mvn clean install




FROM java:8
VOLUME /tmp
COPY --from=0 /target/cocktailabend-0.0.1-SNAPSHOT.jar /app.jar
#ADD  /target/cocktailabend-0.0.1-SNAPSHOT.jar /app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]