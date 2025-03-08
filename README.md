# shopping-cart

## Step-by-step process
1. Spring Initializr
- Spring Deps
  - Spring Web (Build web, including RESTful, applications using Spring MVC. Uses Apache Tomcat as the default embedded container.)
  - Spring Data JPA (Persist data in SQL stores with Java Persistence API using Spring Data and Hibernate)
  - H2 Database (Provides a fast in-memory database that supports JDBC API and R2DBC access, with a small (2mb) footprint. Supports embedded and server modes as well as a browser based console application.)
- Project: Maven
- Language: Java
- Spring boot 3.4.3
- packaging: jar
- Java 17
- Package name: com.self.shopping-cart
2. Install Java
- MacOS
  - Install homebrew `/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"`
  - update homebrew `brew update`
  - install openjdk17 `brew install openjdk@17`
  - Check openjdk installation `ls /usr/local/Cellar/ | grep openjdk*`
  - Check zsh or bash shell `echo $SHELL`
  - add 2 Java paths to bsh `vi ~/.bashrc` then
  - `export JAVA_HOME=/usr/local/Cellar/openjdk@17/17.0.14/libexec/openjdk.jdk/Contents/Home
export PATH=$JAVA_HOME/bin:$PATH`
  - source bashrc `source ~/.bashrc`
3. Setup H2 DB
- add these into src/main/resources/application.properties
```yml
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
```
4. Set up Entity Class
- Entity Class = data model, mapping to DB tables
- @Entity = Making this class as a JPA entity by decorating `@Entity`
- @Id = The primary key
- @GeneratedValue = to auto generate primary keys 
```java
package com.self.shopping_cart.;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private double price;

    // Getters and setters
}
```
5. Create Repository class
- Spring data JPA repositories = providing CRUD
- JPA repository extends repository interfaces, providing save(), findAll(), findById()

6. Create Service Layer
- Service layer contains business logic and talks to repositories
- @Service = marks class as servie component, allow spring component scanning and deps injection

7. Create Controllers
- Controllers handsles HHTP requests and maps them to service methods
- @RestController = @Controller + @ResponseBody = Handling RESTful request
- @RequestMapping = Mapps HTTP request to handler methods
- @GetMapping, @PostMapping = Handling GET and POST request