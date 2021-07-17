# ms-cart

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)
* [Application URL Swagger](#Application-URL-Swagger)
* [Cloudfoundry](#Cloudfoundry)
* [Kubernetes](#Kubernetes)
* [Additional Information-Optional](#Additional-Information-Optional)

### Task

To develop an API which is able to do the below activities.

 1. Create a new cart for a Registered user/customer  `DONE`
     
        POST ​/api​/v1​/carts
 2. Create a cart entry for the cart of the user/customer `DONE`
    
        POST ​/api​/v1​/carts​/{cart-code}​/cart-entries
 
 3. View cart entry of the cart of the user/customer `DONE`
       
        GET ​/api​/v1​/carts​/{cart-code}​/cart-entries
 
#### TBD
 * Validation yet be implements
 * As per our discussion, not required to implements user/customer module
 * Unit test and Integration test yet be implements
 
#### Problem Identified with shared code

- [ ] Lot of code mix with database layer and (service+controller) layer
- [ ] The sampled code more difficult to maintenance and understanding code flow

#### Solution with shared your code
- [x] According the layer approach, controller should not talk to directly into database layer
  that means whenever write some service(API or endpoint), we have use DTO's(service+controller)
  and database layer both are individual model class
- [x] More linear approach and easy to maintenance 
- [x] The controller also we have created new separate class such as CartEntry and Cart
- [x] Use mapper either Mapper class or MapStruct Library

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
      

POST ​/api​/v1​/carts create new cart

Payload:
    
    {
      "total": 50,
      "totalTax": 48,
      "subtotal": 8,
      "discounts": 0,
      "billingAddress": {
        "streetName": "Avenger street",
        "streetNumber": "11",
        "name": "Iron Man",
        "postalCode": "11111",
        "country": "Germany"
      },
      "shippingAddress": {
       "streetName": "DC street",
        "streetNumber": "11",
        "name": "Super Man",
        "postalCode": "11111",
        "country": "Germany"
      },
      "entriesList": [
        {
          "cartEntryCode": "12345_1_1",
          "quantity": 10,
          "price": 25
        }, {
          "cartEntryCode": "12345_1_2",
          "quantity": 1,
          "price": 23
        }
      ]
    }
    
    refer output folder :: "post-cart-creaton.JPG"

GET ​/api​/v1​/carts get all created carts

    refer output folder :: "get-all-created-cart.JPG"

GET ​/api​/v1​/carts​/{cart-code}​/cart-entries
    
    refer output folder :: "get-all-cart-entries-for-cart.JPG"

POST ​/api​/v1​/carts​/{cart-code}​/cart-entries
   request payload 
   
     {
            "cartEntryCode": "12345_7_1",
            "quantity": 1,
            "price": 5
      }
          
    refer output folder :: "add-existing-cart-into-entry.JPG"
        
## Cloudfoundry

    ibmcloud login -a https://cloud.ibm.com -u passcode -p <passcode>
    ibmcloud target --cf
    ibmcloud cf push  -f cloudfoundry/manifest.yml  --vars-file cloudfoundry/dev-vars.yml
## Kubernetes
    
  At first time, application setup execute: 'kubectl-execute-dev.bat'
  
  This bat file having all configuration and apply the kubernetes cluster, this will step the such like <b>deployment,service,ingress,HPA,VPA and network policy</b>
   
<b>This application will be deploy kubernetes cluster via CI/CD pipleline</b>  

refer: output folder

        kubernate-pods-up.JPG
         
##### Application Name:

     ms --->    means Microservice
     cart --> Application Name
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

#### Jenkins file

  here used only windows configuration         
