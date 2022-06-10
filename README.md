# precharge-service

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

### Swagger
```
http://localhost:8080/swagger-ui.html
```

### Build database dev
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

```
### swagger url
```
    http://localhost:8080/swagger-ui.html#/
```
### Comment

The class 'KafkaConsumer' is the 3rd system service, 
so it will not actually be implemented in this class
and don't have test for it in this project.





