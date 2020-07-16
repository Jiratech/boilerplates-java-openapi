# Java boilerplate with login using OpenApi v3

![alt text](https://www.startus.cc/sites/default/files/styles/company_profile_cover/public/logo_1_rand2000x.png?itok=QKPdHi01 "Jiratech Logo")

## Description

Simple boilerplate for building dockerized spring rest server with open api and postgres database.

Features: 
1. automatic creation of user table
2. automatic creation of endpoints and DTOs based on open-api schema
3. Swagger on http://localhost:8070/swagger-ui.html#/
4. integration with logback for elk stack


## Fast Start
1. Install docker and docker-compose 
~~~~
cd fast-start
chmod u+x docker-compose-ubuntu.sh
./docker-compose-ubuntu.sh
~~~~
2. Build server image in root
~~~~
mvn clean install dockerfile:build
~~~~
3. Run docker compose (this will start the server with a postgres database)
~~~~
cd fast-start
docker-compose up -d
~~~~


## Setup
1. Add your configuration to application-dev.yml 
2. Start local server with: clean spring-boot:run -Dspring-boot.run.profiles=dev


## OpenApi

The project uses openapi v3 generator for models and endpoints
For user model and enpoints use the following generator:
~~~~
<execution>
    <id>1</id>
    <goals>
        <goal>generate</goal>
    </goals>
    <configuration>
        <inputSpec>https://raw.githubusercontent.com/Jiratech/boilerplate-openapi-schema/master/schema.json</inputSpec>
        <generatorName>spring</generatorName>
        <modelPackage>openapi.project.model</modelPackage>
        <apiPackage>openapi.project.api</apiPackage>
        <invokerPackage>openapi.project.invoker</invokerPackage>
        <configOptions>
            <sourceFolder>target/generated-sources/java/main</sourceFolder>
        </configOptions>
    </configuration>
</execution>
~~~~

In adition the boilerpalte provides the automatic generation of DeepVISS (https://deepviss.org/)
https://github.com/deepviss-org

![alt text](https://avatars1.githubusercontent.com/u/51749880?s=200&v=4 "DeepVISS Logo")

## Docker build

1. Go to root folder and run:
~~~~
mvn clean install dockerfile:build
~~~~
2. Run the docker with the following configuration:
~~~~
docker run --name project -d \
    -e "SENTRY_DSN=$SENTRY_DSN" \
    -e "SPRING_PROFILES_ACTIVE=prod" \            
    -e SERVER_PORT=server_port \
    -e POSTGRES_URI=postgres_uri \
    -e POSTGRES_USERNAME=username \
    -e POSTGRES_PASSWORD=password \
    -e ACCESS_KEY=jwt_secret \
    -e LOG_HOME=/data/logs \
    -p desired_port:server_port
    project/server 
~~~~

SENTRY_DSN is used for monitoring and error tracking (sentry.io)


