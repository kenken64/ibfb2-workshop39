FROM maven:3.8.3-openjdk-17 AS build

#
# first stage
#
ENV MONGOHOST=mongodb
ENV MONGOPORT=27017
ENV MONGODB=marveldb
ENV MONGOUSER=test
ENV MONGOPASSWORD=1234
ENV REDISHOST=redis
ENV REDISPORT=6379
ENV REDISUSER=
ENV REDISPASSWORD=
ENV MARVEL_API_URL=https://gateway.marvel.com:443/v1/public/
ENV MARVEL_API_PRIVATE_KEY=9ec118cdd907df89f157b4f2625c5b0abb670bd7
ENV MARVEL_API_PUBLIC_KEY=76ee705b0c648cd243529c1870cfbf79

COPY src /home/app/src
COPY pom.xml /home/app

RUN mvn -f /home/app/pom.xml clean package

#
# second stage
#

FROM openjdk:17-oracle

ENV MONGOHOST=mongodb
ENV MONGOPORT=27017
ENV MONGODB=marveldb
ENV MONGOUSER=test
ENV MONGOPASSWORD=1234
ENV REDISHOST=redis
ENV REDISPORT=6379
ENV REDISUSER=
ENV REDISPASSWORD=
ENV MARVEL_API_URL=https://gateway.marvel.com:443/v1/public/
ENV MARVEL_API_PRIVATE_KEY=9ec118cdd907df89f157b4f2625c5b0abb670bd7
ENV MARVEL_API_PUBLIC_KEY=76ee705b0c648cd243529c1870cfbf79

COPY --from=build /home/app/target/server-0.0.1-SNAPSHOT.jar /usr/local/lib/server.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/lib/server.jar"]

