FROM openjdk:21
ARG JAR_FILE
COPY target/weather-0.0.1-SNAPSHOT.jar /weather-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/weather-0.0.1-SNAPSHOT.jar","-web -webAllowOthers -tcp -tcpAllowOthers -browser"]