#!/bin/bash
./mvnw clean package && docker build -t twitter-spring-boot .
cd docker
docker-compose up