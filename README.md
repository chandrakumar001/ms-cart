# ms-cart

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)
* [Application URL Swagger](#Application-URL-Swagger)
* [Cloudfoundry](#Cloudfoundry)
* [Additional Information-Optional](#Additional-Information-Optional)

## General info
This project for ms-cart microservice and used lasted version spring boot, swagger(OpenAPI),Code Generator swagger.

#### Technologies

 * Java: 11
 * Spring boot/data jpa/Swagger(OpenAPI)
 * In-memory database: H2 database
 * Maven 3.6.3

#### Project Structure
    src
      main
      test
   
## Setup

#### Clone the project
   
   
        git clone https://github.com/chandrakumar001/ms-cart

#### Running Tests

  You can run tests by invoking the following command. 

        cd ms-cart
        mvn clean install
        mvn verify
    
#### Packaging
        
  You can package by invoking the following command. 
        
        mvn package -Dmaven.test.skip=true

## Application-URL-Swagger:
  Perform all operation like get parent and child transactions all list.
  
  Local Swagger:
  <http://localhost:8080/swagger-ui.html>
   
  IBM cloud:
   <https://ms-cart.mybluemix.net/swagger-ui.html>

    
    Sample Output: please refer output folder

##### Mock database data scripts:

    src/main/resources/data.sql
    src/main/resources/schema.sql

##### In-memory-Database URL:


https://ms-cart.mybluemix.net/h2-console/
    
    url: jdbc:h2:mem:testdb
    username: sa
    password: <empty>
      

GET  https://ms-cart.mybluemix.net/api/v1/child-transactions
    show all list of child-transactions
    
    refer output folder :: "get-person.PNG"
    
## Cloudfoundry

    ibmcloud login -a https://cloud.ibm.com -u passcode -p <passcode>
    ibmcloud target --cf
    ibmcloud cf push  -f cloudfoundry/manifest.yml  --vars-file cloudfoundry/dev-vars.yml

##### Application Name:

     ms --->    means Microservice
     transactions --> Application Name
     Example: ms-cart
  

##### Application Scaling:

Scaling Horizontally:

Incoming requests to your application are automatically load balanced across all instances of your application, and each instance handles tasks in parallel with every other instance. 

    ibmcloud cf scale ms-cart -i 2

Scaling Vertically:

Vertically scaling an application changes the disk space limit or memory limit that Cloud Foundry applies to all instances of the application


-k DISK to change the disk space limit applied to all instances of your application

-m MEMORY must be an integer followed by either an M, for megabytes, or G, for gigabytes


    ibmcloud cf scale ms-cart -k 512M
    ibmcloud cf scale ms-cart -m 1G
    ibmcloud cf scale ms-cart -i 2
    
Show all apps:

        ibmcloud cf apps
        ibmcloud cf app ms-cart   
        ibmcloud cf logs ms-cart
        ibmcloud cf delete ms-cart
