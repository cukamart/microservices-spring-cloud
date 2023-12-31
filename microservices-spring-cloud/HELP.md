http://localhost:8080/eureka/web - eureka dashboard (through api-gateway)
http://localhost:8081/actuator - spring boot actuator


-----------------------START DOCKER----------------------------
https://springframework.guru/why-you-should-be-using-spring-boot-docker-layers/

JAVA 9
Multi-Stage Docker Build

https://cloud.google.com/blog/products/application-development/introducing-jib-build-java-docker-images-better

https://tomgregory.com/jib-vs-spring-boot-for-building-docker-images/

<plugin>
                <groupId>com.google.cloud.tools</groupId>
                <artifactId>jib-maven-plugin</artifactId>
                <version>3.2.1</version>
                <configuration>
                    <from>
                        <image>eclipse-temurin:17.0.4.1_1-jre</image>
                    </from>
                    <to>
<!--                        <image>microservices-tutorial/${project.artifactId}</image>-->
                        <image>registry.hub.docker.com/cukamart/${project.artifactId}</image>
                    </to>
                </configuration>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>dockerBuild</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>

mvn clean compile jib:build

copy to m2 folder - settings.xml
<servers>
<server>
<id>registry.hub.docker.com</id>
<username>your_docker_hub_id</username>
<password>your_password</password>
</server>
</servers>

docker compose up -d
docker logs -f broker
docker logs -f order-service
-----------------------END DOCKER----------------------------


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
dont use "latest in docker specify version"

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