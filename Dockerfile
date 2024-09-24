#FROM ubuntu:latest
#LABEL authors="asm"
#
#ENTRYPOINT ["top", "-b"]

FROM openjdk:17-jdk
WORKDIR /app
RUN mkdir /app/config
COPY target/*.jar /app/app.jar
EXPOSE 8080/tcp
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

#docker image build --tag addressbook:1 .
#docker container run --publish 8080:8080 addressbook:1

#docker container run --publish 8080:8080 --link db --volume ./configuration:/app/config addressbook:1


#docker container run --publish 5432:5432 --env POSTGRESQL_PASSWORD=my_pass bitnami/postgresql:15


