#!/usr/bin/env bash

docker container run \
--publish 5432:5432 \
--env POSTGRESQL_DATABASE=addressbook_db \
--env POSTGRESQL_USERNAME=addressbook \
--env POSTGRESQL_PASSWORD=my_pass \
--detach \
--volume ./db/init-db.sql:/docker-entrypoint-initdb.d/init-db.sql \
--name db \
bitnami/postgresql:15

docker container start db

docker container run \
--publish 8080:8080 \
--link db \
--volume ./configuration:/app/config \
addressbook:1
