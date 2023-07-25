# Read Me First
The following was discovered as part of building this project:

* The original package name 'com.cukamart.product-service' is invalid and this project uses 'com.cukamart.productservice' instead.

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.2/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.2/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.1.2/reference/htmlsingle/#web)
* [Spring Data MongoDB](https://docs.spring.io/spring-boot/docs/3.1.2/reference/htmlsingle/#data.nosql.mongodb)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with MongoDB](https://spring.io/guides/gs/accessing-data-mongodb/)

RUN docker container (mysql 8.x):
docker run -d -p 3306:3306 --name mysql-container -e MYSQL_ROOT_PASSWORD=mysql mysql:latest
open Mysql WorkBench connect to it.
CREATE SCHEMA order_service;
SHOW DATABASES;


Services do not autowire class - seperate to api / impl
TODO decouple controller and services both ways (Request/Response) - no domain in controller - can use mapper package
mongock, liquibase - initializer and updater
POM - externalized versions
MAVEN - modularized services further to multiple maven modules
Inventory Response - used in multiple microservices - how to handle ??
webClient - hardcoded url how to make it environment independent ?

eureka neregistruje multiple instances of 1 service
intellij neukazuje porty beziacich service



InventoryResponse:
In your case, where both microservices require an object called InventoryResponse, the best approach is to create a shared library or module containing the common data model, and then include this library as a dependency in both microservices.

Here's a step-by-step guide on how to handle this scenario:

Create a Shared Library:

Create a new Java project that will serve as the shared library/module.
Define the InventoryResponse class in this shared project with all the required fields and methods.
Package the Shared Library:

Build the shared library as a JAR file using your build system (Maven, Gradle, etc.).
Publish the JAR file to a local Maven repository or a remote repository (like Nexus, JFrog, etc.).
Include the Shared Library in Microservices:

In your Order and Inventory microservices, update the pom.xml (if you are using Maven) or build.gradle (if you are using Gradle) to include the shared library as a dependency.
Use InventoryResponse in Microservices:

Now, you can directly use the InventoryResponse class in both the Order and Inventory microservices.
Here's an example of how your pom.xml might look in the Order and Inventory microservices:

<!-- In the Order microservice pom.xml -->
<dependencies>
    <!-- Other dependencies -->
    <dependency>
        <groupId>com.example</groupId>
        <artifactId>shared-library</artifactId>
        <version>1.0.0</version> <!-- Replace with the actual version you published -->
    </dependency>
</dependencies>

<!-- In the Inventory microservice pom.xml -->
<dependencies>
    <!-- Other dependencies -->
    <dependency>
        <groupId>com.example</groupId>
        <artifactId>shared-library</artifactId>
        <version>1.0.0</version> <!-- Replace with the actual version you published -->
    </dependency>
</dependencies>

By following this approach, you maintain a clear separation between the microservices while still being able to share the InventoryResponse object and keep its definition consistent across services. If there are any changes to the InventoryResponse object, you can update the shared library and then update both microservices to use the new version.

Remember that this approach works well for simple data models like DTOs (Data Transfer Objects). If the object becomes complex or starts having specific business logic tied to it, consider keeping them separate and only share minimal data between services.