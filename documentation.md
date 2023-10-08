# Documentation

## Contents
[Requirements](#requirements)  
[Build & Run](#build--run)  
[Calling the API](#calling-the-api)  
[Running the tests](#running-the-tests)  

___

## Requirements

To build and run this app, you will need

- JDK 17 or higher
- Apache Maven
- Docker

## Build & Run

The app works along with a Postgres database. To get it started, simply position yourself in the project directory and run `docker compose up`.

Once it is up and running, you can build the app using `mvn clean compile`.

Finally, you can run the app either from your IDE, or by running `mvn spring-boot:run`.

## Calling the API

As per the requirements, this app provides 2 endpoints, allowing the client to :

- Register a user
- Get the details of a registered user

While the app is running, you will have access to a [Swagger API documentation](http://localhost:8080/swagger-ui/index.html) allowing to call the endpoints directly.

Alternatively, you can import the [Postman collection](offre_tech_test.postman_collection.json) should you prefer running the calls through Postman.

While calling the endpoints, you will notice that the AOP logging displays 5 lines in the console,
allowing you to precisely observe as the calls are processed, as well as the speed of treatment. 
Those logs are quite verbous right now, as they exist to display the functionning of the AOP logging.


## Running the tests

Two kinds of tests are embedded to this project

- Unit tests, with Junit 5
- Integration tests, with Junit 5 and Cucumber 7.14.0

You can run these tests using `mvn test`, or through your IDE.