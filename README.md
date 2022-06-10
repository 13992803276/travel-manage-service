# travel-manage-service

# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/maven-plugin/reference/html/#build-image)
* [Spring Data JDBC](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/reference/htmlsingle/#features.sql.jdbc)
* [Spring for Apache Kafka](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/reference/htmlsingle/#boot-features-kafka)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Swagger 2.0](https://swagger.io/specification/v2/)
### Guides

The following guides illustrate how to use some features concretely:

* [Using Spring Data JDBC](https://github.com/spring-projects/spring-data-examples/tree/master/jdbc/basics)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

### Swagger API
-[http://localhost:8080/swagger-ui/](http://localhost:8080/swagger-ui.html#/)

### Prerequisites
- Java 8
- Maven 3.8.1
- Docker 20.10.7
- MySql latest
- colima 0.3.2

#### Install Colima To Run Docker Without Docker Desktop On MacOS
Because of the license limit whit Docker Desktop, we should use colima instead.
```shell

# install colima (using sudo may request password input)
curl -LO https://github.com/abiosoft/colima/releases/download/v0.3.2/colima-Darwin-x86_64 && sudo install colima-Darwin-x86_64 /usr/local/bin/colima

# install lima and docker
brew install lima docker

# start colima
colima start -m 4
```
### Build Database [Mac OS]
database is created using mysql,build process as fellows:
```
docker run --name travel-manage -e MYSQL_ROOT_PASSWORD=123456 -p 3306:3306 -d mysql:latest

docker exec -it travel-manage bash

mysql -uroot -p

create user 'user1' identified with mysql_native_password by '123456';

grant all privileges on *.* to 'user1';

create database TRAVEL_MANAGE charset=utf8;
```
### install mvnvm
```
    brew install maven
    brew install mvnvm
```
### compile
```
   mvn compile
```
### start server
```
   mvn install
```
### run test
```
   mvn test
```

### Run the application

Run the application which will be listening on port `8080`.

### Fake Repository test

Repository test are build with H2 database
```
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>
```

###Code struct
```
├── main
│   ├── java
│   │   └── com
│   │       └── tw
│   │           └── travelmanage
│   │               ├── TravelManageServiceApplication.java
│   │               ├── constant
│   │               ├── controller
│   │               │   ├── advice
│   │               │   ├── configuration
│   │               │   └── dto
│   │               ├── domain
│   │               │   └── usermanage
│   │               ├── infrastructure
│   │               │   ├── httpInterface
│   │               │   │   └── httpentity
│   │               │   ├── mqservice
│   │               │   │   ├── kafka
│   │               │   │   └── mqentity
│   │               │   └── repository
│   │               │       └── entity
│   │               ├── service
│   │               │   └── datamodel
│   │               └── util
│   │                   ├── exception
│   │                   └── mapstruct
│   └── resources
│       ├── application.properties
│       └── db
│           └── migration

└── test
    ├── java
    │   └── com
    │       └── tw
    │           └── travelmanage
    │               ├── TravelManageServiceApplicationTests.java
    │               ├── controller
    │               │   └── TravelAgreementControllerTest.java
    │               ├── infrastructure
    │               │   ├── httpInterface
    │               │   │   ├── FixedChargeServiceTest.java
    │               │   │   └── InvoiceApplyServiceTest.java
    │               │   ├── mqservice
    │               │   │   └── MqServiceTest.java
    │               │   └── repository
    │               │       └── FixedStatementRepositoryTest.java
    │               └── service
    │                   └── TravelAgreementServiceTest.java
    └── resources
        ├── application-test.properties
        └── db
            └── migration

```






