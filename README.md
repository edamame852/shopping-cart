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
2. Installing Java 17
- `bash (WSL)`
  - Update apt-get `sudo apt-get update`, enter sudo password
  - install openjdk17 `sudo apt-get install openjdk-17-jdk`
  - Usually apt-get java via WSL is installed `/usr/lib/jvm/java-17-openjdk-amd64`
  - Add this into ~/.bashrc `# Installing JAVA on Mar 8, 2025
    export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
    export PATH=$JAVA_HOME/bin:$PATH`
  - source bashrc `source ~/.bashrc`
  - check java version `java -version`
  - `mvn -version` should also work as a result
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
package com.self.shopping_cart.model;

// Imported into pom.xml Java Persistence API (JPA)
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
5. Create test class
- under src/test/java
- rewrite it in this fashion

```java
package com.self.shopping_cart;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShoppingCartApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testGetAllProducts() {
		ResponseEntity<String> response = restTemplate.getForEntity("/products", String.class);
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).contains("[]");
	}
}
```

6. Create a Product Controllers to handle a products/ enpoint
- Spring data JPA repositories = providing CRUD
- JPA repository extends repository interfaces, providing save(), findAll(), findById()

- Controllers handles HTTP requests and maps them to service methods
- @RestController = @Controller + @ResponseBody = Handling RESTful request
- @RequestMapping = Maps HTTP request to handler methods
- @GetMapping, @PostMapping = Handling GET and POST request

```java
package com.self.shopping_cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
```

7. Create Repository 


High level design overview:
```markdown
src/
    main/
        java/
            com/
                self/
                    shopping_cart/
                        controller/
                            ProductController.java
                        model/
                            Product.java
                        repository/
                            ProductRepository.java
                        ShoppingCartApplication.java
        resources/
            application.properties
    test/
        java/
            com/
                self/
                    shopping_cart/
                        ShoppingCartApplicationTests.java
```


6. Create Service Layer
- Service layer contains business logic and talks to repositories
- @Service = marks class as servie component, allow spring component scanning and deps injection

7. Verify that mvn can compile properly: ` mvn clean install -Dmaven.test.skip=true`
