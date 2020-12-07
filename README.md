# Twitter project

Project is simple Twitter like application based technologies:
* Java
* Spring Boot
* Spring WebFlux
* Reactive MongoDB
* Docker 
* Swagger


## Scenarios
Application was implemented basing on scenarios:
#### Posting

A user should be able to post a 140 character message.

#### Wall

A user should be able to see a list of the messages they've posted, in reverse
chronological order.

#### Following

A user should be able to follow another user. Following doesn't have to be
reciprocal: Alice can follow Bob without Bob having to follow Alice.

#### Timeline

A user should be able to see a list of the messages posted by all the people
they follow, in reverse chronological order.

## Running solution
Before run the solution please ensure that You have JDK 15 and Docker installed.

Simple script:
* . ./start.sh

Or manual steps:
* ./mvnw clean package && docker build -t twitter-spring-boot .
* open **/docker** folder
* docker-compose up

## Documentation
Page was documented using Swagger. It means that all endpoints are listed on page:
http://localhost:8080/swagger-ui/

## Postman
Postman collection was created with examples which are enough to run and test the application. Collection could be found in **/postman** directory. Executing all test cases from catalog **execute-all-scenarios** verifies main application scenarios.



