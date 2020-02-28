# Java boilerplate with login using OpenApi v3

![alt text](https://www.startus.cc/sites/default/files/styles/company_profile_cover/public/logo_1_rand2000x.png?itok=QKPdHi01 "Jiratech Logo")


## Setup
1. Add your configuration to application-dev.yml
2. For minio setup uncomment initializeAmazon() function from com.project.server.business.StorageService
3. Start local server with: clean spring-boot:run -Dspring-boot.run.profiles=dev


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
        <inputSpec>${project.basedir}/src/main/resources/open-api/user-management.json</inputSpec>
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


In adition the boilerpalte provide the automatic generation of DeepVISS (https://deepviss.org/)
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
    -e STORAGE_URI=storage_uri \
    -e STORAGE_ACCESS_KEY=storage_access \
    -e STORAGE_SECRET_KEY=storage_secret \
    -e STORAGE_DEFAULT_BUCKET=bucket \
    -e ACCESS_KEY=jwt_secret \
    -e LOG_HOME=/data/logs \
    -p desired_port:server_port
    project/server 
~~~~

SENTRY_DSN is used for monitoring and erro tracking (sentry.io)


